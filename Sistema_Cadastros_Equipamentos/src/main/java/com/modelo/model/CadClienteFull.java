package com.modelo.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CadClienteFull extends CadClienteMini {
	
	private Timestamp ccliDataIns = null;
	private Timestamp ccliDataStatus = null;
	private String ccliAtivo = null;
	
	public Timestamp getCcliDataIns() {
		return ccliDataIns;
	}
	
	public String getCcliDataInsMasked() {
		if(ccliDataIns != null) {
			String dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ccliDataIns);
			return dtFormat;
		}
		return "";
	}
	
	public void setCcliDataIns(Timestamp ccliDataIns) {
		this.ccliDataIns = ccliDataIns;
	}
	
	public Timestamp getCcliDataStatus() {
		return ccliDataStatus;
	}
	
	public String getCcliDataStatusMasked() {
		if(ccliDataStatus != null) {
			String dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ccliDataStatus);
			return dtFormat;
		}
		return "";
	}
	
	public void setCcliDataStatus(Timestamp ccliDataStatus) {
		this.ccliDataStatus = ccliDataStatus;
	}
	
	public String getCcliAtivo() {
		return ccliAtivo;
	}

	public void setCcliAtivo(String ccliAtivo) {
		this.ccliAtivo = ccliAtivo;
	}
}
