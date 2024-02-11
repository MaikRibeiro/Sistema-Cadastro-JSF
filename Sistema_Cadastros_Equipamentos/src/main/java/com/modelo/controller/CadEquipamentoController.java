package com.modelo.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.modelo.jdbc.dao.CadEquipamentoDAO;
import com.modelo.model.CadEquipamento;
import com.modelo.model.TabTecnologia;

@ManagedBean(name = "CadEquipamentoController")
@SessionScoped

public class CadEquipamentoController {
	
	private CadEquipamento cadEquipamentoFiltro = new CadEquipamento();
	private CadEquipamento cadEquipamentoEdit = new CadEquipamento();
	private ArrayList<CadEquipamento> alCadEquipamentos = null;
	private ArrayList<TabTecnologia> alTecnologias = null;
	
	public CadEquipamentoController() {
		carregaTecnologias();
	}
	
	public void insert() {
		
		if(validateError()) {
			addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Preencha os campos corretamente!");
		
		} else {
			CadEquipamentoDAO.insert(cadEquipamentoEdit);
			addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipamento cadastrado!");
			cadEquipamentoEdit = new CadEquipamento();
			
		}
		
	}
	
	public void edit() {
		
		if(cadEquipamentoEdit.getCequNumSerial().toString().length() < 10) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Informe no mínimo 10 algarismos para Serial Number!");
			return;
			
		} else {
			CadEquipamentoDAO.edit(cadEquipamentoEdit);
			cadEquipamentoEdit = new CadEquipamento();
			addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipamento atualizado!");
		
		}
	}
	
	public void filtrar() {

		if ((cadEquipamentoFiltro.getCequNumSerial() != null)
				|| (cadEquipamentoFiltro.getTtecDescricao() != null && !cadEquipamentoFiltro.getTtecDescricao().trim().isEmpty())) {

			alCadEquipamentos = CadEquipamentoDAO.list(cadEquipamentoFiltro.getCequNumSerial(), cadEquipamentoFiltro.getTtecDescricao());

		} else {
			alCadEquipamentos = CadEquipamentoDAO.list(cadEquipamentoFiltro.getCequNumSerial(), cadEquipamentoFiltro.getTtecDescricao());

		}
	}
	
	public String goInsert() {
		cadEquipamentoEdit = new CadEquipamento();
		return "/cadEquipamento/cadEquipamentoInsert.xhtml";
		
	}
	
	public String goEdit(CadEquipamento cadEquipamentoEdit) {

		return "/cadEquipamento/cadEquipamentoEdit.xhtml";
	}
	
	public String goView() {
		alCadEquipamentos = null;
		return "/cadEquipamento/cadEquipamentoView.xhtml";
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
        
    }
	
	public void carregaTecnologias() {
		alTecnologias = CadEquipamentoDAO.alTabTecnologia();
	}
	
	public boolean validateError() {
		
		boolean verificaEqui = false;
		
		if(cadEquipamentoEdit.getCequNumSerial().toString().length() < 10) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Informe no mínimo 10 algarismos para Serial Number!");
			verificaEqui = true;
			
		} 
		
		if(CadEquipamentoDAO.verificaSerialNumberExistente(cadEquipamentoEdit.getCequNumSerial())) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Serial Number já cadastrado!");
			verificaEqui = true;
		}
		
		return verificaEqui;
	}
	
	public CadEquipamento getCadEquipamentoFiltro() {
		return cadEquipamentoFiltro;
		
	}
	
	public void setCadEquipamentoFiltro(CadEquipamento cadEquipamentoFiltro) {
		this.cadEquipamentoFiltro = cadEquipamentoFiltro;
		
	}
	
	public CadEquipamento getCadEquipamentoEdit() {
		return cadEquipamentoEdit;
		
	}
	
	public void setCadEquipamentoEdit(CadEquipamento cadEquipamentoEdit) {
		this.cadEquipamentoEdit = cadEquipamentoEdit;
		
	}
	
	public ArrayList<CadEquipamento> getAlCadEquipamentos() {
		return alCadEquipamentos;
		
	}
	
	public void setAlCadEquipamentos(ArrayList<CadEquipamento> alCadEquipamentos) {
		this.alCadEquipamentos = alCadEquipamentos;
		
	}

	public ArrayList<TabTecnologia> getAlTecnologias() {
		return alTecnologias;
	}

	public void setAlTecnologias(ArrayList<TabTecnologia> alTecnologias) {
		this.alTecnologias = alTecnologias;
	}
}
