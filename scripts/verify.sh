#!/usr/bin/env bash
# Replays the recorded gameplay scenarios and compares them with the baseline
# taken from the code as it was before any refactoring.
set -e
cd "$(dirname "$0")/.."
rm -rf out && mkdir -p out/main out/test
javac -encoding UTF-8 -Xlint:none -d out/main src/main/java/*.java
cp -r src/main/java/img out/main/
javac -encoding UTF-8 -Xlint:none -cp out/main -d out/test src/test/java/*.java
java -Djava.awt.headless=true -cp out/main:out/test BehaviourCheck
java -Djava.awt.headless=true -cp out/main:out/test PatternChecks
java -cp out/main:out/test WindowCheck
