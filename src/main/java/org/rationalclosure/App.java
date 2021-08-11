package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.*;
import java.util.Scanner;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.reasoner.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ParserException {
        PlBeliefSet bs = new PlBeliefSet();
        PlParser parser = new PlParser();
        PlBeliefSet classicalSet = new PlBeliefSet();
        PlFormula formulaWereCheckingFor = (PlFormula) parser.parseFormula("a");

        String fileName = args[0];
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            formulaWereCheckingFor = (PlFormula) parser.parseFormula(reformatDefeasibleImplication(reader.nextLine()));
            while (reader.hasNextLine()) {
                String stringFormula = reader.nextLine();
                if (stringFormula.contains("~>")) {
                    stringFormula = reformatDefeasibleImplication(stringFormula);
                } else {
                    classicalSet.add((PlFormula) parser.parseFormula(stringFormula));
                }
                bs.add((PlFormula) parser.parseFormula(stringFormula));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(
                    "Output not in correct format. Please ensure each formula is in a separate line, with the first line being the defeasible query, and the remainder being the knowledge base. All formulas must use the following syntax:");
            System.out.println("Implication symbol: =>");
            System.out.println("Defeasible Implication symbol: ~>");
            System.out.println("Conjunction symbol: && ");
            System.out.println("Disjunction symbol: ||");
            System.out.println("Equivalence symbol: <=>");
            System.out.println("Negation symbol: !");
        }

        // Penguins have wings example
        // PlFormula formula2 = (PlFormula) parser.parseFormula("b => f");
        // PlFormula formula3 = (PlFormula) parser.parseFormula("b => w");
        // PlFormula formula1 = (PlFormula) parser.parseFormula("p => !f");
        // PlFormula formula4 = (PlFormula) parser.parseFormula("r => b");
        // PlFormula formula5 = (PlFormula) parser.parseFormula("p => b");
        // classicalSet.add(formula4);
        // classicalSet.add(formula5);
        // bs.add(formula1);
        // bs.add(formula2);
        // bs.add(formula3);
        // bs.add(formula4);
        // bs.add(formula5);
        // RationalReasoner reasoner = new RationalReasoner(bs, (PlFormula)
        // parser.parseFormula("p => w"), classicalSet);

        // flying Dutchman Example
        // PlFormula formula2 = (PlFormula) parser.parseFormula("l => b");
        // PlFormula formula3 = (PlFormula) parser.parseFormula("l => !f");
        // PlFormula formula1 = (PlFormula) parser.parseFormula("fd => b");
        // PlFormula formula4 = (PlFormula) parser.parseFormula("fd => l");
        // PlFormula formula5 = (PlFormula) parser.parseFormula("b => f");
        // bs.add(formula1);
        // bs.add(formula2);
        // bs.add(formula3);
        // bs.add(formula4);
        // bs.add(formula5);
        // RationalReasoner reasoner = new RationalReasoner(bs, (PlFormula)
        // parser.parseFormula("fd => !f"), classicalSet);

        // Example where knowledge base entails the negation of the antecedent!
        // PlFormula formula1 = (PlFormula) parser.parseFormula("!a");
        // PlFormula formula2 = (PlFormula) parser.parseFormula("c => d");
        // bs.add(formula1);
        // bs.add(formula2);
        // classicalSet.add(formula1);
        // RationalReasoner reasoner = new RationalReasoner(bs, (PlFormula)
        // parser.parseFormula("a => b"), classicalSet);

        // edge case
        // PlFormula formula1 = (PlFormula) parser.parseFormula("a => b");
        // PlFormula formula2 = (PlFormula) parser.parseFormula("a => !b");
        // bs.add(formula1);
        // bs.add(formula2);
        RationalReasoner reasoner = new RationalReasoner(bs, formulaWereCheckingFor, classicalSet);
    }

    static String reformatDefeasibleImplication(String formula) {
        int index = formula.indexOf("~>");
        formula = "(" + formula.substring(0, index) + ") => (" + formula.substring(index + 2, formula.length()) + ")";
        return formula;
    }
}