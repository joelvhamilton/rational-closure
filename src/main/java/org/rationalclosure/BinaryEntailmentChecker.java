package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;

public class BinaryEntailmentChecker {

    static int rankFromWhichToRemove = -1;
    static int prevmid;

    Boolean checkEntailmentBinarySearch(PlBeliefSet[] originalRankedKB, PlFormula formula, int left, int right,
            PlFormula negationOfAntecedent) {
        SatSolver.setDefaultSolver(new Sat4jSolver());
        SatReasoner classicalReasoner = new SatReasoner();
        PlBeliefSet[] rankedKB = originalRankedKB.clone();
        if (right > left) {
            int mid = left + ((right - left) / 2);
            // if removing middle one and the ones above it, the negation of the antecedent
            // is still entailed, then its in the top half.
            System.out.println("Trying rank " + Integer.toString(mid));
            PlBeliefSet[] rankedKBArray = Arrays.copyOfRange(rankedKB, mid + 1, rankedKB.length);
            PlBeliefSet combinedRankedKBArray = combine(rankedKBArray);
            if (classicalReasoner.query(combinedRankedKBArray, negationOfAntecedent)) {
                System.out.println("The rank we\'re looking for is in the bottom half of the remaining ranks.");
                return checkEntailmentBinarySearch(rankedKB, formula, mid + 1, right, negationOfAntecedent);
            }
            // we now know that removing the middle one and those above it results in the
            // negation of the antecedent not being entailed.
            // we must check whether or not putting it back in means the negation of the
            // antecdent is entailed.
            else {
                if (classicalReasoner.query(combine(Arrays.copyOfRange(rankedKB, mid, rankedKB.length)),
                        negationOfAntecedent)) {
                    rankFromWhichToRemove = mid;
                    System.out.println("We\'ve found the rank from which we need to remove the above ranks! It's rank "
                            + Integer.toString(mid));
                } else { // removing it still means the negation of the antecedent is not entailed. we
                    // know its in the bottom half.
                    System.out.println("The rank we\'re looking for is in the top half of the remaining ranks.");
                    return checkEntailmentBinarySearch(rankedKB, formula, left, mid, negationOfAntecedent);
                }
            }
        } else {
            return true;
        }

        if (rankFromWhichToRemove + 1 < rankedKB.length) {
            System.out.println("We now check whether or not the formula" + formula.toString()
                    + " is entailed by the statements in the remaining ranks.");
            if (classicalReasoner.query(
                    combine(Arrays.copyOfRange(rankedKB, rankFromWhichToRemove + 1, rankedKB.length)), formula)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    static PlBeliefSet combine(PlBeliefSet[] ranks) {
        PlBeliefSet combined = new PlBeliefSet();
        for (PlBeliefSet rank : ranks) {
            combined.addAll(rank);
        }
        return combined;
    }

}
