package br.com.javaaps.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.services.exceptions.ObjetoJaExisteException;
import br.com.javaaps.services.exceptions.ObjetoNaoEncontradoException;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.FileUtils;

/**
 * Classe respons�vel por centralizar as opera��es relacionado ao cadastro de aluno
 */
public class AlunoService implements Service<Aluno> {
	
	private final FileUtils fileUtils = new FileUtils("alunos.csv");
	
	/**
	 * Retorna uma lista de alunos que est�o armazenados na base de dados 
	 */
	@Override
	public Set<Aluno> load() {
		Set<Aluno> dados = new HashSet<Aluno>();
		
		for(String valor : fileUtils.getFileContent()) {
			String[] linha = valor.split(";");
			dados.add(new Aluno(linha[0], linha[1]));
		}
		
		return dados;
	}
	
	/**
	 * Retorna um aluno atrav�s do seu identificador (id)
	 */
	public Aluno getByNome(String id) {
		return load().stream().filter(a -> a.getId().equals(id)).findFirst()
			.orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Nenhum aluno encontrado com o identificador %s!", id)));
	}
	
	/**
	 * Realiza o cadastro de um novo aluno ba base dados
	 */
	@Override
	public void save(Aluno aluno) {
		if (isValid(aluno)) {
			Set<String> idExistentes = load().stream().map(a -> a.getId()).collect(Collectors.toSet());
			
			if (!idExistentes.contains(aluno.getId())) {
				fileUtils.appendToFile(aluno.toCSV());
			} else {
				throw new ObjetoJaExisteException(String.format("Já existe um aluno cadastrado com o identificador %s!", aluno.getId()));
			}
		} else {
			throw new ValidacaoException("Nem todos os campos foram preenchidos corretamente. Verifique");
		}
	}
	
	/**
	 * Edita um aluno j� existente
	 */
	@Override
	public void edit(String objectId, Aluno aluno) {
		if (isValid(aluno)) {
			List<String> fileContent = new ArrayList<String>();
			
			for(Aluno alunoAtual : load()) {
				if (alunoAtual.getId().equals(objectId)) {
					alunoAtual = aluno;
				}
				
				fileContent.add(alunoAtual.toCSV());
			}
			
			fileUtils.write(fileContent);
		} else {
			throw new ValidacaoException("Nem todos os campos foram preenchidos corretamente. Verifique");
		}
	}
	
	/**
	 * Deleta um aluno existente
	 */
	@Override
	public void delete(String objectId) {
		List<String> fileContent = new ArrayList<String>();
		
		for(Aluno aluno : load()) {
			if (!aluno.getId().equals(objectId)) {
				fileContent.add(aluno.toCSV());
			}
		}
		
		fileUtils.write(fileContent);
	}

	/**
	 * Efetua a validação da entidade aluno
	 */
	@Override
	public boolean isValid(Aluno obj) {
		return !obj.getId().trim().isEmpty() && !obj.getNome().trim().isEmpty(); 
	}
}
