package br.com.javaaps.view;

import java.util.Collection;
import java.util.List;

import br.com.javaaps.models.Curso;
import br.com.javaaps.models.Rendimento;
import br.com.javaaps.services.CursoService;
import br.com.javaaps.services.RendimentoService;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.ConsoleUtils;

public class CursosSubmenu extends Submenu {
	private CursoService cursoService = new CursoService();

	private final int LISTAR_OPTION = 1;
	private final int CADASTRAR_OPTION = 2;
	private final int EDITAR_OPTION = 3;
	private final int REMOVER_OPTION = 4;
	private final int RELATORIO_OPTION = 5;
	private final int VOLTAR_OPTION = 6;
  
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
			menu.append(String.format("[%d] Relatório de rendimento \n", RELATORIO_OPTION));
			menu.append(String.format("[%d] VOLTAR \n\n", VOLTAR_OPTION));

			menu.append("Escolha uma opção: ");

			System.out.print(menu.toString());

			opcaoEscolhida = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());

			switch(opcaoEscolhida) {
				case LISTAR_OPTION:
					listarCursos();
					break;
		        case CADASTRAR_OPTION:
		        	cadastrarCurso();
		        	break;
		        case EDITAR_OPTION:
		        	///
		        	break;
		        case REMOVER_OPTION:
		        	///
		        	break;
		        case RELATORIO_OPTION:
		        	exibirRelatorioRendimento();
		        	break;
			}
		}
	}

	/**
	* Exibe todos os cursos armazenados no banco de dados
	*/
	private void listarCursos() {
		Collection<Curso> cursos = cursoService.load();
	
	    System.out.println("-----------Cursos-----------");
	    for(Curso curso : cursos) {
	    	System.out.println(curso);
	    }
	} 
	
	/**
	* Cadastra um novo curso na base de dados
	*/
	private void cadastrarCurso() {
	    System.out.println();
	
	    String nome;
	    String nivel;
	    int ano;
	
	    try {
	    	System.out.print("Nome: ");
	    	nome = ConsoleUtils.getValorDigitado().trim();
	
	    	System.out.print("Nível: ");
	    	nivel = ConsoleUtils.getValorDigitado().trim();
	
	    	System.out.print("Ano: ");
	    	ano = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado().trim());
	
	    	// Cadastra o curso na base de dados
	    	cursoService.save(new Curso(nome, nivel, ano));
	
	    	ConsoleUtils.showInfo(String.format("Curso %s cadastrado com sucesso!", nome));
	    } catch (ObjetoJaExisteException | ValidacaoException ex) {
	    	ConsoleUtils.showError(ex.getMessage());
	    }
	}
	
	/**
	 * Exibe o relatório de rendimento de um dado curso
	 */
	private void exibirRelatorioRendimento() {
		try {
			String nomeCurso, nivelCurso;
			int anoCurso;
			Curso curso;
						
			System.out.print("Digite o nome do curso: ");
			nomeCurso = ConsoleUtils.getValorDigitado();
			
			System.out.print("Digite o nível do curso: ");
			nivelCurso = ConsoleUtils.getValorDigitado();
			
			System.out.print("Digite o ano do curso: ");
			anoCurso = ConsoleUtils.tryParseToInt(ConsoleUtils.getValorDigitado());
			
			curso = cursoService.getByNomeAndNivelAndAno(nomeCurso, nivelCurso, anoCurso);
			
			// Carrega os rendimentos em memória
			List<Rendimento> rendimentosCurso = new RendimentoService(curso).getByCurso();
			
			// Lista rendimentos no console
			rendimentosCurso.forEach(rendimento -> {
				System.out.println();
				System.out.println(rendimento.toString(false, true));
			});
			
		} catch (ObjetoNaoEncontradoException ex) {
			ConsoleUtils.showError(ex.getMessage());
		}
	}
}
