package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.*;
import java.util.Scanner;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.commons.util.SetTools;
import org.tweetyproject.logics.pl.reasoner.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ParserException {
        PlBeliefSet beliefSet = new PlBeliefSet();
        PlParser parser = new PlParser();
        PlBeliefSet classicalSet = new PlBeliefSet();
        PlFormula formulaWereCheckingFor = (PlFormula) parser.parseFormula("a");

        String fileName = args[0];
        String entailmentCheckingAlgorithm = args[1];
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            String formulaToCheckFor = reader.nextLine();
            if (formulaToCheckFor.contains("¬")) {
                formulaToCheckFor = formulaToCheckFor.replaceAll("¬", "!");
            }
            formulaWereCheckingFor = (PlFormula) parser.parseFormula(reformatDefeasibleImplication(formulaToCheckFor));
            while (reader.hasNextLine()) {
                String stringFormula = reader.nextLine();
                if (stringFormula.contains("¬")) {
                    stringFormula = stringFormula.replaceAll("¬", "!");
                }
                if (stringFormula.contains("~>")) {
                    stringFormula = reformatDefeasibleImplication(stringFormula);
                    beliefSet.add((PlFormula) parser.parseFormula(stringFormula));
                } else {
                    classicalSet.add((PlFormula) parser.parseFormula(stringFormula));
                }
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

        RationalReasoner reasoner = new RationalReasoner(beliefSet, classicalSet, entailmentCheckingAlgorithm);
        System.out.println(reasoner.query(formulaWereCheckingFor, entailmentCheckingAlgorithm));
        System.out.println(
                "Enter a defeasible implication formula to see if it is entailed by the current knowledge base.");
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            try {
                PlFormula formula = (PlFormula) parser.parseFormula(reformatDefeasibleImplication(input.nextLine()));
                System.out.println("Enter which entailment checking algorithm you'd like to use (binary/regular):");
                entailmentCheckingAlgorithm = input.nextLine();
                while (!entailmentCheckingAlgorithm.equals("binary")
                        && !entailmentCheckingAlgorithm.equals("regular")) {
                    System.out
                            .println("Invalid entailment checking algorithm. Please enter \'binary\' or \'regular\'.");
                    entailmentCheckingAlgorithm = input.nextLine();
                }
                System.out.println(reasoner.query(formula, entailmentCheckingAlgorithm));
                System.out.println(
                        "Enter a defeasible implication formula to see if it is entailed by the current knowledge base.");
            } catch (Exception e) {
                System.out.println(
                        "Couldn't parse formula. Ensure it is a defeasible implication in the correct format:");
                System.out.println("Implication symbol: =>");
                System.out.println("Defeasible Implication symbol: ~>");
                System.out.println("Conjunction symbol: && ");
                System.out.println("Disjunction symbol: ||");
                System.out.println("Equivalence symbol: <=>");
                System.out.println("Negation symbol: !");
                System.out.println(
                        "Enter a defeasible implication to see if it is entailed by the current knowledge base.");
            }
        }
        input.close();
    }

    static String reformatDefeasibleImplication(String formula) {
        int index = formula.indexOf("~>");
        formula = "(" + formula.substring(0, index) + ") => (" + formula.substring(index + 2, formula.length()) + ")";
        return formula;
    }
}