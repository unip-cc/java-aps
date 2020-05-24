package br.com.javaaps.view;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.models.Curso;
import br.com.javaaps.services.CursoService;
import br.com.javaaps.services.Service;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.ConsoleUtils;

import java.util.Collection;

public class CursosSubmenu extends Submenu {

  private Service<Curso> cursoService = new CursoService();

	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;

		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();

			// Defini��o do menu
			menu.append(String.format("[%d] Listar cursos \n", LISTAR_OPTION));
			menu.append(String.format("[%d] Cadastrar curso \n", CADASTRAR_OPTION));
			menu.append(String.format("[%d] Editar curso \n", EDITAR_OPTION));
			menu.append(String.format("[%d] Remover curso \n", REMOVER_OPTION));
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
        // case EDITAR_OPTION:
        //  editarAluno();
        //  break;
        // case REMOVER_OPTION:
        //  deletarAluno();
        //  break;
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
    String ano;

    try {
      System.out.print("Nome: ");
      nome = ConsoleUtils.getValorDigitado().trim();

      System.out.print("Nível: ");
      nivel = ConsoleUtils.getValorDigitado().trim();

      System.out.print("Ano: ");
      ano = ConsoleUtils.getValorDigitado().trim();

      // Cadastra o curso na base de dados
      cursoService.save(new Curso(nome, nivel, ano));

      ConsoleUtils.showInfo(String.format("Curso %s cadastrado com sucesso!", nome));
    } catch (ObjetoJaExisteException | ValidacaoException ex) {
      ConsoleUtils.showError(ex.getMessage());
    }
  }

}
