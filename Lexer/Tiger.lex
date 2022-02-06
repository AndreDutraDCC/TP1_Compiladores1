package Lexer;

import Grammar.sym;
import java_cup.runtime.Symbol;
import ErrorHandle.ErrorMsg;

%%

%{

  public Yylex(java.io.Reader s, ErrorMsg e) {
    this(s);
    errorMsg = e;
  }

  private ErrorMsg errorMsg;

  private void newline() {
    errorMsg.newline(yychar);
  }

  private void err(String s) {
    errorMsg.error(yychar, s);
  }

  private Symbol symb(int tipo, Object valor) {   
    return new Symbol(tipo, yychar, yychar+yylength(), valor);
  }

  private Symbol symb(int tipo) {    
    return new Symbol(tipo, yychar, yychar+yylength());
  }

%}

%eofval{
  return symb(sym.EOF);
%eofval}

%public
%cup
%char

%state COMMENT

LETTER = [A-Za-z]
DIGIT = [0-9]
NEWLINE = (\n|\r|\r\n|\n\r)
DELIM = (" "|\t|\f)
WHITESPACE = {DELIM}+
INTEGER = {DIGIT}+
STRING = \"([^\"\\\n\r]|\\.)*\"
ID = (LETTER)(LETTER|DIGIT)*

%%

<YYINITIAL> "/*" {yybegin(COMMENT);}
<COMMENT> "*/" {yybegin(YYINITIAL);}
<COMMENT>  {NEWLINE} {newline();}
<COMMENT>   .  {}
<YYINITIAL> {NEWLINE} {newline();}
<YYINITIAL> {WHITESPACE} {}
<YYINITIAL> {DIGIT}+ {return symb(sym.NUM, new Integer(yytext()));}
<YYINITIAL> {STRING} {return symb(sym.STR, yytext());}
<YYINITIAL> "+" {return symb(sym.PLUS);}
<YYINITIAL> "-" {return symb(sym.MINUS);}
<YYINITIAL> "|" {return symb(sym.OR);}
<YYINITIAL> "*" {return symb(sym.TIMES);}
<YYINITIAL> "/" {return symb(sym.DIV);}
<YYINITIAL> "&" {return symb(sym.AND);}
<YYINITIAL> "=" {return symb(sym.EQ);}
<YYINITIAL> "<>" {return symb(sym.NEQ);}
<YYINITIAL> ">" {return symb(sym.GT);}
<YYINITIAL> "<" {return symb(sym.LT);}
<YYINITIAL> ">=" {return symb(sym.GTE);}
<YYINITIAL> "<=" {return symb(sym.LTE);}
<YYINITIAL> ":=" {return symb(sym.ATT);}
<YYINITIAL> "(" {return symb(sym.APAR);}
<YYINITIAL> ")" {return symb(sym.FPAR);}
<YYINITIAL> "[" {return symb(sym.ACOL);}
<YYINITIAL> "]" {return symb(sym.FCOL);}
<YYINITIAL> "{" {return symb(sym.ACHAV);}
<YYINITIAL> "}" {return symb(sym.FCHAV);}
<YYINITIAL> ":" {return symb(sym.DPONTOS);}
<YYINITIAL> "," {return symb(sym.VIRG);}
<YYINITIAL> "." {return symb(sym.PONTO);}
<YYINITIAL> ";" {return symb(sym.PVIRG);}
<YYINITIAL> "nil" {return symb(sym.NIL);}
<YYINITIAL> "of" {return symb(sym.OF);}
<YYINITIAL> "if" {return symb(sym.IF);}
<YYINITIAL> "then" {return symb(sym.THEN);}
<YYINITIAL> "else" {return symb(sym.ELSE);}
<YYINITIAL> "while" {return symb(sym.WHILE);}
<YYINITIAL> "do" {return symb(sym.DO);}
<YYINITIAL> "for" {return symb(sym.FOR);}
<YYINITIAL> "to" {return symb(sym.TO);}
<YYINITIAL> "break" {return symb(sym.BREAK);}
<YYINITIAL> "let" {return symb(sym.LET);}
<YYINITIAL> "in" {return symb(sym.IN);}
<YYINITIAL> "end" {return symb(sym.END);}
<YYINITIAL> "type" {return symb(sym.TYPE);}
<YYINITIAL> "array" {return symb(sym.ARRAY);}
<YYINITIAL> "var" {return symb(sym.VAR);}
<YYINITIAL> "function" {return symb(sym.FUNCTION);}
<YYINITIAL> ({LETTER}|"_")({LETTER}|{DIGIT}|"_")* {return symb(sym.ID, yytext());}
<YYINITIAL> . {err("Símbolo proibido na linguagem: "+yytext());}