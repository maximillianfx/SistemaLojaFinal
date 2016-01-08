package br.unb.cic.procedures;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

//Classe para gerenciamento dos dados sobre as tabelas SQL
public class DataBase {

	public void createTableProducts (Connection c) {
		Statement stmt = null;
		String status;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			System.out.println("Conexao aberta com sucesso!");
			
			stmt = c.createStatement();
			
			String sqlcode = "CREATE TABLE PRODUCTS " +
							 "(NOME				TEXT NOT NULL," +
							 " MARCA			TEXT NOT NULL," +
							 " VALOR			FLOAT NOT NULL," +
							 " CODIGO			INT PRIMARY KEY NOT NULL," +
							 " QUANTIDADE		INT NOT NULL," +
							 " DESCRICAO		TEXT NOT NULL)";
			
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.close();
			
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
		}
	}
	
	public void createTableUsers (Connection c) throws SQLException {
		Statement stmt = null;
		String status;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/users.db");
			System.out.println("Conexao aberta com sucesso!");
			
			stmt = c.createStatement();
			
			String sqlcode = "CREATE TABLE USERS " +
							 "(LOGIN			TEXT NOT NULL," +
							 " SENHA			TEXT NOT NULL," +
							 " NOME				TEXT NOT NULL," +
							 " EMAIL			TEXT NOT NULL," +
							 " CELULAR			TEXT NOT NULL," +
							 " RESIDENCIAL		TEXT NOT NULL," +
							 " ENDERECO			TEXT NOT NULL," +
							 " NUMERO			TEXT NOT NULL)";
			
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.close();
			
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
	}

	public boolean addProduct (Connection c, String nome, String marca, String descricao, float valor, int codigo, int quantidade) throws SQLException {
		
		Statement stmt = null;
		String status;
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			
			stmt = c.createStatement();
			
			
			String sqlcode = "INSERT INTO PRODUCTS (NOME,MARCA,VALOR,CODIGO,QUANTIDADE,DESCRICAO) " +
							 "VALUES ('" + nome + "', '" + marca + "', '" + valor + "', '" + codigo + "', '" + quantidade + "', '" + descricao + "');";
			
			
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.commit();
			c.close();
			
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			c.close();
			return false;
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			c.close();
			return false;
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			c.close();
			return false;
		}
		
		return true;
	}
	
	public void addUser (Connection c, String login, String senha, String nome, String email, String telcelular, String telresidencial, String endereco, String numero) throws SQLException {
		Statement stmt = null;
		String status;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/users.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			
			String sqlcode = "INSERT INTO USERS (LOGIN,SENHA,NOME,EMAIL,CELULAR,RESIDENCIAL,ENDERECO,NUMERO) " +
							 "VALUES ('" + login + "', '" + senha + "', '" + nome + "', '" + email + "', '" + telcelular + "', '" + telresidencial + "', '" + endereco + "', '" + numero + "');";
			
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.commit();
			c.close();
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
	}
	
	public String verificarUsuario (Connection c, String login, String senha) throws SQLException {
		Statement stmt = null;
		String user, senhax;
		String status;
		String retorno = "Error";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/users.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while (rs.next()) {
				user = rs.getString("login");
				senhax = rs.getString("senha");
				if (user.equals(login) && senhax.equals(senha)) {
					retorno = rs.getString("nome");
					c.close();
					return retorno;
				} else {
					retorno = "Error";
				}
			}
			stmt.close();
			c.close();
			return retorno;
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
		return retorno;
	}

	public boolean verificarCadastro (Connection c, String login) throws SQLException {
		Statement stmt = null;
		String log;
		String status;
		boolean retorno = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/users.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while (rs.next()) {
				log = rs.getString("login");
				if (login.equals(log)) {
					retorno = true;
				} else {
					retorno = false;
				}
			}
			stmt.close();
			c.close();
			return retorno;
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
		return retorno;
	}
	
	public ArrayList<Products> getProdutos (Connection c) throws SQLException {
		Statement stmt = null;
		String nome, marca, descricao;
		int quantidade, codigo;
		float valor;
		String status;
		ArrayList<Products> produtos = new ArrayList<Products>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS;");
			while (rs.next()) {
				nome = rs.getString("nome");
				marca = rs.getString("marca");
				valor = rs.getFloat("valor");
				codigo = rs.getInt("codigo");
				quantidade = rs.getInt("quantidade");
				descricao = rs.getString("descricao");
				produtos.add(new Products(nome, marca, codigo, valor, quantidade,descricao));
			}
			stmt.close();
			c.close();
			return produtos;
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
		return produtos;
	}
	
	
	public void deleteProduct (Connection c, String codigo) throws SQLException {
		Statement stmt = null;
		String status;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			
			String sqlcode = "DELETE FROM PRODUCTS WHERE CODIGO = " + codigo + ";";
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.commit();
			c.close();
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
	}

	public void atualizarProduto (Connection c, String codigoOld, String nome, int codigoNew, String marca, int quantidade, float valor) throws SQLException {
		Statement stmt = null;
		String status;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			
			String sqlcode = "UPDATE PRODUCTS SET NOME = '" + nome + "', MARCA = '" + marca + "', QUANTIDADE = '" + quantidade + "', VALOR = '" + valor + "', CODIGO = '" + codigoNew + "' WHERE CODIGO = " + codigoOld + ";";
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.commit();
			c.close();
		
		} catch (SQLException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			System.out.println(status);
			stmt.close();
			c.close();
		}
	}

	
	public String buscaDescricao (Connection c, String codigo) throws SQLException {
		Statement stmt = null;
		String descricao = null;
		String code;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execução dos tratamentos SQL e abertura de conexão com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS;");
			while (rs.next()) {
				code = rs.getString("codigo");
				if (code.equals(codigo)) {
					descricao = rs.getString("descricao");
					stmt.close();
					c.close();
					return descricao;
				}
			}
			stmt.close();
			c.close();
			return descricao;
		} catch (SQLException e) {
			status = e.getMessage();
			stmt.close();
			c.close();
			return (String) null;
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			stmt.close();
			c.close();
			return (String) null;
		} catch (Exception e) {
			status = e.getMessage();
			stmt.close();
			c.close();
			return (String) null;
		}
	}
	
	public float getValorProdutoEstoque (Connection c, int codigo) throws SQLException {
		Statement stmt = null;
		int code;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execução dos tratamentos SQL e abertura de conexão com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS;");
			while (rs.next()) {
				code = rs.getInt("codigo");
				if (code == codigo) {
					int quantidade = rs.getInt("quantidade");
					float preco = rs.getFloat("valor");
					stmt.close();
					c.close();
					return (float)(preco*quantidade);
				}
			}
			stmt.close();
			c.close();
			return -1;
		} catch (SQLException e) {
			status = e.getMessage();
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			stmt.close();
			c.close();
		}
		return -1;
	}
	
	public float getValorEstoque (Connection c) throws SQLException {
		Statement stmt = null;
		String status;
		//Bloco try-catch
		float valor = 0;
		try {
			//Carregamento do driver JDBC para execução dos tratamentos SQL e abertura de conexão com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/products.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS;");
			while (rs.next()) {
				int quantidade = rs.getInt("quantidade");
				float preco = rs.getFloat("valor");
				valor = valor + (preco*quantidade);
			}
			stmt.close();
			c.close();
			return valor;
		} catch (SQLException e) {
			status = e.getMessage();	
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			stmt.close();
			c.close();
		} catch (Exception e) {
			status = e.getMessage();
			stmt.close();
			c.close();
		}
		return -1;
	}
}
