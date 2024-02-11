package com.modelo.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.modelo.jdbc.dao.CadVeiculoDAO;
import com.modelo.model.CadClienteFull;
import com.modelo.model.CadEquipamento;
import com.modelo.model.CadVeiculoFull;
import com.modelo.model.CadVeiculoMini;
import com.modelo.model.TabCor;
import com.modelo.model.TabTipoVeiculo;

@ManagedBean(name = "CadVeiculoController")
@SessionScoped
public class CadVeiculoController {
	private CadVeiculoFull cadVeiculoEdit = new CadVeiculoFull();
	private CadVeiculoMini cadVeiculoFiltro = new CadVeiculoMini();
	
	private ArrayList<CadVeiculoMini> alCadVeiculos = null;
	private ArrayList<CadClienteFull> alCadClientes = null;
	private ArrayList<CadEquipamento> alCadEquipamentos = null;
	private ArrayList<TabTipoVeiculo> alTabTiposVeiculos = null;
	private ArrayList<TabCor> alTabCores = null;
	
	public CadVeiculoController() {
		carregaAlTabTiposVeiculos();
		carregaAlTabCores();
	}

	public void insert() {
		
		if(validateError()) {
			addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Preencha os campos corretamente!");
			
		} else {
			CadVeiculoDAO.insert(cadVeiculoEdit);
			cadVeiculoEdit = new CadVeiculoFull();
			addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Veículo cadastrado com sucesso!");
			
		}
	}
	
	public void edit() {
		
		if(cadVeiculoEdit.getCveiPlaca() != null && cadVeiculoEdit.getCveiDisplay() != null) {
			
			CadVeiculoDAO.edit(cadVeiculoEdit);
			addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Veículo atualizado!");
			return;
			
		}
	}
	
	public void filtrar() {
		
		if ((cadVeiculoFiltro.getCveiPlaca() != null && !cadVeiculoFiltro.getCveiPlaca().trim().isEmpty())
				|| (cadVeiculoFiltro.getCveiDisplay() != null && !cadVeiculoFiltro.getCveiDisplay().trim().isEmpty())) {

			alCadVeiculos = CadVeiculoDAO.list(cadVeiculoFiltro.getCveiPlaca(), cadVeiculoFiltro.getCveiDisplay());

		} else {
			alCadVeiculos = CadVeiculoDAO.list(cadVeiculoFiltro.getCveiPlaca(), cadVeiculoFiltro.getCveiDisplay());

		}
	}
	
	public String goInsert() {
		cadVeiculoEdit = new CadVeiculoFull();
		carregaAlCadEquipamentos();
		carregaAlCadClientes();
		return "/cadVeiculo/cadVeiculoInsert.xhtml";
		
	}
	
	public String goEdit() {
		cadVeiculoEdit = CadVeiculoDAO.getCadVeiculoFull(cadVeiculoEdit);
		carregaAlCadClientes();
		carregaAlCadEquipamentos();
		return "/cadVeiculo/cadVeiculoEdit.xhtml";
	}
	
	public String goView() {
		alCadVeiculos = null;
		return "/cadVeiculo/cadVeiculoView.xhtml";
			
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
        	addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public boolean validateError() {
		
		boolean verificaVei = false;
		
		if(cadVeiculoEdit.getCveiDisplay().length() < 2) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Deve conter no mínimo 2 caracteres!");
			verificaVei = true;
			
		} 
		
		if(CadVeiculoDAO.verificaPlacaExistente(cadVeiculoEdit.getCveiPlaca())) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Placa já existe!");
			verificaVei = true;
			
		}
		
		return verificaVei;
	}
	
	public void atualizaMaskPlaca() {
		cadVeiculoEdit.inputMaskPlaca(cadVeiculoEdit);
		
	}
	
	public void carregaAlTabCores() {
		
		alTabCores = CadVeiculoDAO.listTabCores();
		
	}
	
	public void carregaAlTabTiposVeiculos() {
		
		alTabTiposVeiculos = CadVeiculoDAO.listTabTipoVeiculos();
		
	}
	
	public void carregaAlCadEquipamentos() {
		
		alCadEquipamentos = CadVeiculoDAO.listCadEquipamentos(cadVeiculoEdit);
		
	}
	
	public void carregaAlCadClientes() {
		
		alCadClientes = CadVeiculoDAO.listCadClientes(cadVeiculoEdit);
		
	}

	public ArrayList<CadClienteFull> getAlCadClientes() {
		return alCadClientes;
		
	}
	
	public ArrayList<TabCor> getAlTabCores() {
		return alTabCores;
		
	}
	
	public ArrayList<CadEquipamento> getAlCadEquipamentos() {
		return alCadEquipamentos;
		
	}
	
	public ArrayList<CadVeiculoMini> getAlCadVeiculos() {
		return alCadVeiculos;
		
	}

	public ArrayList<TabTipoVeiculo> getAlTabTiposVeiculos() {
		return alTabTiposVeiculos;
		
	}

	public CadVeiculoFull getCadVeiculoEdit() {
		return cadVeiculoEdit;
		
	}

	public void setCadVeiculoEdit(CadVeiculoFull cadVeiculo) {
		this.cadVeiculoEdit = cadVeiculo;
		
	}
	
	public CadVeiculoMini getCadVeiculoFiltro() {
		return cadVeiculoFiltro;
	}

	public void setCadVeiculoFiltro(CadVeiculoFull cadVeiculoFiltro) {
		this.cadVeiculoFiltro = cadVeiculoFiltro;
	}
}