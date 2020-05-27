package br.com.javaaps.services;

import java.util.Collection;

import br.com.javaaps.models.Curso;
import br.com.javaaps.models.Rendimento;
import br.com.javaaps.services.exceptions.ValidacaoException;
import br.com.javaaps.util.FileUtils;

public class RendimentoService implements Service<Rendimento> {

	private final FileUtils fileUtils;
	
	public RendimentoService(Curso curso) {
		String nomeArquivo = String.format("%s_%s_%d", curso.getNome().toUpperCase(), curso.getNivel().toUpperCase(), curso.getAno());
		fileUtils = new FileUtils(nomeArquivo + ".csv");
	}
	
	@Override
	public Collection<Rendimento> load() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rendimento getByNome(String objectId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String objectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Rendimento obj) {
		if (obj.getAluno() == null || obj.getCurso() == null || obj.getNotas().size() != 4) {
			return false;
		}
		
		return true;
	}
}
