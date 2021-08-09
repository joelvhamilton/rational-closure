package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;

import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

public class EntailmentChecker {

    static Boolean checkEntailment(ArrayList<PlBeliefSet> rankedKB, PlFormula formula) {
        SimplePlReasoner classicalReasoner = new SimplePlReasoner();
        while (rankedKB.size() != 0) {
            System.out.println("We are checking whether or not "
                    + (new Negation(((Implication) formula).getFormulas().getFirst())).toString() + " is entailed  by: "
                    + combine(rankedKB).toString());
            if (classicalReasoner.query(combine(rankedKB),
                    new Negation(((Implication) formula).getFormulas().getFirst()))) {
                System.out.println("It is not, so we remove " + rankedKB.get(0).toString());
                rankedKB.remove(rankedKB.get(0));
            } else {
                break;
            }
        }
        if (combine(rankedKB).size() != 0) {
            System.out.println("We now check whether or not the formula" + formula.toString() + " is entailed by "
                    + combine(rankedKB).toString());
            if (classicalReasoner.query(combine(rankedKB), formula)) {
                return true;
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
