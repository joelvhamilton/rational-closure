package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;

import org.tweetyproject.logics.pl.parser.PlParser;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class RationalReasoner {
    PlBeliefSet knowledgeBase = new PlBeliefSet();
    PlParser parser = new PlParser();
    ArrayList<PlBeliefSet> rankedKnowledgeBase = new ArrayList<PlBeliefSet>();
    IndexingEntailmentChecker indReg = new IndexingEntailmentChecker();
    IndexingBinaryEntailmentChecker indBin = new IndexingBinaryEntailmentChecker();

    RationalReasoner(PlBeliefSet kb, PlBeliefSet classicalStatements, String entailmentCheckingAlgorithm) {
        this.knowledgeBase = kb;
        this.parser = new PlParser();
        rankedKnowledgeBase = BaseRankThreaded.rank(kb, classicalStatements);
    }

    Boolean query(PlFormula formula, String entailmentCheckingAlgorithm) {
        if (entailmentCheckingAlgorithm.equals("binary")) {
            PlBeliefSet[] rankedKnowledgeBaseArray = new PlBeliefSet[rankedKnowledgeBase.size()];
            PlBeliefSet[] rankedKBArray = rankedKnowledgeBase.toArray(rankedKnowledgeBaseArray);
            PlFormula negationOfAntecedent = new Negation(((Implication) formula).getFormulas().getFirst());
            return indBin.checkEntailmentBinarySearch(rankedKBArray, formula, 0, rankedKnowledgeBase.size(),
                    negationOfAntecedent);
        } else if (entailmentCheckingAlgorithm.equals("regular")) {
            return indReg.checkEntailment(rankedKnowledgeBase, formula);
        } else {
            System.out.println("Invalid entailment checking algorithm.");
        }
        return false;
    }
}
