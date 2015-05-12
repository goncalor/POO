
jar:
	javac stringlist/*.java
	jar cef stringlist.StringList stringlist.jar stringlist/

run:
	java -jar stringlist.jar
	#java stringlist.StringList

.PHONY clean:
clean:
	$(RM) stringlist.jar stringlist/*.class
