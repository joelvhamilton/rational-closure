package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;

import org.tweetyproject.logics.pl.parser.PlParser;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class RationalReasoner {
    PlBeliefSet knowledgeBase;
    PlParser parser;
    PlFormula formulaToCheckEntailmentFor;

    RationalReasoner(PlBeliefSet kb, PlFormula formula, PlBeliefSet classicalStatements) {
        this.knowledgeBase = kb;
        this.parser = new PlParser();
        this.formulaToCheckEntailmentFor = formula;
        ArrayList<PlBeliefSet> rankedKnowledgeBase = BaseRank.rank(kb, classicalStatements);
        System.out.println(EntailmentChecker.checkEntailment(rankedKnowledgeBase, formula));
    }

}
