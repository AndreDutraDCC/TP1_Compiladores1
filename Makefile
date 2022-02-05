all: main

main: Analisador.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. Main.java

Analisador.class: Analisador.java Grammar/parser.class Grammar/sym.class Lexer/Yylex.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. $<

Grammar/parser.class: Grammar/parser.java Lexer/Yylex.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. $<

Lexer/Yylex.class: Lexer/Yylex.java Grammar/sym.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. $<

Lexer/Yylex.java: Lexer/Tiger.lex
	java -cp Lexer JLex.Main $<
	mv Lexer/Tiger.lex.java Lexer/Yylex.java

Grammar/sym.class: Grammar/sym.java
	javac $<

Grammar/sym.java Grammar/parser.java: Grammar/Grm.cup
	cd Grammar; java -jar java-cup-11b.jar -expect 2 Grm.cup

clean: 
	rm -f Lexer/*.java Lexer/*.class Grammar/*.java Grammar/*.class *.class

	