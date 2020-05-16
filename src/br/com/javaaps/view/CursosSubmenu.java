package br.com.javaaps.view;

import br.com.javaaps.util.ConsoleUtils;

public class CursosSubmenu extends Submenu {

	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();
			
			// Definição do menu
			menu.append(String.format("[%d] Listar cursos \n", LISTAR_OPTION));
			menu.append(String.format("[%d] Cadastrar curso \n", CADASTRAR_OPTION));
			menu.append(String.format("[%d] Editar curso \n", EDITAR_OPTION));
			menu.append(String.format("[%d] Remover curso \n", REMOVER_OPTION));
			menu.append(String.format("[%d] VOLTAR \n\n", VOLTAR_OPTION));
			
			menu.append("Escolha uma opção: ");
			
			System.out.print(menu.toString());
			
			opcaoEscolhida = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());
		}
	}
}
