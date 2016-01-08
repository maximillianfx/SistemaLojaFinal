package br.unb.cic.controller;

import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.unb.cic.estoque.EstoqueView;
import br.unb.cic.main.Main;
import br.unb.cic.procedures.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class ControllerCadastroUsuario implements Initializable {

	@FXML private TextField fieldNome;
	@FXML private TextField fieldEmail;
	@FXML private TextField fieldTelCelular;
	@FXML private TextField fieldTelResidencial;
	@FXML private TextField fieldEndereco;
	@FXML private TextField fieldNumero;
	@FXML private Button btnCadastrar;
	@FXML private TextField fieldLogin;
	@FXML private PasswordField fieldPassword;
	@FXML private Button btnSair;
	@FXML private AnchorPane paneFundo;
	protected DataBase bd;
	protected Connection conexao;
	protected Main main;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bd = new DataBase();
		conexao = null;
		
		BackgroundImage myBI= new BackgroundImage(new Image("images/program/CadastrarUsuario.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        //then you set to your node
        paneFundo.setBackground(new Background(myBI));
	}

	
	public void cadastrar () throws HeadlessException, SQLException {
		String login = fieldLogin.getText();
		String password = fieldPassword.getText();
		String nome = fieldNome.getText();
		String email = fieldEmail.getText();
		String telcelular = fieldTelCelular.getText();
		String telresidencial = fieldTelResidencial.getText();
		String endereco = fieldEndereco.getText();
		String numero = fieldNumero.getText();
		if (!bd.verificarCadastro(conexao,login)) { 
			bd.addUser(conexao, login, password, nome, email, telcelular, telresidencial, endereco, numero);
			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");		
			try {
				main = new Main();
				main.start(new Stage());
				Stage stage = (Stage) btnCadastrar.getScene().getWindow();
				stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Login já existente! Realize uma nova tentativa");
		}
	}
	
	public void sair (ActionEvent event) {
		try {
			main = new Main();
			main.start(new Stage());
			Stage stage = (Stage) btnSair.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
