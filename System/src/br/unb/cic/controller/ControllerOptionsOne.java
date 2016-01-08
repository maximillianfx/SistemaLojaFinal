package br.unb.cic.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.unb.cic.estoque.EstoqueView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class ControllerOptionsOne implements Initializable {
	
	@FXML private Button btnSair;
	@FXML private Button btnEstoque;
	@FXML private Button btnCaixa;
	@FXML private AnchorPane paneFundo;
	protected EstoqueView estoque;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		BackgroundImage myBI= new BackgroundImage(new Image("images/program/OptionsPage.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        //then you set to your node
        paneFundo.setBackground(new Background(myBI));
		
	}
	
	public void closeProgram (ActionEvent event) {
		Stage stage = (Stage) btnSair.getScene().getWindow();
		stage.close();
	}
	
	public void goEstoque (ActionEvent event) {
		try {
			estoque = new EstoqueView();
			estoque.start(new Stage());
			Stage stage = (Stage) btnEstoque.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void goCaixa (ActionEvent event) {
		System.out.println("Go Caixa!");
	}

}
