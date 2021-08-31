package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;

import org.tweetyproject.logics.pl.parser.PlParser;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class RationalReasoner {
    PlBeliefSet knowledgeBase = new PlBeliefSet();
    PlParser parser = new PlParser();
    ArrayList<PlBeliefSet> rankedKnowledgeBase = new ArrayList<PlBeliefSet>();
    Boolean indexing = false;
    IndexingBinaryEntailmentChecker indBin;
    BinaryEntailmentChecker bin;
    RegularEntailmentChecker reg;
    IndexingEntailmentChecker indReg;

    RationalReasoner(PlBeliefSet kb, PlBeliefSet classicalStatements, String entailmentCheckingAlgorithm,
            Boolean indexing) {
        this.knowledgeBase = kb;
        this.parser = new PlParser();
        rankedKnowledgeBase = BaseRankThreaded.rank(kb, classicalStatements);
        this.indexing = indexing;
        if (entailmentCheckingAlgorithm.equals("binary")) {
            if (indexing) {
                indBin = new IndexingBinaryEntailmentChecker();
            } else {
                bin = new BinaryEntailmentChecker();
            }
        } else if (entailmentCheckingAlgorithm.equals("regular")) {
            if (indexing) {
                indReg = new IndexingEntailmentChecker();
            } else {
                reg = new RegularEntailmentChecker();
            }
        }
    }

    Boolean query(PlFormula formula, String entailmentCheckingAlgorithm) {
        if (entailmentCheckingAlgorithm.equals("binary")) {
            PlBeliefSet[] rankedKnowledgeBaseArray = new PlBeliefSet[rankedKnowledgeBase.size()];
            PlBeliefSet[] rankedKBArray = rankedKnowledgeBase.toArray(rankedKnowledgeBaseArray);
            PlFormula negationOfAntecedent = new Negation(((Implication) formula).getFormulas().getFirst());
            if (indexing) {
                return indBin.checkEntailmentBinarySearch(rankedKBArray, formula, 0, rankedKnowledgeBase.size(),
                        negationOfAntecedent);
            } else {
                return bin.checkEntailmentBinarySearch(rankedKBArray, formula, 0, rankedKnowledgeBase.size(),
                        negationOfAntecedent);
            }
        } else if (entailmentCheckingAlgorithm.equals("regular")) {
            if (indexing) {
                return indReg.checkEntailment(rankedKnowledgeBase, formula);
            } else {
                return reg.checkEntailment(rankedKnowledgeBase, formula);
            }
        } else {
            System.out.println("Invalid entailment checking algorithm.");
        }
        return false;
    }
}
