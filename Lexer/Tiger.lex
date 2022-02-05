package Lexer;

import Grammar.sym;
import java_cup.runtime.Symbol;

%%

%public
%cup

%eofval{
return new Symbol(sym.EOF);
%eofval}

%state COMMENT

LETTER = [A-Za-z]
DIGIT = [0-9]
DELIM = (" "|\n|\t)
STOKEN = {DELIM}+
INTEGER = {DIGIT}+
STRING = \"([^\"\\\n\r]|\\.)*\"
ID = (LETTER)(LETTER|DIGIT)*

%%

<YYINITIAL> "/*" {yybegin(COMMENT);}
<COMMENT> (.|"\n") {}
<COMMENT> "*/" {yybegin(YYINITIAL);}
<YYINITIAL> {DIGIT}+ {return new Symbol(sym.NUM, new Integer(yytext()));}
<YYINITIAL> {STRING} {return new Symbol(sym.STR, yytext());}
<YYINITIAL> {STOKEN} {}
<YYINITIAL> "+" {return new Symbol(sym.PLUS);}
<YYINITIAL> "-" {return new Symbol(sym.MINUS);}
<YYINITIAL> "|" {return new Symbol(sym.OR);}
<YYINITIAL> "*" {return new Symbol(sym.TIMES);}
<YYINITIAL> "/" {return new Symbol(sym.DIV);}
<YYINITIAL> "&" {return new Symbol(sym.AND);}
<YYINITIAL> "=" {return new Symbol(sym.EQ);}
<YYINITIAL> "<>" {return new Symbol(sym.NEQ);}
<YYINITIAL> ">" {return new Symbol(sym.GT);}
<YYINITIAL> "<" {return new Symbol(sym.LT);}
<YYINITIAL> ">=" {return new Symbol(sym.GTE);}
<YYINITIAL> "<=" {return new Symbol(sym.LTE);}
<YYINITIAL> ":=" {return new Symbol(sym.ATT);}
<YYINITIAL> "(" {return new Symbol(sym.APAR);}
<YYINITIAL> ")" {return new Symbol(sym.FPAR);}
<YYINITIAL> "[" {return new Symbol(sym.ACOL);}
<YYINITIAL> "]" {return new Symbol(sym.FCOL);}
<YYINITIAL> "{" {return new Symbol(sym.ACHAV);}
<YYINITIAL> "}" {return new Symbol(sym.FCHAV);}
<YYINITIAL> ":" {return new Symbol(sym.DPONTOS);}
<YYINITIAL> "," {return new Symbol(sym.VIRG);}
<YYINITIAL> "." {return new Symbol(sym.PONTO);}
<YYINITIAL> ";" {return new Symbol(sym.PVIRG);}
<YYINITIAL> "nil" {return new Symbol(sym.NIL);}
<YYINITIAL> "of" {return new Symbol(sym.OF);}
<YYINITIAL> "if" {return new Symbol(sym.IF);}
<YYINITIAL> "then" {return new Symbol(sym.THEN);}
<YYINITIAL> "else" {return new Symbol(sym.ELSE);}
<YYINITIAL> "while" {return new Symbol(sym.WHILE);}
<YYINITIAL> "do" {return new Symbol(sym.DO);}
<YYINITIAL> "for" {return new Symbol(sym.FOR);}
<YYINITIAL> "to" {return new Symbol(sym.TO);}
<YYINITIAL> "break" {return new Symbol(sym.BREAK);}
<YYINITIAL> "let" {return new Symbol(sym.LET);}
<YYINITIAL> "in" {return new Symbol(sym.IN);}
<YYINITIAL> "end" {return new Symbol(sym.END);}
<YYINITIAL> "type" {return new Symbol(sym.TYPE);}
<YYINITIAL> "array" {return new Symbol(sym.ARRAY);}
<YYINITIAL> "var" {return new Symbol(sym.VAR);}
<YYINITIAL> "function" {return new Symbol(sym.FUNCTION);}
<YYINITIAL> ({LETTER}|"_")({LETTER}|{DIGIT}|"_")* {return new Symbol(sym.ID, yytext());}
