package br.com.javaaps.services;

import java.util.ArrayList;
import java.util.List;

import br.com.javaaps.models.Aluno;
import br.com.javaaps.util.FileUtils;

/**
 * Classe respons�vel por centralizar as opera��es relacionado ao cadastro de aluno
 */
public class AlunoService {
	
	private final FileUtils fileUtils = new FileUtils("alunos.csv");
	
	/**
	 * Retorna uma lista de alunos que est�o armazenados na base de dados 
	 * @return
	 */
	public List<Aluno> loadAlunos() {
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
	public void save(Aluno aluno) {
		String content = aluno.getId() + ";" + aluno.getNome();
		fileUtils.appendToFile(content);
	}
}
