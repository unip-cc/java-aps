package br.com.javaaps.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUtils {
	
	private static Scanner scan;

	/**
	 * Solicita a digita��o do usu�rio no console
	 * @return
	 */
	public static String getValorDigitado() {
		scan = new Scanner(System.in);
		String valor = "";
		
		try{
			valor = scan.nextLine();
		} catch(NoSuchElementException ex) {
			ex.getMessage();
		}
		
		return valor;
	}
	
	/**
	 * Tenta converter um dado do tipo String p/ integer (realiza tratativas de erro)
	 * @param value
	 * @return
	 */
	public static int tryParseToInt(String value) {
		int valor = 0;
		
		try {
			valor = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			ConsoleUtils.showError("O valor digitado não parece ser um número inteiro.. verifique e tente novamente!");
		}
		
		return valor;
	}
	
	/**
	 * Tenta converter um dado do tipo String p/ double (realiza tratativas de erro)
	 * @param value
	 * @return
	 */
	public static double tryParseToDouble(String value) {
		double valor = 0;
		
		try {
			valor = Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			ConsoleUtils.showError("O valor digitado não parece ser um número decimal.. verifique e tente novamente!");
		}
		
		return valor;
	}
	
	
	/**
	 * Exibe um erro personalizado no console
	 * @param errorMessage
	 */
	public static void showError(String errorMessage) {
		System.err.println(String.format("\n\n[ERRO] => %s", errorMessage));
		System.err.println("Precione qualquer tecla para continuar...");
		
		ConsoleUtils.getValorDigitado();
	}
	
	/**
	 * Exibe uma mensagem informativa personalizada
	 * @param errorMessage
	 */
	public static void showInfo(String message) {
		System.out.println(String.format("\n[INFO] => %s", message));
		System.out.println("Precione qualquer tecla para continuar...");
		
		ConsoleUtils.getValorDigitado();
	}
	
	
}
