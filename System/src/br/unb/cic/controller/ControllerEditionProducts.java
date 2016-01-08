package br.unb.cic.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.unb.cic.estoque.EstoqueView;
import br.unb.cic.optionsone.Options;
import br.unb.cic.procedures.DataBase;
import br.unb.cic.procedures.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class ControllerEditionProducts implements Initializable {

	private Products produto;
	private EstoqueView estoque;
	@FXML private TextField fieldNome;
	@FXML private TextField fieldMarca;
	@FXML private TextField fieldCodigo;
	@FXML private TextField fieldValor;
	@FXML private TextField fieldQuantidade;
	@FXML private Button btnVoltar;
	@FXML private Button btnConfirmar;
	@FXML private AnchorPane paneFundo;
	private DataBase bd;
	private Connection conexao;
	
	
	public ControllerEditionProducts(Products prod) {
		this.produto = prod;
		bd = new DataBase();
		conexao = null;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fieldNome.setText(this.produto.getTnome());
		fieldMarca.setText(this.produto.getTmarca());
		fieldCodigo.setText(this.produto.getTcodigo().toString());
		fieldValor.setText(this.produto.getTvalor().toString());
		fieldQuantidade.setText(this.produto.getTquantidade().toString());	
		
		BackgroundImage myBI= new BackgroundImage(new Image("images/program/EditarProduto.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        //then you set to your node
        paneFundo.setBackground(new Background(myBI));
	}
	
	
	public void voltar (ActionEvent event) {
		try {
			estoque = new EstoqueView();
			estoque.start(new Stage());
			Stage stage = (Stage) btnVoltar.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void confirmar (ActionEvent event) {
		String nome = fieldNome.getText();
		int codigo = Integer.parseInt(fieldCodigo.getText());
		String marca = fieldMarca.getText();
		int quantidade = Integer.parseInt(fieldQuantidade.getText());
		String valorx = fieldValor.getText();
		float valor;
		if (valorx.indexOf(',') > 0) {
			int pos = valorx.indexOf(',');
			char [] v = valorx.toCharArray();
			v[pos] = '.';
			valorx = String.valueOf(v);
			valor = Float.parseFloat(valorx);	
		} else {
			valor = Float.parseFloat(fieldValor.getText());
		}
		
		try {
			bd.atualizarProduto (conexao,this.produto.getTcodigo().toString(),nome, codigo, marca, quantidade,valor);
			estoque = new EstoqueView();
			estoque.start(new Stage());
			Stage stage = (Stage)btnConfirmar.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
