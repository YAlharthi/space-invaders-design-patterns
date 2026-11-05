#!/usr/bin/env bash
# Compiles the game with plain javac and starts it.
set -e
cd "$(dirname "$0")/.."
rm -rf out/main && mkdir -p out/main
javac -encoding UTF-8 -Xlint:none -d out/main src/main/java/*.java
cp -r src/main/java/img out/main/
java -cp out/main SpaceInvaders
