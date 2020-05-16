package br.com.javaaps.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe genérica criada para realizar a manipulação dos arquivos CSV (leitura e escrita)
 */
public class FileUtils {

	private final String filePath;
	private final File file;
	private final String charsetName = "UTF-8";
	
	public FileUtils(String filePath) {
		this.filePath = filePath;
		this.file = new File(filePath);
	}

	/**
	 * Retorna o conteúdo do arquivo CSV (cada índice da lista representa uma linha do arquivo)
	 * @return
	 */
	public List<String> getFileContent() {
		List<String> content = new ArrayList<String>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file, Charset.forName(charsetName)))) {
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
	 * Adiciona uma linha (content) no final do arquivo
	 * @param content
	 */
	public void appendToFile(String content) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, Charset.forName(charsetName), true))) {
			writer.write("\n" + content);
		} catch (IOException ex) {
			ConsoleUtils.showError("Ocorreu um erro ao realizar a escrita no arquivo de dados!'");
		}
	}
	
	public String getFilePath() {
		return filePath;
	}
}
