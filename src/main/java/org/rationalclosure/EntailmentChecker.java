package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;

import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class EntailmentChecker {

    static Boolean checkEntailment(ArrayList<PlBeliefSet> rankedKB, PlFormula formula) {
        SimplePlReasoner classicalReasoner = new SimplePlReasoner();
        while (rankedKB.size() != 0) {
            if (!classicalReasoner.query(combine(rankedKB),
                    new Negation(((Implication) formula).getFormulas().getFirst()))) {
                rankedKB.remove(rankedKB.get(0));
            } else {
                if (classicalReasoner.query(combine(rankedKB), formula)) {
                    return true;
                }
            }
        }
        return false;
    }

    static PlBeliefSet combine(ArrayList<PlBeliefSet> ranks) {
        PlBeliefSet combined = new PlBeliefSet();
        for (PlBeliefSet rank : ranks) {
            combined.addAll(rank);
        }
        return combined;
    }

}
