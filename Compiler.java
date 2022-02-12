import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;
import Lexer.*;
import Grammar.*;
import ErrorHandle.*;
import AbsSyn.*;
import Semant.Semant;
import Intermediate.*;

public class Compiler {
	public static final String division = "===============================================================================";

	public static void displayUsageError(String errorMessage) {
		System.out.println("Erro: " + errorMessage);
		System.out.println("\n" + division);
		System.out.println("Uso correto: java Compiler nome_do_arquivo.tig [opções]\n");
		System.out.println("Em que as opções são:");
		System.out.println("-absyn      Imprime na saída parão a representação da árvore de sintaxe abstrata");
		System.out.println("-listinput  Imprime na saída padrão a listagem do código fonte de entrada");
		System.out.println("-intermcode Imprime na saída padrão o código intermediário gerado");
		System.out.println(division);
	}

	public static Arguments parseCmdArgs(String[] args) {
		Arguments res = new Arguments();
		if(args.length < 1) {
			displayUsageError("Nenhum arquivo de entrada foi fornecido!");
			return null;
		}
		res.filePath = args[0];
		for(int i = 1; i < args.length; i++) {
			if(args[i].equals("-absyn")) {
				res.displaySyntaxTree = true;
			}else if(args[i].equals("-listinput")) {
				res.listSource = true;
			}else if(args[i].equals("-intermcode")) {
				res.displayIntermediate = true;
			}else {
				displayUsageError("O argumento \"" + args[i] + "\" é inválido!");
				return null;
			}
		}
		return res;
	}

	public static Scanner readInputFile(String filePath) throws Exception {
		Scanner fileReader;
		try {
			fileReader = new Scanner(new File(filePath));
		}catch(FileNotFoundException e) {
			throw new Exception("Erro: arquivo \"" + filePath + "\" não encontrado!");
		}catch(Throwable e) {
			throw new Exception("Erro: ocorreu um erro ao processar o arquivo: \"" + filePath + "\"!");
		}
		return fileReader;
	}

	public static void main(String[] args) throws Exception{
		Arguments options = parseCmdArgs(args);
		if(options == null)
			return;
		Scanner inputReader = readInputFile(options.filePath);
		String source = "";
		while(inputReader.hasNextLine())
			source += inputReader.nextLine() + "\n";
		inputReader.close();
		ErrorMsg err = new ErrorMsg(options.filePath);
		StringReader codeReader = new StringReader(source);
		Yylex lexer = new Yylex(codeReader, err);
		Exp res;
		parser p = new parser(lexer, err);
		if(options.listSource) {
			System.out.println("Código fonte:\n");
			System.out.println(source);
			System.out.println(division);
		}
		try{
			res = (Exp) p.parse().value;
		}catch(Exception e){
			return;
		}
		System.out.println("\nSintaxe Correta!\n");
		if(options.displaySyntaxTree) {
			System.out.println("Árvore de sintaxe abstrata:");
			res.print("");
			System.out.println("\n" + division);
		}
		Semant sem = new Semant(res, err);
		Generator codeTree = sem.translateProgram();
		if(codeTree == null){
			return;
		}
		System.out.println("\nSemântica Correta!\n");
		if(options.displayIntermediate) {
			System.out.println("Código intermediário:\n");
			System.out.println(codeTree.getVariableAssociations());
			System.out.println(codeTree.getParameterAssociations());
			System.out.println(codeTree.genIntermediateCode());
			System.out.println(codeTree.getDataLabelsString());
		}
	}
};
