package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.util.*;

import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;

public class BaseRank {

    static ArrayList<PlBeliefSet> rank(PlBeliefSet kb) {
        SimplePlReasoner reasoner = new SimplePlReasoner();

        ArrayList<PlBeliefSet> rankedKB = new ArrayList<PlBeliefSet>();

        PlBeliefSet currentMaterialisation = kb;

        PlBeliefSet prevMaterialisation = new PlBeliefSet();

        while (!currentMaterialisation.equals(prevMaterialisation)) {
            prevMaterialisation = currentMaterialisation;
            currentMaterialisation = new PlBeliefSet();
            for (PlFormula f : prevMaterialisation) {
                if (f.toString().contains("=>")) {
                    if (reasoner.query(prevMaterialisation, new Negation(((Implication) f).getFormulas().getFirst()))) {
                        currentMaterialisation.add(f);
                    }
                }
            }
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
