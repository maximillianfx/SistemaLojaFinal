package br.unb.cic.edicaoprodutos;

import br.unb.cic.controller.ControllerEditionProducts;
import br.unb.cic.procedures.Products;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class EditionProducts extends Application {
	
	
	private Products produto;
	
	public EditionProducts(Products prod) {
		this.produto = prod;
	}
		
	@Override
	public void start(Stage stage) throws Exception {
		try {
			ControllerEditionProducts controller = new ControllerEditionProducts(this.produto);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/unb/cic/edicaoprodutos/EditionProductsView.fxml"));			
			fxmlLoader.setController(controller);
			Parent root = (Parent)fxmlLoader.load();  
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon32.jpg")));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			} catch (Exception e) {
				e.printStackTrace();
		}
	}	

}