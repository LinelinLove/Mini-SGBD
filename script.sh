#!/bin/bash

javac -d ./CODE/bin ./CODE/src/bdd/*.java && java -cp ./CODE/bin/ bdd.Main $(realpath ./BD)