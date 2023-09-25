@ECHO OFF

set mypath=%cd%/BD

javac -d ./CODE/bin ./CODE/src/bdd/*.java && java -cp ./CODE/bin/ bdd.Main %mypath%
:: javac -d ./CODE/bin ./CODE/src/bdd/*.java && java -cp ./CODE/bin/ bdd.DiskManagerTests %mypath%
:: javac -d ./CODE/bin ./CODE/src/bdd/*.java && java -cp ./CODE/bin/ bdd.BufferManagerTests %mypath%
:: javac -d ./CODE/bin ./CODE/src/bdd/*.java && java -cp ./CODE/bin/ bdd.RecordTests %mypath%
:: javac -d ./CODE/bin ./CODE/src/bdd/*.java && java -cp ./CODE/bin/ bdd.FileManagerTest2 %mypath%
pause