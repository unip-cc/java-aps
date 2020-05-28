package br.com.javaaps.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.javaaps.models.Curso;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.FileUtils;

public class CursoService implements Service<Curso> {

  private final FileUtils fileUtils = new FileUtils("cursos.csv");

  /**
   * Retorna uma lista de cursos que estão armazenados na base de dados
   */
  @Override
  public Set<Curso> load() {
    Set<Curso> dados = new HashSet<Curso>();

    for(String valor : fileUtils.getFileContent()) {
      String[] linha = valor.split(";");
      dados.add(new Curso(linha[0], linha[1], Integer.parseInt(linha[2])));
    }

    return dados;
  }

  /**
   * Retorna um curso através do seu nome
   */
  public Set<Curso> getByNome(String nome) {
    return load().stream().filter(a -> a.getNome().equals(nome)).collect(Collectors.toSet());
  }
  
  /**
   * Retorna um curso através do seu nome, do seu nível e do seu ano
   * @param nome
   * @param nivel
   * @param ano
   * @return
   */
  public Curso getByNomeAndNivelAndAno(String nome, String nivel, int ano) {
	  return load().stream().filter(c -> c.getNome().equals(nome) && c.getNivel().equals(nivel) && c.getAno() == ano).findFirst()
		.orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhum curso encontrado com os parâmetros informados!"));
  }
  
  /**
   * Realiza o cadastro de um novo curso ba base dados
   */
  @Override
  public void save(Curso curso) {
    if (isValid(curso)) {
      if (getByNome(curso.getNome()).isEmpty()) {
        fileUtils.appendToFile(curso.toCSV());
      } else {
        throw new ObjetoJaExisteException(String.format("Já existe um curso cadastrado com o nome %s!", curso.getNome()));
      }
    } else {
      throw new ValidacaoException("Nem todos os campos foram preenchidos corretamente. Verifique");
    }
  }

  /**
   * Edita um curso já existente
   */
  @Override
  public void edit(String objectNome, Curso curso) {
    if (isValid(curso)) {
      List<String> fileContent = new ArrayList<String>();

      for(Curso cursoAtual : load()) {
        if (cursoAtual.getNome().equals(objectNome)) {
          cursoAtual = curso;
        }

        fileContent.add(cursoAtual.toCSV());
      }

      fileUtils.write(fileContent);
    } else {
      throw new ValidacaoException("Nem todos os campos foram preenchidos corretamente. Verifique");
    }
  }


  /**
   * Deleta um curso existente
   */
  @Override
  public void delete(String objectNome) {
    List<String> fileContent = new ArrayList<String>();

    for(Curso curso : load()) {
      if (!curso.getNome().equals(objectNome)) {
        fileContent.add(curso.toCSV());
      }
    }

    fileUtils.write(fileContent);
  }


  /**
   * Efetua a validação da entidade curso
   */
  @Override
  public boolean isValid(Curso obj) {
    return !obj.getNome().trim().isEmpty() && !obj.getNivel().trim().isEmpty() && obj.getAno() != 0;
  }

}
