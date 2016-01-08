package br.unb.cic.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.unb.cic.cadastrousuario.CadastroUsuario;
import br.unb.cic.optionsone.Options;
import br.unb.cic.procedures.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class ControllerMain implements Initializable {

	
	@FXML private Button btnEntrar;
	@FXML private Button btnSair;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtSenha;
	@FXML private Button btnCadastrarUsuario;
	@FXML private Button btnTabelas;
	@FXML private AnchorPane paneFundo;
	protected DataBase db;
	protected Connection conexao;
	protected Options opcoes;
	protected CadastroUsuario cadastro;
	protected String usuario;
	protected String senha;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnCadastrarUsuario.setVisible(false);
		btnTabelas.setVisible(false);
		opcoes = new Options();
		cadastro = new CadastroUsuario();
		conexao = null;
		db = new DataBase();
		
		
		 BackgroundImage myBI= new BackgroundImage(new Image("images/program/Start.png"),
	                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	                  BackgroundSize.DEFAULT);
	        //then you set to your node
	        paneFundo.setBackground(new Background(myBI));
		
	}	
	
	public void initLogin (ActionEvent event) throws SQLException {
		String user = txtUsuario.getText();
		String password = txtSenha.getText();
		
		if (user.equals("maximillianfx") && password.equals("220494max")) {
			if (!btnCadastrarUsuario.isVisible()) {
				btnCadastrarUsuario.setVisible(true);
			} else {
				btnCadastrarUsuario.setVisible(false);
			}
			if (!btnTabelas.isVisible()) {
				btnTabelas.setVisible(true);
			} else {
				btnTabelas.setVisible(false);
			}
		} else {
			String usuario = db.verificarUsuario(conexao,user,password);
			if (!usuario.equals("Error")) {
				JOptionPane.showMessageDialog(null, "Olá " + usuario + " !");
				try {
					opcoes.start(new Stage());
					Stage stage = (Stage) btnEntrar.getScene().getWindow();
					stage.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
			}
		}
	}
	
	public void closeLogin (ActionEvent event) {
		Stage stage = (Stage) btnSair.getScene().getWindow();
		stage.close();
	}
	
	public void cadastrarUsuario (ActionEvent event) {
		try {
			cadastro.start(new Stage());
			Stage stage = (Stage) btnCadastrarUsuario.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void criarTabela (ActionEvent event) throws SQLException {
		DataBase bd = new DataBase();
		Connection conexao = null;
		bd.createTableUsers(conexao);
		conexao = null;
		bd.createTableProducts(conexao);
	}
	
	

}
