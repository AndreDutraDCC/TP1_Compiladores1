abssyntarget = 
absdeps = AbsSyn/AbsNode.java AbsSyn/DecFunc.java AbsSyn/Dec.java AbsSyn/DecList.java AbsSyn/DecType.java AbsSyn/DecVar.java AbsSyn/ExpArray.java AbsSyn/ExpAttr.java AbsSyn/ExpBreak.java AbsSyn/ExpCall.java AbsSyn/ExpFor.java AbsSyn/ExpIf.java AbsSyn/ExpInt.java AbsSyn/Exp.java AbsSyn/ExpLet.java AbsSyn/ExpList.java AbsSyn/ExpNil.java AbsSyn/ExpOp.java AbsSyn/ExpRec.java AbsSyn/ExpSeq.java AbsSyn/ExpString.java AbsSyn/ExpWhile.java AbsSyn/FieldExpList.java AbsSyn/FieldTyList.java AbsSyn/TyArray.java AbsSyn/Ty.java AbsSyn/TyName.java AbsSyn/TyRec.java AbsSyn/VarField.java AbsSyn/VarIndexed.java AbsSyn/Var.java AbsSyn/VarSimple.java 
all: main

main: Analisador.class 
	javac -cp Grammar/java-cup-11b-runtime.jar:. Main.java

Analisador.class: Analisador.java Grammar/parser.class Grammar/sym.class Lexer/Yylex.class ErrorHandle/ErrorMsg.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. Analisador.java

Grammar/parser.class: Grammar/parser.java Lexer/Yylex.class Grammar/sym.class AbsSyn/*.class ErrorHandle/ErrorMsg.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. Grammar/parser.java

Lexer/Yylex.class: Lexer/Yylex.java Grammar/sym.class ErrorHandle/ErrorMsg.class
	javac -cp Grammar/java-cup-11b-runtime.jar:. Lexer/Yylex.java

ErrorHandle/ErrorMsg.class: ErrorHandle/ErrorMsg.java
	javac $<

AbsSyn/*.class: AbsSyn/*.java Grammar/sym.class
	javac AbsSyn/*.java

Grammar/sym.class: Grammar/sym.java
	javac $<

Lexer/Yylex.java: Lexer/Tiger.lex
	java JLex.Main $<
	mv Lexer/Tiger.lex.java Lexer/Yylex.java

Grammar/sym.java Grammar/parser.java: Grammar/Grm.cup 
	cd Grammar; java -jar java-cup-11b.jar -expect 2 Grm.cup

clean: 
	rm -f Lexer/*.java Lexer/*.class Grammar/parser.java Grammar/*.class *.class ErrorHandle/*.class AbsSyn/*.class

	