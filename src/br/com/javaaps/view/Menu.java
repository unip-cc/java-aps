package br.com.javaaps.view;

import br.com.javaaps.util.ConsoleUtils;

public class Menu {
	
	public static final int ALUNOS_OPTION = 1;
	public static final int CURSOS_OPTION = 2;
	public static final int EXIT_OPTION = 3;
	
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
			
			opcaoEscolhida = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());
			
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
}
