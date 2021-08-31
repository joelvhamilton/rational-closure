package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class TimedReasonerComparison {
    public static void main(String[] args) {
        PlParser parser = new PlParser();
        String kbFileName = args[0];
        String stringQueryFile = args[1];
        PlBeliefSet kb = new PlBeliefSet();
        PlBeliefSet classical = new PlBeliefSet();
        try {
            File kbfile = new File(kbFileName);
            Scanner input = new Scanner(kbfile);
            while (input.hasNextLine()) {
                String formulaToAdd = input.nextLine();
                if (formulaToAdd.contains("~>")) {
                    kb.add((PlFormula) parser.parseFormula(reformatDefeasibleImplication(formulaToAdd)));
                } else {
                    classical.add((PlFormula) parser.parseFormula(formulaToAdd));
                }
            }
            input.close();
        } catch (Exception e) {
            System.out.println("This knowledge base file does not exist.");
        }

        ArrayList<PlFormula> queries = new ArrayList<PlFormula>();

        try {
            File queryFile = new File(stringQueryFile);
            Scanner secondInput = new Scanner(queryFile);
            while (secondInput.hasNextLine()) {
                String formulaToAdd = secondInput.nextLine();
                queries.add((PlFormula) parser.parseFormula(reformatDefeasibleImplication(formulaToAdd)));
            }
            secondInput.close();
        } catch (Exception e) {
            System.out.println("This knowledge base file does not exist.");
        }
        System.out.println("KB is: " + kb.toString());
        System.out.println("Queries are: " + queries.toString());
        // iterate through all queries, check for entailment 4 different ways. Print CS
        // with results. Invoke from bash script with >> filename.
    }

    static String reformatDefeasibleImplication(String formula) {
        int index = formula.indexOf("~>");
        formula = "(" + formula.substring(0, index) + ") => (" + formula.substring(index + 2, formula.length()) + ")";
        return formula;
    }
}
