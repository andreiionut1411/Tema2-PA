#! /bin/bash

build:
	javac Fortificatii.java Beamdrone.java Curse.java

run-p2:
	java Fortificatii

run-p3:
	java Beamdrone

run-p4:
	java Curse

clean:
	rm -f *.class
