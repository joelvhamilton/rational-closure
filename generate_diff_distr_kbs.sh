#!/usr/bin/env bash
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 50 -s 500 -t defeasible -d uniform -o dist_uni_50.txt --verbose
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 50 -s 500 -t defeasible -d normal -o dist_normal_50.txt --verbose
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 50 -s 500 -t defeasible -d exponential -o dist_exponential_50.txt --verbose

java -cp target/rationalclosure-1.0-SNAPSHOT-jar-with-dependencies.jar org.rationalclosure.TimedReasonerComparison dist_uni_50.txt dist_50queries_DIFFANTE.txt dist_50queries_SAME_ANTE.txt dist_50queries_half_repeated_ante.txt dist_50queries_1strank.txt dist_50queries_50thrank.txt
java -cp target/rationalclosure-1.0-SNAPSHOT-jar-with-dependencies.jar org.rationalclosure.TimedReasonerComparison dist_normal_50.txt dist_50queries_DIFFANTE1.txt dist_50queries_SAME_ANTE1.txt dist_50queries_half_repeated_ante1.txt dist_50queries_1strank1.txt dist_50queries_50thrank1.txt
java -cp target/rationalclosure-1.0-SNAPSHOT-jar-with-dependencies.jar org.rationalclosure.TimedReasonerComparison dist_exponential_50.txt dist_50queries_DIFFANTE2.txt dist_50queries_SAME_ANTE2.txt dist_50queries_half_repeated_ante2.txt dist_50queries_1strank2.txt dist_50queries_50thrank2.txt
