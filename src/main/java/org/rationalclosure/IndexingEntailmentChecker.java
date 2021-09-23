package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;

public class IndexingEntailmentChecker {

    HashMap<PlFormula, Integer> antecedentNegationRanksToRemoveFrom = new HashMap<PlFormula, Integer>();

    Boolean checkEntailment(ArrayList<PlBeliefSet> originalRankedKB, PlFormula formula) {
        SatSolver.setDefaultSolver(new Sat4jSolver());
        SatReasoner classicalReasoner = new SatReasoner();
        PlFormula negationOfAntecedent = new Negation(((Implication) formula).getFormulas().getFirst());
        ArrayList<PlBeliefSet> rankedKB = (ArrayList<PlBeliefSet>) originalRankedKB.clone();
        PlBeliefSet combinedRankedKB = combine(rankedKB);
        if (antecedentNegationRanksToRemoveFrom.get(negationOfAntecedent) != null) {
            System.out.println("We know to remove rank "
                    + Integer.toString(antecedentNegationRanksToRemoveFrom.get(negationOfAntecedent))
                    + " and all ranks above it.");
            for (int i = 0; i < (antecedentNegationRanksToRemoveFrom.get(negationOfAntecedent)); i++) {
                rankedKB.remove(rankedKB.get(0));
            }
        } else {
            while (combinedRankedKB.size() != 0) {
                System.out.println("We are checking whether or not " + negationOfAntecedent.toString()
                        + " is entailed by: " + combinedRankedKB.toString());
                if (classicalReasoner.query(combinedRankedKB, negationOfAntecedent)) {
                    System.out.println("It is! so we remove " + rankedKB.get(0).toString());
                    combinedRankedKB.removeAll(rankedKB.get(0));
                    rankedKB.remove(rankedKB.get(0));
                } else {
                    System.out.println("It is not!");
                    antecedentNegationRanksToRemoveFrom.put(negationOfAntecedent,
                            (originalRankedKB.size() - rankedKB.size()));
                    break;
                }
            }
        }

        if (combinedRankedKB.size() != 0) {
            System.out.println("We now check whether or not the formula" + formula.toString() + " is entailed by "
                    + combinedRankedKB.toString());
            if (classicalReasoner.query(combinedRankedKB, formula)) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("There would then be no ranks remaining, which means the knowledge base entails "
                    + negationOfAntecedent.toString() + ", and thus it entails " + formula.toString()
                    + ", so we know the defeasible counterpart of this implication is also entailed!");
            return true;
        }
    }

    static PlBeliefSet combine(ArrayList<PlBeliefSet> ranks) {
        PlBeliefSet combined = new PlBeliefSet();
        for (PlBeliefSet rank : ranks) {
            combined.addAll(rank);
        }
        return combined;
    }

}
