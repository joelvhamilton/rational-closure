package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class TimedReasonerComparison {
    public static void main(String[] args) {
        PlParser parser = new PlParser();
        String kbFileName = args[0];
        String stringQueryFile = args[1];
        String outputFileName = args[2];
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
        ArrayList<PlBeliefSet> rankedKnowledgeBase = BaseRankThreaded.rank(kb, classical);
        // iterate through all queries, check for entailment 4 different ways.
        RationalReasoner regular = new RationalReasoner(rankedKnowledgeBase, false);
        RationalReasoner regularIndexing = new RationalReasoner(rankedKnowledgeBase, true);
        RationalReasoner binary = new RationalReasoner(rankedKnowledgeBase, false);
        RationalReasoner binaryIndexing = new RationalReasoner(rankedKnowledgeBase, true);
        try {
            FileWriter outputFileWriter = new FileWriter(
                    "output" + kbFileName.substring(0, kbFileName.length() - 4) + ".csv");
            outputFileWriter.write("numberofranks, regular, regular_with_indexing, binary, binary_with_indexing \n");
            for (PlFormula query : queries) {
                outputFileWriter.write(rankedKnowledgeBase.size() + ", ");
                long startTime = System.nanoTime();
                regular.query(query, "regular");
                long endTime = System.nanoTime();
                outputFileWriter
                        .write(TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + ", ");

                startTime = System.nanoTime();
                regularIndexing.query(query, "regular");
                endTime = System.nanoTime();
                outputFileWriter
                        .write(TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + ", ");

                startTime = System.nanoTime();
                binary.query(query, "binary");
                endTime = System.nanoTime();
                outputFileWriter
                        .write(TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + ", ");

                startTime = System.nanoTime();
                binaryIndexing.query(query, "binary");
                endTime = System.nanoTime();
                outputFileWriter
                        .write(Long.toString(TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS))
                                + "\n");
            }

            outputFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print CSV with results. Invoke from bash script with >> filename.
    }

    static String reformatDefeasibleImplication(String formula) {
        int index = formula.indexOf("~>");
        formula = "(" + formula.substring(0, index) + ") => (" + formula.substring(index + 2, formula.length()) + ")";
        return formula;
    }
}