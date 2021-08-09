package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.*;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;

public class BaseRank {

    static ArrayList<PlBeliefSet> rank(PlBeliefSet kb) {
        // System.out.println("Initial KB: " + kb.toString());
        SimplePlReasoner reasoner = new SimplePlReasoner();

        ArrayList<PlBeliefSet> rankedKB = new ArrayList<PlBeliefSet>();

        PlBeliefSet currentMaterialisation = kb;

        PlBeliefSet prevMaterialisation = new PlBeliefSet();

        while (!currentMaterialisation.equals(prevMaterialisation)) {
            // if (currentMaterialisation.size() == 0) {
            // break;
            // }
            prevMaterialisation = currentMaterialisation;

            currentMaterialisation = new PlBeliefSet();
            for (PlFormula f : prevMaterialisation) {
                if (f.toString().contains("=>")) {
                    // System.out.println("formula is an implication: " + f.toString());
                    // System.out.println("antecedent of implication " + f.toString() + "is "
                    // + ((Implication) f).getFormulas().getFirst().toString());
                    // System.out.println("the negation of this antecedent is: "
                    // + new Negation(((Implication) f).getFormulas().getFirst()).toString());
                    // System.out.println("We are checking to see if "
                    // + new Negation(((Implication) f).getFormulas().getFirst()).toString()
                    // + " is entailed by the materialisation: " + prevMaterialisation.toString());
                    if (reasoner.query(prevMaterialisation, new Negation(((Implication) f).getFormulas().getFirst()))) {
                        currentMaterialisation.add(f);
                    }
                }
            }
            // System.out.println("Current materialisation size: " +
            // Integer.toString(currentMaterialisation.size()));
            PlBeliefSet newRank = new PlBeliefSet();
            for (PlFormula form : prevMaterialisation) {
                newRank.add(form);
            }
            newRank.removeAll(currentMaterialisation);
            if (newRank.size() != 0) {
                rankedKB.add(newRank);
            }
        }
        if (currentMaterialisation.size() != 0) {
            rankedKB.add(currentMaterialisation);
        }

        for (PlBeliefSet rank : rankedKB) {
            System.out.println("Rank " + Integer.toString(rankedKB.indexOf(rank)) + ":" + rank.toString());
        }
        return rankedKB;
    }

}
