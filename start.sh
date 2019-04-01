#!/usr/bin/env ksh
rm App.jar 2> /dev/null;
rm -r build 2> /dev/null;

mkdir build;
touch autosave.csv;

echo "Compiling...";
javac18 -sourcepath src -d build src/lab/Main.java -encoding UTF-8 &&
(
  echo "Creating jar file...";
  jar cfm App.jar src/MANIFEST.MF -C build .
)