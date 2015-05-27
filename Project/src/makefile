compile:
	find -name "*.class" -delete
	find -name "*.java" > tmp.txt
	javac @tmp.txt -target 1.7 -source 1.7 -verbose
	jar cef project.Main grupo22.jar project

run:
	java -jar grupo22.jar
