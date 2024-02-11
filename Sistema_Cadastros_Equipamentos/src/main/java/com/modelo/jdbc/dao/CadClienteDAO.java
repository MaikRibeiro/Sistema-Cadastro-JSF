package com.modelo.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.modelo.jdbc.ConnectionFactory;
import com.modelo.model.CadClienteFull;
import com.modelo.model.CadClienteMini;

public class CadClienteDAO {
	
	public static void insert(CadClienteFull cadCliente){
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("INSERT INTO cad_cliente (ccli_nome, ccli_cnpj_cpf, ccli_data_ins, ccli_data_status, ccli_ativo) VALUES (?, ?, now(), now(), ?)");
			System.out.println(pstm);
			
			pstm.setString(1, cadCliente.getCcliNome().trim());
			pstm.setString(2, cadCliente.getCcliCnpjCpf());
			pstm.setString(3, cadCliente.getCcliAtivo());
			
			pstm.executeUpdate();
			
		} catch(SQLException e) {
			System.out.println("Erro ao Cadastrar Cliente" + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
	}
	
	public static boolean verificaCpfCnpjExistente(String cnpjCpf) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT ccli_cnpj_cpf FROM cad_cliente WHERE ccli_cnpj_cpf = ?");
			System.out.println(pstm);
			
			pstm.setString(1, cnpjCpf);
			
			rs = pstm.executeQuery();
			
			return rs.next();
			
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se ja existe " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return false;
	}
	
	public static void edit(CadClienteFull cadCliente) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("UPDATE cad_cliente SET ccli_nome = ?, ccli_cnpj_cpf = ?, ccli_data_status = now(), ccli_ativo = ? WHERE ccli_id = ?");
			System.out.println(pstm);
			
			pstm.setString(1, cadCliente.getCcliNome());
			pstm.setString(2, cadCliente.getCcliCnpjCpf());
			pstm.setString(3, cadCliente.getCcliAtivo());
			pstm.setObject(4, cadCliente.getCcliId());
			
			pstm.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar cliente: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
		}
	}
	
	public static CadClienteFull getCadClienteFull(CadClienteMini cadClienteMini) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM cad_cliente WHERE ccli_id = ?");
			System.out.println(pstm);
			
			pstm.setObject(1, cadClienteMini.getCcliId());
			
			rs = pstm.executeQuery();
			
			CadClienteFull cadClienteFull = new CadClienteFull();
			
			while(rs.next()) {
				
				cadClienteFull.setCcliId(rs.getInt("ccli_id"));
				cadClienteFull.setCcliNome(rs.getString("ccli_nome"));
				cadClienteFull.setCcliCnpjCpf(rs.getString("ccli_cnpj_cpf"));
				cadClienteFull.setCcliDataIns(rs.getTimestamp("ccli_data_ins"));
				cadClienteFull.setCcliDataStatus(rs.getTimestamp("ccli_data_status"));
				cadClienteFull.setCcliAtivo(rs.getString("ccli_ativo"));
				
				return cadClienteFull;
			}
			
			
		} catch(SQLException e) {
			System.out.println("ClienteDAO => Erro ao listar Get(Full): " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
	public static ArrayList<CadClienteFull> list(String filtroNome, String filtroCpfCnpj){
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM cad_cliente WHERE ccli_nome ILIKE ? AND ccli_cnpj_cpf LIKE ?";
					 
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, "%" + filtroNome.trim() + "%");
			pstm.setString(2, "%" + filtroCpfCnpj.trim() + "%");
			
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			
			ArrayList<CadClienteFull> alClientes = new ArrayList<CadClienteFull>();
			while(rs.next()) {
				CadClienteFull cliente = new CadClienteFull();
				
				cliente.setCcliId(rs.getInt("ccli_id"));
				cliente.setCcliNome(rs.getString("ccli_nome"));
				cliente.setCcliCnpjCpf(rs.getString("ccli_cnpj_cpf"));
				
				alClientes.add(cliente);
			}
			
			return alClientes;
			
		} catch(SQLException e) {
			System.out.println("Erro ao listar clientes cadastrados: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
}
