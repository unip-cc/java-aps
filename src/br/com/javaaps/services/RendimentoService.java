package br.com.javaaps.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.javaaps.models.Curso;
import br.com.javaaps.models.Nota;
import br.com.javaaps.models.Rendimento;
import br.com.javaaps.models.enums.TipoNota;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.FileUtils;

public class RendimentoService implements Service<Rendimento> {

	private final FileUtils fileUtils;
	private final Curso curso;
	
	private final AlunoService alunoService = new AlunoService();
		
	public RendimentoService(Curso curso) {
		String nomeArquivo = String.format("%s_%s_%d", curso.getNome(), curso.getNivel(), curso.getAno()).toUpperCase();
		
		this.fileUtils = new FileUtils(nomeArquivo + ".csv");
		this.curso = curso;
	}
	
	@Override
	public Collection<Rendimento> load() {
		return null;
	}
	
	/**
	 * Retorna o rendimento do curso
	 * @return
	 */
	public List<Rendimento> getByCurso() {
		List<Rendimento> dados = new ArrayList<Rendimento>();
		
		for(String linha : fileUtils.getFileContent()) {
			String[] linhaSplit = linha.split(",");
			
			dados.add(new Rendimento(alunoService.getById(linhaSplit[0]), this.curso,
				Arrays.asList(
					new Nota(Double.parseDouble(linhaSplit[1]), TipoNota.NP1),
					new Nota(Double.parseDouble(linhaSplit[2]), TipoNota.NP2),
					new Nota(Double.parseDouble(linhaSplit[3]), TipoNota.SUB),
					new Nota(Double.parseDouble(linhaSplit[4]), TipoNota.EXAME)
				)
			));
		}
		
		return dados;
	}
	
	/**
	 * Retorna o rendimento de um aluno específico no curso 'atual'
	 * @return
	 */
	public List<Rendimento> getByCursoAndAlunoId(String alunoId) {
		return getByCurso().stream().filter(r -> r.getAluno().getId().equals(alunoId)).collect(Collectors.toList());
	}

	@Override
	public void save(Rendimento obj) {
		if (isValid(obj)) {
			fileUtils.appendToFile(obj.toCSV());
		} else {
			throw new ValidacaoException("Nem todos os campos foram preenchidos corretamente. Verifique");
		}
	}

	@Override
	public void edit(String objectId, Rendimento obj) {
		
	}

	@Override
	public void delete(String objectId) {
		
		
	}

	@Override
	public boolean isValid(Rendimento obj) {
		if (obj.getAluno() == null || obj.getCurso() == null || obj.getNotas().size() != 4) {
			return false;
		}
		
		return true;
	}
}
