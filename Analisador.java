import java.io.File;
import java.io.StringReader;
import Lexer.*;
import Grammar.*;
import ErrorHandle.*;
import AbsSyn.*;

public class Analisador{
	public static void analisar(String filename) throws Exception{
		java.util.Scanner file_reader;
		String source_code = "";
		
		file_reader = new java.util.Scanner(new File(filename));

		System.out.println("Código fonte do arquivo "+filename+":\n");
		while(file_reader.hasNextLine()){
			String line = file_reader.nextLine();
				System.out.println(line);
				source_code+=line+'\n';
		}
		file_reader.close();
		
		ErrorMsg err = new ErrorMsg(filename);
		StringReader code_reader = new StringReader(source_code);
		Yylex lexer = new Yylex(code_reader,err);
		
		System.out.println("\n===============================================================================");
		System.out.println("Árvore de sintaxe abstrata:\n");
		
		parser parser_obj = new parser(lexer,err);
		Exp res;
		try{
			res = (Exp) parser_obj.parse().value;
			res.print("");
		}
		catch(Exception e){
			System.out.println("\nErro de Sintaxe.\n");
			return;}
		System.out.println("\nSintaxe Correta.\n");
		
	}
	public static void main(String[] args) throws Exception{
		for(String arg: args){
			analisar(arg);
			System.out.println("===============================================================================");
		}
	}
};
