package br.com.javaaps.view;

import java.util.ArrayList;
import java.util.List;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.util.ConsoleUtils;
import br.com.javaaps.util.FileUtils;

public class AlunosSubmenu extends Submenu {

	public AlunosSubmenu() {
		fileUtils = new FileUtils("alunos.csv");
	}
	
	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();
			
			// Definição do menu
			menu.append(String.format("[%d] Listar alunos \n", LISTAR_OPTION));
			menu.append(String.format("[%d] Cadastrar aluno \n", CADASTRAR_OPTION));
			menu.append(String.format("[%d] Editar aluno \n", EDITAR_OPTION));
			menu.append(String.format("[%d] Remover aluno \n", REMOVER_OPTION));
			menu.append(String.format("[%d] VOLTAR \n\n", VOLTAR_OPTION));
			
			menu.append("Escolha uma opção: ");
			
			System.out.print(menu.toString());
			
			opcaoEscolhida = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());
			
			switch(opcaoEscolhida) {
				case LISTAR_OPTION:
					listarAlunos();
					break;
				default:
					
			}
		}
	}
	
	/**
	 * Exibe todos os alunos armazenados no banco de dados 
	 */
	private void listarAlunos() {
		StringBuilder conteudo = new StringBuilder("\n\n");
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		fileUtils.getFileContent().forEach((String valor) -> {
			String[] linha = valor.split(";");
			alunos.add(new Aluno(linha[0], linha[1]));
		});
		
		alunos.forEach((Aluno aluno) -> {
			conteudo.append(aluno.toString() + "\n");
		});
		
		System.out.println(conteudo.toString());
	}
}
