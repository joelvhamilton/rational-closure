#!/usr/bin/env bash
echo "generating KBs for ranks comparison"
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 100 -s 200 -t defeasible -d uniform -o ranks100.txt
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 50 -s 100 -t defeasible -d uniform -o ranks50.txt
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 10 -s 20 -t defeasible -d uniform -o ranks10.txt
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 3 -s 6 -t defeasible -d uniform -o ranks3.txt

echo "generating KBs for distribution comparison"
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 100 -s 500 -t defeasible -d uniform -o uniform.txt
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 100 -s 500 -t defeasible -d normal -o normal.txt
scala ../../../Downloads/skbgen-assembly-1.5.jar -r 100 -s 500 -t defeasible -d exponential -o exponential.txt

echo "running tests for ranks comparison"
echo "100 ranks tests"
mvn clean
mvn package
java -cp target/rationalclosure-1.0-SNAPSHOT-jar-with-dependencies.jar org.rationalclosure.TimedReasonerComparison ranks100.txt ranks100queries_DIFFANTE.txt
echo "ranks100queries_SAME_ANTE.txt"
echo "ranks100queries_half_repeated_ante.txt"
echo "ranks100queries_1strank.txt"
echo "ranks100queries_100thrank.txt"
echo "done"

