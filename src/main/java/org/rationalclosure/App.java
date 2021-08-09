package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.IOException;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;

import org.tweetyproject.logics.pl.sat.SimpleDpllSolver;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ParserException {
        PlBeliefSet bs = new PlBeliefSet();
        // PlFormula hw = new Proposition("HelloWorld");
        SimpleDpllSolver solver = new SimpleDpllSolver();
        PlParser parser = new PlParser();
        PlFormula formula1 = (PlFormula) parser.parseFormula("(a && b) || c");
        PlFormula formula2 = (PlFormula) parser.parseFormula("!c");
        bs.add(formula1);
        bs.add(formula2);
        SimplePlReasoner reasoner = new SimplePlReasoner();
        Boolean entailed = reasoner.query(bs, (PlFormula) parser.parseFormula("d"));
        System.out.println(entailed);
    }
}
