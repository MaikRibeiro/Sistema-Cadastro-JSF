package com.modelo.model;

public class CadClienteMini {
	private Integer ccliId;
	private String ccliNome;
	private String ccliCnpjCpf;
	
	private String tipoCliente;
	private String maskCnpjCpf;
	private Integer maxlengthCnpjCpf;
	
	public int getCcliId() {
		return ccliId;
	}
	
	public void setCcliId(int ccliId) {
		this.ccliId = ccliId;
	}
	
	public String getCcliNome() {
		return ccliNome;
	}
	
	public void setCcliNome(String ccliNome) {
		this.ccliNome = ccliNome;
	}
	
	public String getCcliCnpjCpf() {
		return ccliCnpjCpf;
	}
	
	public void setCcliCnpjCpf(String ccliCnpjCpf) {
		this.ccliCnpjCpf = ccliCnpjCpf;
	}
	
	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getMaskCnpjCpf() {
		return maskCnpjCpf;
	}

	public void setMaskCnpjCpf(String maskCnpjCpf) {
		this.maskCnpjCpf = maskCnpjCpf;
	}

	public Integer getMaxlengthCnpjCpf() {
		return maxlengthCnpjCpf;
	}

	public void setMaxlengthCnpjCpf(Integer maxlengthCnpjCpf) {
		this.maxlengthCnpjCpf = maxlengthCnpjCpf;
	}
	
	public void inputMaskCnpjCPF(CadClienteMini cadCliente) {
	    if ("juridica".equals(cadCliente.getTipoCliente())) {
	        cadCliente.setMaskCnpjCpf("99.999.999/9999-99");
	        cadCliente.setMaxlengthCnpjCpf(18);
	        
	    } else if ("fisica".equals(cadCliente.getTipoCliente())) {
	    	cadCliente.setMaskCnpjCpf("999.999.999-99");
	    	cadCliente.setMaxlengthCnpjCpf(14);
	    
	    } else {
	    	cadCliente.setMaskCnpjCpf(null);
	    	cadCliente.setMaxlengthCnpjCpf(0);
	    }
	}
}
