package br.com.javaaps.services;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.models.Curso;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CursoService implements IService<Curso> {

  private final FileUtils fileUtils = new FileUtils("cursos.csv");

  /**
   * Retorna uma lista de cursos que estão armazenados na base de dados
   */
  @Override
  public Set<Curso> load() {
    Set<Curso> dados = new HashSet<Curso>();

    for(String valor : fileUtils.getFileContent()) {
      String[] linha = valor.split(";");
      dados.add(new Curso(linha[0], linha[1], linha[2]));
    }

    return dados;
  }

  /**
   * Retorna um curso atrav�s do seu identificador (nome)
   */
  @Override
  public Curso getById(String nome) {
    return load().stream().filter(a -> a.getNome().equals(nome)).findFirst()
      .orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Nenhum curso encontrado com o nome %s!", nome)));
  }


  /**
   * Realiza o cadastro de um novo curso ba base dados
   */
  @Override
  public void save(Curso curso) {
    if (isValid(curso)) {
      Set<String> nomeExistentes = load().stream().map(a -> a.getNome()).collect(Collectors.toSet());

      if (!nomeExistentes.contains(curso.getNome())) {
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
    return !obj.getNome().trim().isEmpty() && !obj.getNivel().trim().isEmpty() && !obj.getAno().trim().isEmpty();
  }

}
