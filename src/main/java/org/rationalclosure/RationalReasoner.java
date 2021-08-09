package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.IOException;
import java.util.ArrayList;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.BeliefSet;
import org.tweetyproject.commons.ParserException;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;

public class RationalReasoner {
    PlBeliefSet knowledgeBase;
    PlParser parser;
    PlFormula formulaToCheckEntailmentFor;

    RationalReasoner(PlBeliefSet kb, PlFormula formula) {
        this.knowledgeBase = kb;
        this.parser = new PlParser();
        this.formulaToCheckEntailmentFor = formula;
        ArrayList<PlBeliefSet> rankedKnowledgeBase = BaseRank.rank(kb);
    }

}
