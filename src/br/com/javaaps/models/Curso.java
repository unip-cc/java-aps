package br.com.javaaps.models;

public class Curso {

  private String nome;
  private String nivel;
  private String ano;
  
  public Curso(String nome, String aNivel, String ano) {
    super();
    this.nome = nome;
    this.nivel = aNivel;
    this.ano = ano;
  }

  public String getNome() {return nome;}
  public String getNivel() {return nivel;}
  public String getAno() {return ano;}

  public void setNome(String nome) {this.nome = nome;}
  public void setNivel(String nivel) {this.nivel = nivel;}
  public void setAno(String ano) {this.ano = ano;}

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Curso other = (Curso) obj;
    if (nome == null) {
      if (other.nome != null)
        return false;
    } else if (!nome.equals(other.nome))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Nome: " + getNome() + "\nNível: " + getNivel() + "\nAno: " + getAno() + "\n";
  }

  public String toCSV() {
    return getNome() + ";" + getNivel() + ";" + getAno();
  }
  
  public boolean isGraduacao() {
	  return getNivel().equalsIgnoreCase("graduacao");
  }
  
  public double getMedia () {
	  return isGraduacao() ? 7 : 5;
  }
}
