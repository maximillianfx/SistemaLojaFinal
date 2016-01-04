package br.unb.cic.cadastrousuario;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class CadastroUsuario extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/br/unb/cic/cadastrousuario/CadastroUsuarioView.fxml"));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon32.jpg")));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	

}