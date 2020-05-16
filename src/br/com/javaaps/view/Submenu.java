package br.com.javaaps.view;

import br.com.javaaps.util.FileUtils;

public abstract class Submenu extends Menu {
	
	protected final int LISTAR_OPTION = 1;
	protected final int CADASTRAR_OPTION = 2;
	protected final int EDITAR_OPTION = 3;
	protected final int REMOVER_OPTION = 4;
	protected final int VOLTAR_OPTION = 5;

	protected FileUtils fileUtils;
	
	public abstract void showSubmenu();
}
