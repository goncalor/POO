
jar:
	javac hello/HelloWorld.java
	jar cef hello.HelloWorld hello.jar hello/

run:
	java -jar hello.jar
	#java hello.HelloWorld

.PHONY clean:
clean:
	$(RM) hello.jar hello/HelloWorld.class
