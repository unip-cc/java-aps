package br.com.javaaps.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.models.Curso;
import br.com.javaaps.models.Nota;
import br.com.javaaps.models.Rendimento;
import br.com.javaaps.models.enums.TipoNota;
import br.com.javaaps.services.AlunoService;
import br.com.javaaps.services.CursoService;
import br.com.javaaps.services.RendimentoService;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.ConsoleUtils;

public class RendimentoSubmenu extends Submenu {
	
	private final CursoService cursoService = new CursoService();
	private final AlunoService alunoService = new AlunoService();
	
	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();
			
			// Definição do menu
			menu.append(String.format("[%d] Incluir rendimento\n", CADASTRAR_OPTION));
			menu.append(String.format("[%d] Remover rendimento\n", REMOVER_OPTION));
			menu.append(String.format("[%d] Voltar\n\n\n", VOLTAR_OPTION));
			
			menu.append("Escolha uma opção: ");
			
			System.out.print(menu.toString());
			
			opcaoEscolhida = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());
			
			switch(opcaoEscolhida) {
				case CADASTRAR_OPTION:
					cadastrarRendimento();
					break;
				case REMOVER_OPTION:
					removerRendimento();
					break;
			}
		}
	}
	
	/**
	 * Realiza o cadastro de um novo rendimento
	 */
	private void cadastrarRendimento() {
		System.out.println();
				
		Aluno aluno;
		Curso curso;
		Rendimento rendimento;
		
		try {
			String nomeCurso, nivelCurso;
			int anoCurso;
			Nota notaNP1, notaNP2, notaSUB, notaExame;
			
			System.out.print("Digite o identificador do aluno: ");
			aluno = alunoService.getByNome(ConsoleUtils.getValorDigitado());
			
			System.out.print("Digite o nome do curso: ");
			nomeCurso = ConsoleUtils.getValorDigitado();
			
			System.out.print("Digite o nível do curso: ");
			nivelCurso = ConsoleUtils.getValorDigitado();
			
			System.out.print("Digite o ano do curso: ");
			anoCurso = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());
			
			// NP1
			System.out.print("Digite a nota da NP1: ");
			notaNP1 = new Nota(ConsoleUtils.tryParseToDouble(ConsoleUtils.getValorDigitado()), TipoNota.NP1);
			
			// NP2
			System.out.print("Digite a nota da NP2: ");
			notaNP2 = new Nota(ConsoleUtils.tryParseToDouble(ConsoleUtils.getValorDigitado()), TipoNota.NP2);
			
			// SUB
			System.out.print("Digite a nota da SUB: ");
			notaSUB = new Nota(ConsoleUtils.tryParseToDouble(ConsoleUtils.getValorDigitado()), TipoNota.SUB);
			
			// Exame
			System.out.print("Digite a nota do EXAME: ");
			notaExame = new Nota(ConsoleUtils.tryParseToDouble(ConsoleUtils.getValorDigitado()), TipoNota.EXAME);
			
			curso = cursoService.getByNomeAndNivelAndAno(nomeCurso, nivelCurso, anoCurso);
			
			// Cria uma nova instância de rendimento
			rendimento = new Rendimento(aluno, curso, Arrays.asList(notaNP1, notaNP2, notaSUB, notaExame));
			
			// Grava rendimento na base de dados
			new RendimentoService(rendimento).save(rendimento);
			
			ConsoleUtils.showInfo(String.format("O rendimento do aluno %s foi gravado com sucesso!", rendimento.getAluno().getNome()));
		} catch (ObjetoNaoEncontradoException | ValidacaoException ex) {
			ConsoleUtils.showError(ex.getMessage());
		}
	}
	
	/**
	 * Remove um rendimento existente
	 */
	private void removerRendimento() {
		
	}
}
