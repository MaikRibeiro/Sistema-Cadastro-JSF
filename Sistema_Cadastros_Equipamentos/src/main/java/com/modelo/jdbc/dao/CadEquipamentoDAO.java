package com.modelo.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.modelo.jdbc.ConnectionFactory;
import com.modelo.model.CadEquipamento;
import com.modelo.model.TabTecnologia;

public class CadEquipamentoDAO {
	
	public static void insert(CadEquipamento cadEquipamento) {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("INSERT INTO cad_equipamento (cequ_numserial, cequ_ttec_id, cequ_data_ins, cequ_data_status, cequ_ativo) VALUES (?, ?, now(), now(), ?)");
			System.out.println(pstm);
			
			pstm.setLong(1, cadEquipamento.getCequNumSerial());
			pstm.setObject(2, cadEquipamento.getCequTtecId());
			pstm.setString(3, cadEquipamento.getCequAtivo());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Equipamento DAO => Erro ao cadastrar equipamento: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
			
		}
	}
	
	public static boolean verificaSerialNumberExistente(Long serial) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT cequ_numserial FROM cad_equipamento WHERE cequ_numserial = ?");
			System.out.println(pstm);
			
			pstm.setLong(1, serial);
			
			rs = pstm.executeQuery();
			
			return rs.next();
			
		} catch (SQLException e) {
			System.out.println("EquipamentoDAO => Erro ao verificar Serial Number existente: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return false;
	}
	
	public static void edit(CadEquipamento cadEquipamento) {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("UPDATE cad_equipamento SET cequ_numserial = ?, cequ_ttec_id = ?, cequ_data_status = now(), cequ_ativo = ? WHERE cequ_id = ?");
			System.out.println(pstm);
			
			pstm.setLong(1, cadEquipamento.getCequNumSerial());
			pstm.setObject(2, cadEquipamento.getCequTtecId());
			pstm.setString(3, cadEquipamento.getCequAtivo());
			pstm.setObject(4, cadEquipamento.getCequId());
			
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar equipamento: " + e);
		
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
			
		}
	}
	
	public static ArrayList<CadEquipamento> list(Long filtroEquipamentoNumSerial, String filtroEquipamentoTecnologia){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT cequ_id, cequ_numserial, cequ_ttec_id, ttec_descricao, cequ_data_ins, cequ_data_status, cequ_ativo " +
					 "FROM " +
					 	"cad_equipamento " +
					 	"INNER JOIN tab_tecnologia ON ttec_id = cequ_ttec_id " +
					 "WHERE CAST(cequ_numserial AS TEXT) LIKE ? " +
					 "AND ttec_descricao ILIKE ?";
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			if(filtroEquipamentoNumSerial == null) {
				pstm.setString(1, "%%");
			} else {
				pstm.setString(1, "%" + filtroEquipamentoNumSerial + "%");
			}
			
			pstm.setString(2, "%" + filtroEquipamentoTecnologia.trim() + "%");
			
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			ArrayList<CadEquipamento> alEquipamentos = new ArrayList<CadEquipamento>();
			while(rs.next()) {
				CadEquipamento cadEqui = new CadEquipamento();
				
				cadEqui.setCequId(rs.getInt("cequ_id"));
				cadEqui.setCequNumSerial(rs.getLong("cequ_numserial"));
				cadEqui.setCequTtecId(rs.getInt("cequ_ttec_id"));
				cadEqui.setTtecDescricao(rs.getString("ttec_descricao"));
				cadEqui.setCequDataIns(rs.getTimestamp("cequ_data_ins"));
				cadEqui.setCequDataStatus(rs.getTimestamp("cequ_data_status"));
				cadEqui.setCequAtivo(rs.getString("cequ_ativo"));
				
				alEquipamentos.add(cadEqui);
			}
			
			return alEquipamentos;
			
		} catch (SQLException e) {
			System.out.println("Equipamento DAO => Erro ao listar equipamento cadastrados: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
	public static ArrayList<TabTecnologia> alTabTecnologia(){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM tab_tecnologia");
			System.out.println(pstm);
			rs = pstm.executeQuery();
			
			ArrayList<TabTecnologia> alTecnologias = new ArrayList<TabTecnologia>();
			
			while(rs.next()) {
				TabTecnologia tabTecnologia = new TabTecnologia();
				tabTecnologia.setTtecId(rs.getInt("ttec_id"));
				tabTecnologia.setTtecDescricao(rs.getString("ttec_descricao"));
				
				alTecnologias.add(tabTecnologia);
			}
			
			return alTecnologias;
			
		} catch(SQLException e) {
			System.out.println("Equipamento DAO => Erro ao listar tab_tecnologia: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
}
