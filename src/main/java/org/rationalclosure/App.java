package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.IOException;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ParserException {
        PlBeliefSet bs = new PlBeliefSet();
        PlParser parser = new PlParser();
        PlFormula formula2 = (PlFormula) parser.parseFormula("l => b");
        PlFormula formula3 = (PlFormula) parser.parseFormula("l => !f");
        PlFormula formula1 = (PlFormula) parser.parseFormula("fd => b");
        PlFormula formula4 = (PlFormula) parser.parseFormula("fd => l");
        PlFormula formula5 = (PlFormula) parser.parseFormula("b => f");
        bs.add(formula1);
        bs.add(formula2);
        bs.add(formula3);
        bs.add(formula4);
        bs.add(formula5);
        RationalReasoner reasoner = new RationalReasoner(bs, (PlFormula) parser.parseFormula("fd => !f"));
    }
}