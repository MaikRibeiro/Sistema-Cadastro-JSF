package com.modelo.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.modelo.jdbc.dao.CadClienteDAO;
import com.modelo.model.CadClienteFull;
import com.modelo.model.CadClienteMini;

@ManagedBean(name = "CadClienteController")
@SessionScoped

public class CadClienteController {
	private CadClienteFull cadClienteEdit = new CadClienteFull();
	private CadClienteMini cadClienteFiltro = new CadClienteMini();
	private ArrayList<CadClienteFull> alCadClientes = null;

	public void insert() {
		
		if(validateError()) {
			addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Preencha os campos corretamente!");
			return;
		}
		
		else {
			CadClienteDAO.insert(cadClienteEdit);
			addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente cadastrado!");
			cadClienteEdit = new CadClienteFull();
		}
	}
	
	public void edit() {
		
		if(cadClienteEdit.getCcliNome().length() < 3) {
			addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Mínimo 3 letras para Nome/Razão Social!");
			return;
			
		} else if(cadClienteEdit.getCcliCnpjCpf().length() < 11) {
			addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Mínimo 11 algarismos para campo CPF/CNPJ!");
			return;

		} else {
			CadClienteDAO.edit(cadClienteEdit);
			cadClienteEdit = new CadClienteFull();
			addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente atualizado!");
		}
	}
	
	public void filtrar() {
		
		if ((cadClienteFiltro.getCcliNome() != null && !cadClienteFiltro.getCcliNome().trim().isEmpty())
				|| (cadClienteFiltro.getCcliCnpjCpf() != null && !cadClienteFiltro.getCcliCnpjCpf().trim().isEmpty())) {

			alCadClientes = CadClienteDAO.list(cadClienteFiltro.getCcliNome(), cadClienteFiltro.getCcliCnpjCpf());

		} else {
			alCadClientes = CadClienteDAO.list(cadClienteFiltro.getCcliNome(), cadClienteFiltro.getCcliCnpjCpf());
			
		}
	}
	
	public String goInsert() {
		cadClienteEdit = new CadClienteFull();
		return "/cadCliente/cadClienteInsert.xhtml";
	}
	
	public String goEdit() {
		cadClienteEdit = CadClienteDAO.getCadClienteFull(cadClienteEdit);
		return "/cadCliente/cadClienteEdit.xhtml";
	}
	
	public String goView() {
		alCadClientes = null;
		return "/cadCliente/cadClienteView.xhtml";
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public boolean validateError() {
		
		boolean verifica = false;
		
		if(cadClienteEdit.getCcliNome().length() < 3) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Mínimo 3 letras para Nome/Razão Social!");
			verifica = true;
			
		}
		
		if(CadClienteDAO.verificaCpfCnpjExistente(cadClienteEdit.getCcliCnpjCpf())) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "CPF/CNPJ já cadastrado!");
			verifica = true;
		}
		
		return verifica;
		
	}
	
	public void atualizaMaskCnpjCpf() {
		cadClienteEdit.inputMaskCnpjCPF(cadClienteEdit);
		
	}
	
	public CadClienteFull getCadClienteEdit() {
		return cadClienteEdit;
	}
	
	public void setCadClienteEdit(CadClienteFull cadClienteEdit) {
		this.cadClienteEdit = cadClienteEdit;
	}
	
	public CadClienteMini getCadClienteFiltro() {
		return cadClienteFiltro;
	}
	
	public void setCadClienteFiltro(CadClienteFull cadClienteFiltro) {
		this.cadClienteFiltro = cadClienteFiltro;
	}
	
	public ArrayList<CadClienteFull> getAlCadClientes() {
		return alCadClientes;
	}
	
	public void setAlCadClientes(ArrayList<CadClienteFull> alCadClientes) {
		this.alCadClientes = alCadClientes;
	}
}
