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
    IndexingBinaryEntailmentChecker indBin = new IndexingBinaryEntailmentChecker();
    BinaryEntailmentChecker bin = new BinaryEntailmentChecker();
    RegularEntailmentChecker reg = new RegularEntailmentChecker();
    IndexingEntailmentChecker indReg = new IndexingEntailmentChecker();

    RationalReasoner(PlBeliefSet kb, PlBeliefSet classicalStatements, String entailmentCheckingAlgorithm,
            Boolean indexing) {
        this.knowledgeBase = kb;
        // kb.addAll(classicalStatements);
        // rankedKnowledgeBase = BaseRank.rank(kb, classicalStatements);
        rankedKnowledgeBase = BaseRankThreaded.rank(kb, classicalStatements);
        this.indexing = indexing;
    }

    RationalReasoner(ArrayList<PlBeliefSet> rankedKnowledgeBase, Boolean indexing) {
        this.indexing = indexing;
        this.rankedKnowledgeBase = rankedKnowledgeBase;
    }

    Boolean query(PlFormula formula, String entailmentCheckingAlgorithm) {
        if (entailmentCheckingAlgorithm.equals("binary")) {
            PlBeliefSet[] rankedKnowledgeBaseArray = new PlBeliefSet[rankedKnowledgeBase.size()];
            rankedKnowledgeBaseArray = rankedKnowledgeBase.toArray(rankedKnowledgeBaseArray);
            PlFormula negationOfAntecedent = new Negation(((Implication) formula).getFormulas().getFirst());
            if (indexing) {
                return indBin.checkEntailmentBinarySearch(rankedKnowledgeBaseArray, formula, 0,
                        rankedKnowledgeBase.size(), negationOfAntecedent);
            } else {
                return bin.checkEntailmentBinarySearch(rankedKnowledgeBaseArray, formula, 0, rankedKnowledgeBase.size(),
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
