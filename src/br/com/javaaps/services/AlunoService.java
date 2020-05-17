package br.com.javaaps.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.util.FileUtils;

/**
 * Classe responsável por centralizar as operações relacionado ao cadastro de aluno
 */
public class AlunoService implements IService<Aluno> {
	
	private final FileUtils fileUtils = new FileUtils("alunos.csv");
	
	/**
	 * Retorna uma lista de alunos que estão armazenados na base de dados 
	 */
	public Set<Aluno> load() {
		Set<Aluno> dados = new HashSet<Aluno>();
		
		for(String valor : fileUtils.getFileContent()) {
			String[] linha = valor.split(";");
			dados.add(new Aluno(linha[0], linha[1]));
		}
		
		return dados;
	}
	
	public Aluno getById(String id) {
		return load().stream().filter(a -> a.getId().equals(id)).findFirst()
			.orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Nenhum aluno encontrado com o identificador %s!", id)));
	}
	
	/**
	 * Realiza o cadastro de um novo aluno ba base dados
	 */
	public void save(Aluno aluno) {
		Set<String> idExistentes = load().stream().map(a -> a.getId()).collect(Collectors.toSet());
		
		if (!idExistentes.contains(aluno.getId())) {
			fileUtils.appendToFile(aluno.toCSV());
		} else {
			throw new ObjetoJaExisteException(String.format("Já existe um aluno cadastrado com o identificador %s!", aluno.getId()));
		}
	}
	
	/**
	 * Edita um aluno já existente
	 */
	public void edit(String objectId, Aluno aluno) {
		List<String> fileContent = new ArrayList<String>();
		
		for(Aluno alunoAtual : load()) {
			if (alunoAtual.getId().equals(objectId)) {
				alunoAtual = aluno;
			}
			
			fileContent.add(alunoAtual.toCSV());
		}
		
		fileUtils.write(fileContent);
	}
	
	/**
	 * Deleta um aluno existente
	 */
	public void delete(String objectId) {
		List<String> fileContent = new ArrayList<String>();
		
		for(Aluno aluno : load()) {
			if (!aluno.getId().equals(objectId)) {
				fileContent.add(aluno.toCSV());
			}
		}
		
		fileUtils.write(fileContent);
	}
}
