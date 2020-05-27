package br.com.javaaps.view;

import java.util.Collection;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.services.AlunoService;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.ConsoleUtils;

public class AlunosSubmenu extends Submenu {
	
	private AlunoService alunoService = new AlunoService();
	
	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();
			
			// Defini��o do menu
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
				case EDITAR_OPTION:
					editarAluno();
					break;
				case REMOVER_OPTION:
					deletarAluno();
					break;
			}
		}
	}
	
	/**
	 * Exibe todos os alunos armazenados no banco de dados 
	 */
	private void listarAlunos() {
		System.out.println();
		
		Collection<Aluno> alunos = alunoService.load();
		
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
		
		try {
			System.out.print("Identificador: ");
			id = ConsoleUtils.getValorDigitado().trim();
			
			System.out.print("Nome: ");
			nome = ConsoleUtils.getValorDigitado().trim();
			
			// Cadastra o aluno na base de dados
			alunoService.save(new Aluno(id, nome));
			
			ConsoleUtils.showInfo(String.format("Aluno %s cadastrado com sucesso!", nome));
		} catch (ObjetoJaExisteException | ValidacaoException ex) {
			ConsoleUtils.showError(ex.getMessage());
		}
	}
	
	/**
	 * Edita um aluno j� existente
	 */
	private void editarAluno() {
		System.out.println();
		
		String id;
		String novoNome;
		
		System.out.print("Digite o identificador do aluno que deseja editar: ");
		id = ConsoleUtils.getValorDigitado();
		
		try {
			Aluno aluno = alunoService.getByNome(id);
			
			System.out.println("Dados do aluno => " + aluno.toString());
			
			System.out.print("Novo nome: ");
			novoNome = ConsoleUtils.getValorDigitado();
			
			aluno.setNome(novoNome);
			
			// Salva alterações no banco de dados
			alunoService.edit(id, aluno);
			
			ConsoleUtils.showInfo("Cadastro atualizado com sucesso!");
		} catch (ObjetoNaoEncontradoException | ValidacaoException ex) {
			ConsoleUtils.showError(ex.getMessage());
		}
	}
	
	/**
	 * Remove um aluno j� existente
	 */
	private void deletarAluno() {
		System.out.println();
		
		System.out.print("Digite o identificador do aluno que deseja remover: ");
		String id = ConsoleUtils.getValorDigitado();
		
		try {
			Aluno aluno = alunoService.getByNome(id);
			
			// Remove aluno da base de dados
			alunoService.delete(id);
			
			ConsoleUtils.showInfo(String.format("Aluno %s removido com sucesso!", aluno.getNome()));
		} catch (ObjetoNaoEncontradoException ex) {
			ConsoleUtils.showError(ex.getMessage());
		}
	}
}
