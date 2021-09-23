package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

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
        Boolean indexing = false;

        String fileName = args[0];
        if (args.length > 1) {
            System.out.println(args[1]);
            if (args[1].equals("indexing")) {
                indexing = true;
            }
        } else {
            indexing = false;
        }
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
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

        ArrayList<PlBeliefSet> rankedKnowledgeBase = BaseRankThreaded.rank(beliefSet, classicalSet);

        RationalReasoner reasoner = new RationalReasoner(rankedKnowledgeBase, indexing);
        System.out.println(
                "Enter a defeasible implication formula to see if it is entailed by the current knowledge base.");
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            try {
                String entailmentCheckingAlgorithm = " ";
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
                e.printStackTrace();
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