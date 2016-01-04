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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class ControllerEstoque implements Initializable {
	
	protected DataBase bd;
	protected Connection conexao;
	@FXML private Button btnCadastrar;
	@FXML private Button btnEditar;
	@FXML private Button btnVoltar;
	@FXML private Button btnDeletar;
	@FXML private TextField fieldNome;
	@FXML private TextField fieldMarca;
	@FXML private TextField fieldQuantidade;
	@FXML private TextField fieldValor;
	@FXML private TextField fieldDescricao;
	@FXML private TextField fieldBusca;
	@FXML private TextField fieldCodigo;
	@FXML TableView<Products> tabelaProdutos;
	@FXML TableColumn<Products, String> tabelaNome;
	@FXML TableColumn<Products, String> tabelaMarca;
	@FXML TableColumn<Products, Integer> tabelaCodigo;
	@FXML TableColumn<Products, Float> tabelaValor;
	@FXML TableColumn<Products, Integer> tabelaQuantidade;
	private ObservableList<Products> data = FXCollections.observableArrayList();
	private ArrayList<Products> produtos = new ArrayList<Products>();
	private Options options;
	
	public ControllerEstoque () {
		bd = new DataBase();
		conexao = null;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tabelaNome.setCellValueFactory(new PropertyValueFactory<Products, String>("Tnome"));
		tabelaMarca.setCellValueFactory(new PropertyValueFactory<Products, String>("Tmarca"));
		tabelaCodigo.setCellValueFactory(new PropertyValueFactory<Products, Integer>("Tcodigo"));
		tabelaValor.setCellValueFactory(new PropertyValueFactory<Products, Float>("Tvalor"));
		tabelaQuantidade.setCellValueFactory(new PropertyValueFactory<Products, Integer>("Tquantidade"));
		
		updateTable();
		tabelaProdutos.setItems(data);	
		
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
    }
	
	
	public void cadastrarProduto (ActionEvent event) {
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
				data.add(new Products(nome, marca, codigo, valor, quantidade));
				tabelaProdutos.setItems(data);
				fieldNome.setText("");
				fieldCodigo.setText("");
				fieldMarca.setText("");
				fieldQuantidade.setText("");
				fieldValor.setText("");
				fieldDescricao.setText("");
				conexao = null;
			} else {
				conexao = null;
				JOptionPane.showMessageDialog(null, "Não foi possível adicionar o produto!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deletarProduto (ActionEvent event) {
		Products prod = tabelaProdutos.getSelectionModel().getSelectedItem();
		bd.deleteProduct(prod.getTcodigo().toString());
		data.clear();
		updateTable();
	}
	
	public void updateTable () {
		ArrayList<Products> produtos = bd.getProdutos();
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