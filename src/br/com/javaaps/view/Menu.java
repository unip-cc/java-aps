package br.com.javaaps.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {
	
	public static final int ALUNOS_OPTION = 1;
	public static final int CURSOS_OPTION = 2;
	public static final int EXIT_OPTION = 3;

	private Scanner scan = new Scanner(System.in);
	
	public Menu() {

	}
	
	/**
	 * Exibe o menu inicial da aplicação
	 */
	public void show()
	{
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != EXIT_OPTION)
		{
			System.out.println("\n");
			
			Submenu submenu = null;
			StringBuilder menu = new StringBuilder();
			
			menu.append("Seja bem vindo ao sistema de gerenciamento da Universidade Amazônia! \n\n");
			
			// Definição do menu
			menu.append(String.format("[%d] Alunos \n", ALUNOS_OPTION));
			menu.append(String.format("[%d] Cursos \n", CURSOS_OPTION));
			menu.append(String.format("[%d] Sair \n\n", EXIT_OPTION));
			
			menu.append("Escolha uma opção: ");
			
			System.out.print(menu.toString());
			
			opcaoEscolhida = this.tryParseToInt(this.getValorDigitado());
			
			// Realiza o direcionamento, de acordo com a opção escolhida
			switch(opcaoEscolhida) {
				case ALUNOS_OPTION:
					submenu = new AlunosSubmenu();
					break;
				case CURSOS_OPTION:
					submenu = new CursosSubmenu();
					break;
				default:
					
			}
			
			if (submenu != null) {
				submenu.showSubmenu();
			}
		}
		
		System.out.println("\n\nEncerrando aplicação...");
	}
	
	/**
	 * Solicita a digitação do usuário no console
	 * @return
	 */
	protected String getValorDigitado() {
		String valor = "";
		
		try{
			valor = this.scan.nextLine();
		} catch(NoSuchElementException ex) {
			ex.getMessage();
		}
		
		return valor;
	}
	
	/**
	 * Tenta converter um dado do tipo Stirng p/ integer (realiza tratativas de erro)
	 * @param value
	 * @return
	 */
	protected int tryParseToInt(String value) {
		int valor = 0;
		
		try {
			valor = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			this.showError("O valor digitado não parece ser um número.. verifique e tente novamente!");
		}
		
		return valor;
	}
	
	/**
	 * Exibe um erro personalizado no console
	 * @param errorMessage
	 */
	protected void showError(String errorMessage) {
		System.err.println(String.format("\n\n[ERRO] => %s", errorMessage));
		System.err.println("Precione qualquer tecla para continuar...");
		
		this.getValorDigitado();
	}
}
