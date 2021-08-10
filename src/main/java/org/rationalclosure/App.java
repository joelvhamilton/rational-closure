package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.IOException;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ParserException {
        PlBeliefSet bs = new PlBeliefSet();
        PlParser parser = new PlParser();
        // Penguins have wings example
        // PlFormula formula2 = (PlFormula) parser.parseFormula("b => f");
        // PlFormula formula3 = (PlFormula) parser.parseFormula("b => w");
        // PlFormula formula1 = (PlFormula) parser.parseFormula("p => !f");
        // PlFormula formula4 = (PlFormula) parser.parseFormula("r => b");
        // PlFormula formula5 = (PlFormula) parser.parseFormula("p => b");
        // PlBeliefSet classicalSet = new PlBeliefSet();
        // classicalSet.add(formula4);
        // classicalSet.add(formula5);
        // bs.add(formula1);
        // bs.add(formula2);
        // bs.add(formula3);
        // bs.add(formula4);
        // bs.add(formula5);
        // RationalReasoner reasoner = new RationalReasoner(bs, (PlFormula)
        // parser.parseFormula("p => w"), classicalSet);

        // flying Dutchman Example
        PlFormula formula2 = (PlFormula) parser.parseFormula("l => b");
        PlFormula formula3 = (PlFormula) parser.parseFormula("l => !f");
        PlFormula formula1 = (PlFormula) parser.parseFormula("fd => b");
        PlFormula formula4 = (PlFormula) parser.parseFormula("fd => l");
        PlFormula formula5 = (PlFormula) parser.parseFormula("b => f");
        PlBeliefSet classicalSet = new PlBeliefSet();
        bs.add(formula1);
        bs.add(formula2);
        bs.add(formula3);
        bs.add(formula4);
        bs.add(formula5);
        RationalReasoner reasoner = new RationalReasoner(bs, (PlFormula) parser.parseFormula("fd => !f"), classicalSet);

    }
}