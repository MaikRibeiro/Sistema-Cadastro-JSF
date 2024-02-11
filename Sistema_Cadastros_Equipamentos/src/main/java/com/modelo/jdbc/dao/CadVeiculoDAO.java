package com.modelo.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.modelo.jdbc.ConnectionFactory;
import com.modelo.model.CadClienteFull;
import com.modelo.model.CadEquipamento;
import com.modelo.model.CadVeiculoFull;
import com.modelo.model.CadVeiculoMini;
import com.modelo.model.TabCor;
import com.modelo.model.TabTipoVeiculo;

public class CadVeiculoDAO {
	
	public static void insert(CadVeiculoFull cadVeiculo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("INSERT INTO cad_veiculo (cvei_placa, cvei_display, cvei_tcor_id, cvei_ttve_id, cvei_cequ_id, cvei_ccli_id, cvei_data_ins, cvei_data_status, cvei_ativo) " +
									 	 "VALUES(?, ?, ?, ?, ?, ?, now(), now(), ?)");
			System.out.println(pstm);
			
			pstm.setString(1, cadVeiculo.getCveiPlaca());
			pstm.setString(2, cadVeiculo.getCveiDisplay());
			pstm.setObject(3, cadVeiculo.getCveiTcorId());
			pstm.setObject(4, cadVeiculo.getCveiTtveId());
			pstm.setObject(5, cadVeiculo.getCveiCequId());
			pstm.setObject(6, cadVeiculo.getCveiCcliId());
			pstm.setString(7, cadVeiculo.getCveiAtivo());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar veiculo: " + e);
		
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
			
		}
	}
	
	public static boolean verificaPlacaExistente(String placa) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT cvei_placa FROM cad_veiculo WHERE cvei_placa = ?");
			System.out.println(pstm);
			
			pstm.setString(1, placa);
			
			rs = pstm.executeQuery();
			
			return rs.next();
			
		} catch(SQLException e) {
			System.out.println("VeiculoDAO => Erro ao verificar placa existente: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		
		return false;
	}
	
	public static void edit(CadVeiculoFull cadVeiculo) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("UPDATE cad_veiculo " +
					 					 	"SET cvei_placa = ?, cvei_display = ?, cvei_tcor_id = ?, cvei_ttve_id = ?, cvei_cequ_id = ?, cvei_ccli_id = ?, cvei_data_status = now(), cvei_ativo = ? " + 
										 "WHERE cvei_id = ?");
			System.out.println(pstm);
			
			pstm.setString(1, cadVeiculo.getCveiPlaca());
			pstm.setString(2, cadVeiculo.getCveiDisplay());
			pstm.setObject(3, cadVeiculo.getCveiTcorId());
			pstm.setObject(4, cadVeiculo.getCveiTtveId());
			pstm.setObject(5, cadVeiculo.getCveiCequId());
			pstm.setObject(6, cadVeiculo.getCveiCcliId());
			pstm.setString(7, cadVeiculo.getCveiAtivo());
			pstm.setObject(8, cadVeiculo.getCveiId());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar Veiculo: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm);
			
		}
	}
	
	public static CadVeiculoFull getCadVeiculoFull(CadVeiculoMini cadVeiculoMini) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm =  conn.prepareStatement("SELECT cvei_id, cvei_placa, cvei_display, cvei_tcor_id, tcor_descricao, cvei_ttve_id, ttve_descricao, cvei_cequ_id, cequ_numserial, cvei_ccli_id, ccli_nome, cvei_data_ins, cvei_data_status, cvei_ativo " + 
										  	"FROM " +
											"cad_veiculo " +
											"INNER JOIN tab_cor ON tcor_id = cvei_tcor_id " +
											"INNER JOIN tab_tipo_veiculo ON ttve_id = cvei_ttve_id " +
											"LEFT JOIN cad_equipamento ON cequ_id = cvei_cequ_id " +
											"INNER JOIN cad_cliente ON ccli_id = cvei_ccli_id " +
										  "WHERE cvei_id = ?");
			
			pstm.setObject(1, cadVeiculoMini.getCveiId());
			
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			CadVeiculoFull cadVeiculoFull = new CadVeiculoFull();
			
			while(rs.next()) {
				
				cadVeiculoFull.setCveiId(rs.getInt("cvei_id"));
				cadVeiculoFull.setCveiPlaca(rs.getString("cvei_placa"));
				cadVeiculoFull.setCveiDisplay(rs.getString("cvei_display"));
				
				cadVeiculoFull.setCveiTcorId(rs.getInt("cvei_tcor_id"));
				cadVeiculoFull.setCveiTcorDescricao(rs.getString("tcor_descricao"));
				
				cadVeiculoFull.setCveiTtveId(rs.getInt("cvei_ttve_id"));
				cadVeiculoFull.setCveiTtveDescricao(rs.getString("ttve_descricao"));
				
				cadVeiculoFull.setCveiCequId(rs.getInt("cvei_cequ_id"));
				cadVeiculoFull.setCveiCequNumSerial(rs.getLong("cequ_numserial"));
				
				cadVeiculoFull.setCveiCcliId(rs.getInt("cvei_ccli_id"));
				cadVeiculoFull.setCveiCcliNome(rs.getString("ccli_nome"));
				
				cadVeiculoFull.setCveiDataIns(rs.getTimestamp("cvei_data_ins"));
				cadVeiculoFull.setCveiDataStatus(rs.getTimestamp("cvei_data_status"));
				cadVeiculoFull.setCveiAtivo(rs.getString("cvei_ativo"));
				
				return cadVeiculoFull;
			}
			
		} catch(SQLException e) {
			System.out.println("Erro ao listar Get(Full): " + e);
			
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		
		return null;
	}
	
	public static ArrayList<CadVeiculoMini> list(String filtroPlaca, String filtroDisplay){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = "SELECT cvei_id, cvei_placa, cvei_display, cvei_tcor_id, tcor_descricao " + 
					 "FROM " +
					 	"cad_veiculo " +
					 	"INNER JOIN tab_cor ON tcor_id = cvei_tcor_id " +
					 "WHERE cvei_placa ILIKE ? " +
					 "AND cvei_display ILIKE ?";
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			
			pstm.setString(1, "%" + filtroPlaca.trim() + "%");
			pstm.setString(2, "%" + filtroDisplay.trim() + "%");
			
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			ArrayList<CadVeiculoMini> alVeiculosCadastrados = new ArrayList<CadVeiculoMini>();
			while(rs.next()) {
				CadVeiculoMini cadVeiculo = new CadVeiculoMini();
				
				cadVeiculo.setCveiId(rs.getInt("cvei_id"));
				cadVeiculo.setCveiPlaca(rs.getString("cvei_placa"));
				cadVeiculo.setCveiDisplay(rs.getString("cvei_display"));
				
				cadVeiculo.setCveiTcorId(rs.getInt("cvei_tcor_id"));
				cadVeiculo.setCveiTcorDescricao(rs.getString("tcor_descricao"));
				
				alVeiculosCadastrados.add(cadVeiculo);
			}
			
			return alVeiculosCadastrados;
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar list(Mini): " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
	public static ArrayList<TabCor> listTabCores(){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM tab_cor");
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			ArrayList<TabCor> alCores = new ArrayList<TabCor>();
			
			while(rs.next()) {
				TabCor tabCor = new TabCor();
				tabCor.setTcorId(rs.getInt("tcor_id"));
				tabCor.setTcorDescricao(rs.getString("tcor_descricao"));
				
				alCores.add(tabCor);
			}
			
			return alCores;
			
		} catch (SQLException e) {
			System.out.println("Veiculo DAO -> Erro ao listar tab_cor: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
	public static ArrayList<TabTipoVeiculo> listTabTipoVeiculos(){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM tab_tipo_veiculo");
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			ArrayList<TabTipoVeiculo> alTipos = new ArrayList<TabTipoVeiculo>();
			
			while(rs.next()) {
				TabTipoVeiculo tipo = new TabTipoVeiculo();
				tipo.setTtveId(rs.getInt("ttve_id"));
				tipo.setTtveDescricao(rs.getString("ttve_descricao"));
				
				alTipos.add(tipo);
			}
			
			return alTipos;
			
		} catch (SQLException e) {
			System.out.println("Veiculo DAO -> Erro ao listar tecnologias: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
	public static ArrayList<CadEquipamento> listCadEquipamentos(CadVeiculoFull cadVeiculo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT cequ_id, cequ_numserial FROM cad_equipamento " +
					 					 	"LEFT JOIN cad_veiculo ON cvei_cequ_id = cequ_id " +
					 					 "WHERE cvei_cequ_id IS NULL or cvei_cequ_id = ?");
			System.out.println(pstm);
			
			pstm.setObject(1, cadVeiculo.getCveiCequId());
			
			rs = pstm.executeQuery();
			
			ArrayList<CadEquipamento> alEquipamentosDisponivelParaVeiculo = new ArrayList<CadEquipamento>();
			
			while(rs.next()) {
				CadEquipamento cadEquipamento = new CadEquipamento();
				cadEquipamento.setCequId(rs.getInt("cequ_id"));
				cadEquipamento.setCequNumSerial(rs.getLong("cequ_numserial"));
				
				alEquipamentosDisponivelParaVeiculo.add(cadEquipamento);
			}
			
			return alEquipamentosDisponivelParaVeiculo;
			
		} catch (SQLException e) {
			System.out.println("Veiculo DAO -> Erro ao listar equipamentos disponiveis para veiculo: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
	
	public static ArrayList<CadClienteFull> listCadClientes(CadVeiculoFull cadVeiculo){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			pstm = conn.prepareStatement("SELECT ccli_id, ccli_nome FROM cad_cliente");
			System.out.println(pstm);
			
			rs = pstm.executeQuery();
			
			ArrayList<CadClienteFull> alClientes = new ArrayList<CadClienteFull>();
			
			while(rs.next()) {
				CadClienteFull cadCliente = new CadClienteFull();
				cadCliente.setCcliId(rs.getInt("ccli_id"));
				cadCliente.setCcliNome(rs.getString("ccli_nome"));
				
				alClientes.add(cadCliente);
			}
			
			return alClientes;
			
		} catch (SQLException e) {
			System.out.println("Veiculo DAO -> Erro ao listar equipamentos disponiveis: " + e);
			
		} finally {
			ConnectionFactory.closeConnection(conn, pstm, rs);
			
		}
		return null;
	}
}
