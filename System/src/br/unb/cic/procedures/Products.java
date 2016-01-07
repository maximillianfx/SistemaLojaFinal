package br.unb.cic.procedures;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Products {
	
	private final SimpleStringProperty Tnome;
	private final SimpleStringProperty Tmarca;
	private final SimpleIntegerProperty Tcodigo;
	private final SimpleFloatProperty Tvalor;
	private final SimpleIntegerProperty Tquantidade;
	String descricao;
	
	public Products (String nome, String marca, int codigo, float valor, int quantidade, String desc) {
		this.Tnome = new SimpleStringProperty(nome);
		this.Tmarca = new SimpleStringProperty(marca);
		this.Tcodigo = new SimpleIntegerProperty(codigo);
		this.Tvalor = new SimpleFloatProperty(valor);
		this.Tquantidade = new SimpleIntegerProperty(quantidade);
		this.descricao = desc;
	}
	
	public String getTnome () {
		return Tnome.get();
	}
	
	public String getTmarca () {
		return Tmarca.get();
	}

	public Integer getTcodigo () {
		return Tcodigo.get();
	}
	
	public Float getTvalor () {
		return Tvalor.get();
	}
	
	public Integer getTquantidade () {
		return Tquantidade.get();
	}
	
	public String getTdescricao () {
		return this.descricao;
	}
}
