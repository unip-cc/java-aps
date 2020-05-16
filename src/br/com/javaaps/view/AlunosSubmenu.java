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
				case CADASTRAR_OPTION:
					cadastrarAluno();
					break;
				default:
					
			}
		}
	}
	
	/**
	 * Exibe todos os alunos armazenados no banco de dados 
	 */
	private void listarAlunos() {
		System.out.println();
		
		List<Aluno> alunos = loadAlunos();
		
		for(Aluno aluno : alunos) {
			System.out.println(aluno);
		}
	}
	
	/**
	 * Cadastra um novo aluno na base de dados
	 */
	private void cadastrarAluno() {
		System.out.println();
				
		String id;
		String nome;
		
		System.out.print("Identificador: ");
		id = ConsoleUtils.getValorDigitado().trim();
		
		System.out.print("Nome: ");
		nome = ConsoleUtils.getValorDigitado().trim();
		
		// Cadastra o aluno na base de dados
		save(new Aluno(id, nome));
		
		ConsoleUtils.showInfo(String.format("Aluno %s cadastrado com sucesso!", nome));
	}
	
	/**
	 * Retorna uma lista de alunos que estão armazenados na base de dados 
	 * @return
	 */
	private List<Aluno> loadAlunos() {
		List<Aluno> dados = new ArrayList<Aluno>();
		
		for(String valor : fileUtils.getFileContent()) {
			String[] linha = valor.split(";");
			dados.add(new Aluno(linha[0], linha[1]));
		}
		
		return dados;
	}
	
	/**
	 * Realiza o cadastro de um novo aluno ba base dados
	 */
	private void save(Aluno aluno) {
		String content = aluno.getId() + ";" + aluno.getNome();
		fileUtils.appendToFile(content);
	}
}
