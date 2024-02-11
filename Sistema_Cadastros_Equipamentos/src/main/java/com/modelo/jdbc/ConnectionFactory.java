package com.modelo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		final String url = "jdbc:postgresql://localhost:5432/prometheus";
		final String usuario = "postgres";
		final String senha = "root";
		final String driver = "org.postgresql.Driver";

		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, usuario, senha);

		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexao: " + e);

		}
	}

	public static void closeConnection(Connection con) {

		try {
			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			System.out.println("Erro ao fechar conexao: " + e);

		}

	}

	public static void closeConnection(Connection con, PreparedStatement pstm) {

		try {
			if (pstm != null) {
				pstm.close();
			}

		} catch (SQLException e) {
			System.out.println("Erro ao fechar conexao e preparedStatement: " + e);

		}

		closeConnection(con);

	}

	public static void closeConnection(Connection con, PreparedStatement ptsm, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			System.out.println("Erro ao fechar con, pstm e rs");

		}

		closeConnection(con, ptsm);

	}
}
