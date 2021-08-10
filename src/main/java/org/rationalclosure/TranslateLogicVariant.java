package org.rationalclosure;

import org.tweetyproject.logics.pl.syntax.*;

import java.io.IOException;

import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;

public class TranslateLogicVariant {

    static String translate(String formula) throws IOException, ParserException {
        formula = reformatDefeasibleImplication(formula);
        formula.replaceAll("~", "!");
        formula = formula.replaceAll("&", "&&");
        // formula = formula.replaceAll("|", "||");
        formula = formula.replaceAll("<->", "<=>");
        formula = formula.replaceAll("->", "=>");
        return formula;
    }

    static String reformatDefeasibleImplication(String formula) {
        int index = formula.indexOf("~>");
        formula = "(" + formula.substring(0, index) + ") => (" + formula.substring(index + 2, formula.length()) + ")";
        return formula;
    }
}
