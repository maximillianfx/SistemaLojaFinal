package br.unb.cic.controller;
import br.unb.cic.edicaoprodutos.EditionProducts;
import br.unb.cic.estoque.EstoqueView;
import br.unb.cic.optionsone.Options;
import br.unb.cic.procedures.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ControllerEstoque implements Initializable {
	
	protected DataBase bd;
	protected Connection conexao;
	@FXML private ImageView imgVCadastro;
	@FXML private ImageView imgVTable;
	@FXML private Button btnCadastrar;
	@FXML private Button btnEditar;
	@FXML private Button btnVoltar;
	@FXML private Button btnDeletar;
	@FXML private Button btnFoto;
	@FXML private Button btnValorEstoque;
	@FXML private TextArea fieldDescricaoArea;
	@FXML private TextField fieldNome;
	@FXML private TextField fieldMarca;
	@FXML private TextField fieldQuantidade;
	@FXML private TextField fieldValor;
	@FXML private TextField fieldDescricao;
	@FXML private TextField fieldBusca;
	@FXML private TextField fieldCodigo;
	@FXML private Label lblValorEstoque;
	@FXML private AnchorPane paneFundo;
	@FXML TableView<Products> tabelaProdutos;
	@FXML TableColumn<Products, String> tabelaNome;
	@FXML TableColumn<Products, String> tabelaMarca;
	@FXML TableColumn<Products, Integer> tabelaCodigo;
	@FXML TableColumn<Products, Float> tabelaValor;
	@FXML TableColumn<Products, Integer> tabelaQuantidade;
	private ObservableList<Products> data = FXCollections.observableArrayList();
	private ArrayList<Products> produtos = new ArrayList<Products>();
	private Options options;
	private String nomeImagem = "";
	private File file = null;
	private NumberFormat z = NumberFormat.getCurrencyInstance();
	
	public ControllerEstoque () {
		bd = new DataBase();
		conexao = null;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tabelaNome.setCellValueFactory(new PropertyValueFactory<Products, String>("Tnome"));
		tabelaMarca.setCellValueFactory(new PropertyValueFactory<Products, String>("Tmarca"));
		tabelaCodigo.setCellValueFactory(new PropertyValueFactory<Products, Integer>("Tcodigo"));
		tabelaQuantidade.setCellValueFactory(new PropertyValueFactory<Products, Integer>("Tquantidade"));
		tabelaValor.setCellValueFactory(new PropertyValueFactory<Products, Float>("Tvalor"));
	
		FilteredList<Products> filteredData = new FilteredList<>(data, p -> true);
		fieldBusca.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (produto.getTnome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (produto.getTmarca().toLowerCase().contains(lowerCaseFilter)) {
                	return true;
                } else if (produto.getTcodigo().toString().toLowerCase().contains(lowerCaseFilter)) {
                	return true;
                } else
                	return false;
            });
        });
		SortedList<Products> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tabelaProdutos.comparatorProperty());
        tabelaProdutos.setItems(sortedData);
		
		tabelaValor.setCellFactory(TextFieldTableCell.<Products, Float>forTableColumn(new StringConverter<Float>() {
	        private final NumberFormat nf = NumberFormat.getNumberInstance();

	        {
	             nf.setMaximumFractionDigits(2);
	             nf.setMinimumFractionDigits(2);
	        }
	        @Override public Float fromString(final String s) {
	            // Don't need this, unless table is editable, see DoubleStringConverter if needed
	            return null; 
	        }

			@Override
			public String toString(Float arg0) {
	            return nf.format(arg0);
			}
	    }));		
		
		try {
			updateTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
        
        fieldDescricaoArea.setWrapText(true);
        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tabelaProdutos.getSelectionModel().getSelectedItem() != null) {
                try {
					fieldDescricaoArea.setText(bd.buscaDescricao(conexao, newValue.getTcodigo().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
                String teste = "file:///" + "C:/images/products/" + newValue.getTcodigo().toString() + ".jpg";
                imgVTable.setImage(new Image(teste, 200, 220, false, false));
            }
        });
        
        try {
			lblValorEstoque.setText("Valor do estoque (R$): " + z.format(bd.getValorEstoque(conexao)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        
        BackgroundImage myBI= new BackgroundImage(new Image("images/program/Estoque.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        //then you set to your node
        paneFundo.setBackground(new Background(myBI));
    }
	
	public void buscarFoto (ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Abrir arquivo...");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			file = fileChooser.showOpenDialog(btnFoto.getScene().getWindow());
			if (file != null) {
				String path = file.getAbsolutePath();
				nomeImagem = file.getName();
				String pathok = "file:///" + path.replace("\\","/");	
				imgVCadastro.setImage(new Image(pathok, 200, 160, false, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void cadastrarProduto (ActionEvent event) {
		
		if (!nomeImagem.isEmpty() && (!fieldNome.getText().isEmpty() && !fieldMarca.getText().isEmpty()
				&& !fieldCodigo.getText().isEmpty() && !fieldQuantidade.getText().isEmpty()
				&& !fieldValor.getText().isEmpty() && !fieldDescricao.getText().isEmpty()) && file != null) {
			
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
			String descricao = fieldDescricao.getText();
			
			try {
				if (bd.addProduct(conexao, nome, marca, descricao, valor, codigo, quantidade)) {
					data.add(new Products(nome, marca, codigo, valor, quantidade,descricao));
					tabelaProdutos.setItems(data);
					fieldNome.setText("");
					fieldCodigo.setText("");
					fieldMarca.setText("");
					fieldQuantidade.setText("");
					fieldValor.setText("");
					fieldDescricao.setText("");
					conexao = null;
					
					if (file != null) {
		                try {
		                	File saida = new File("C:/images/products/" + codigo + ".jpg");
		                    ImageIO.write(SwingFXUtils.fromFXImage(imgVCadastro.getImage(),null), "jpg", saida);
		                    imgVCadastro.setImage(null);
		                    file = null;
		                    saida = null;
		                } catch (IOException ex) {
		                    System.out.println(ex.getMessage());
		                }
		            }
					
					lblValorEstoque.setText("Valor do estoque (R$): " + z.format(bd.getValorEstoque(conexao)));
				} else {
					conexao = null;
					JOptionPane.showMessageDialog(null, "Não foi possível adicionar o produto!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Falha ao adicionar o produto!");
		}
	}
	
	public void valorEstoque (ActionEvent event) throws SQLException {
		float valorEstoqueProduto;
		
		Products prod = tabelaProdutos.getSelectionModel().getSelectedItem();
		valorEstoqueProduto = bd.getValorProdutoEstoque (conexao, prod.getTcodigo());
		
		JOptionPane.showMessageDialog(null, "Valor do produto '" + prod.getTnome() + "' em estoque (R$): " + z.format(valorEstoqueProduto));
	}
	
	public void deletarProduto (ActionEvent event) throws SQLException {
		Products prod = tabelaProdutos.getSelectionModel().getSelectedItem();
		bd.deleteProduct(conexao, prod.getTcodigo().toString());
		data.clear();
		updateTable();
		lblValorEstoque.setText("Valor do estoque (R$): " + z.format(bd.getValorEstoque(conexao)));
	}
	
	public void updateTable () throws SQLException {
		ArrayList<Products> produtos = bd.getProdutos(conexao);
		for (Products produto : produtos) {
			data.add(produto);
		}
		
	}

	public void editarProduto (ActionEvent event) {
		try {
			Products prod = tabelaProdutos.getSelectionModel().getSelectedItem();
			EditionProducts edicao = new EditionProducts(prod);
			edicao.start(new Stage());
			Stage stage = (Stage) btnEditar.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void voltar (ActionEvent event) {
		try {
			options = new Options();
			options.start(new Stage());
			Stage stage = (Stage) btnVoltar.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}