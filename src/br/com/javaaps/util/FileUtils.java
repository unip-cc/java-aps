package br.com.javaaps.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe gen�rica criada para realizar a manipula��o dos arquivos CSV (leitura e escrita)
 */
public class FileUtils {

	private final String filePath;
	private final File file;
	private final Charset charset = Charset.forName("UTF-8");
	
	public FileUtils(String filePath) {
		this.filePath = "database/" + filePath;
		this.file = new File(this.filePath);
		
		// Cria o arquivo de dados, caso seja necessário..
		createFile(this.file);
	}

	/**
	 * Retorna o conte�do do arquivo CSV (cada �ndice da lista representa uma linha do arquivo)
	 * @return
	 */
	public List<String> getFileContent() {
		List<String> content = new ArrayList<String>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file, charset))) {
			String linha = reader.readLine();
			
			while (linha != null) {
				content.add(linha);
				linha = reader.readLine();
			}
			
		} catch (IOException ex) {
			ConsoleUtils.showError("Ocorreu um erro ao realizar a leitura do arquivo de dados!'");
		}
		
		return content;
	}
	
	/**
	 * Escreve no arquivo (remove todo o conte�do anterior)
	 * @param lines
	 */
	public void write(Collection<String> lines) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, charset, false))){
			for(String linha : lines) {
				writer.write(linha + "\n");
			}
		} catch (IOException ex) {
			ConsoleUtils.showError("Ocorreu um erro ao realizar a escrita no arquivo de dados!'");
		}
	}
	
	/**
	 * Adiciona uma linha (content) no final do arquivo
	 * @param content
	 */
	public void appendToFile(String content) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, charset, true))) {
			writer.write(content + "\n");
		} catch (IOException ex) {
			ConsoleUtils.showError("Ocorreu um erro ao realizar a escrita no arquivo de dados!'");
		}
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Cria o arquivo de dados
	 * @param file
	 */
	private void createFile(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException ex) {
			ConsoleUtils.showError("Ocorreu um erro ao criar o arquivo de dados..");
		}
	}
}
