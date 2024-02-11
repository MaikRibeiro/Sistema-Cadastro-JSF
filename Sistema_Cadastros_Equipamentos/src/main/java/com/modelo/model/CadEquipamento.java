package com.modelo.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CadEquipamento {
	private Integer cequId = null;
	private Long cequNumSerial = null;
	private Integer cequTtecId = null;
	private String ttecDescricao = null;
	private Timestamp cequDataIns = null;
	private Timestamp cequDataStatus = null;
	private String cequAtivo = null;

	public Integer getCequId() {
		return cequId;
	}

	public void setCequId(Integer cequId) {
		this.cequId = cequId;
	}
	
	public String getCequNumSerialMasked() {
		if(cequNumSerial == null) {
			return"";
		}
		return String.valueOf(cequNumSerial);
	}
	
	public Long getCequNumSerial() {
		return cequNumSerial;
	}
	
	public void setCequNumSerial(Long cequNumSerial) {
		this.cequNumSerial = cequNumSerial;
	}

	public Integer getCequTtecId() {
		return cequTtecId;
	}

	public void setCequTtecId(Integer cequTtecId) {
		this.cequTtecId = cequTtecId;
	}

	public String getTtecDescricao() {
		return ttecDescricao;
	}

	public void setTtecDescricao(String ttecDescricao) {
		this.ttecDescricao = ttecDescricao;
	}

	public Timestamp getCequDataIns() {
		return cequDataIns;
	}
	
	public String getCequDataInsMasked() {
		if(cequDataIns != null) {
			String dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cequDataIns);
			return dtFormat;
		}
		
		return "";
	}

	public void setCequDataIns(Timestamp cequDataIns) {
		this.cequDataIns = cequDataIns;
	}

	public Timestamp getCequDataStatus() {
		return cequDataStatus;
	}
	
	public String getCequDataStatusMasked() {
		if(cequDataStatus != null) {
			String dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cequDataStatus);
			return dtFormat;
		}
		
		return "";
	}

	public void setCequDataStatus(Timestamp cequDataStatus) {
		this.cequDataStatus = cequDataStatus; 
	}

	public String getCequAtivo() {
		return cequAtivo;
	}

	public void setCequAtivo(String cequAtivo) {
		this.cequAtivo = cequAtivo;
	}
}
