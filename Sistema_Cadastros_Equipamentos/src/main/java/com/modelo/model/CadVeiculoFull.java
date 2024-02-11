package com.modelo.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CadVeiculoFull extends CadVeiculoMini {
	
	private Integer cveiTtveId = null;
	private String cveiTtveDescricao = null;
	private Integer cveiCequId = null;
	private Long cveiCequNumSerial = null;
	private Integer cveiCcliId = null;
	private String cveiCcliNome = null;
	private Timestamp cveiDataIns = null;
	private Timestamp cveiDataStatus = null;
	private String cveiAtivo = null;
	
	private String tipoPlaca = null;
	private String maskPlaca = null;
	private Integer maxLenghtInput = null;

	public Integer getCveiTtveId() {
		return cveiTtveId;
	}

	public void setCveiTtveId(Integer cveiTtveId) {
		this.cveiTtveId = cveiTtveId;
	}

	public String getCveiTtveDescricao() {
		return cveiTtveDescricao;
	}

	public void setCveiTtveDescricao(String ttveDescricao) {
		this.cveiTtveDescricao = ttveDescricao;
	}

	public Integer getCveiCequId() {
		return cveiCequId;
	}

	public void setCveiCequId(Integer cveiCequId) {
		this.cveiCequId = cveiCequId;
	}

	public Long getCveiCequNumSerial() {
		return cveiCequNumSerial;
	}
	
	public String getCveiCequNumSerialMasked() {
		if(cveiCequNumSerial == 0) {
			return "";
		}
		
		return String.valueOf(cveiCequNumSerial);
	}

	public void setCveiCequNumSerial(Long cveiCequNumserial) {
		this.cveiCequNumSerial = cveiCequNumserial;
	}

	public Integer getCveiCcliId() {
		return cveiCcliId;
	}

	public void setCveiCcliId(Integer cveiCcliId) {
		this.cveiCcliId = cveiCcliId;
	}

	public String getCveiCcliNome() {
		return cveiCcliNome;
	}

	public void setCveiCcliNome(String cveiCcliNome) {
		this.cveiCcliNome = cveiCcliNome;
	}

	public Timestamp getCveiDataIns() {
		return cveiDataIns;
	}
	
	public String getCveiDataInsMasked() {
		if(cveiDataIns != null) {
			String dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cveiDataIns);
			return dtFormat;
		}
		
		return "";
	}

	public void setCveiDataIns(Timestamp cveiDataIns) {
		this.cveiDataIns = cveiDataIns;
	}

	public Timestamp getCveiDataStatus() {
		return cveiDataStatus;
	}
	
	public String getCveiDataStatusMasked() {
		if(cveiDataStatus != null) {
			String dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cveiDataStatus);
			return dtFormat;
		}
		
		return "";
	}

	public void setCveiDataStatus(Timestamp cveiDataStatus) {
		this.cveiDataStatus = cveiDataStatus;
	}

	public String getCveiAtivo() {
		return cveiAtivo;
	}

	public void setCveiAtivo(String cveiAtivo) {
		this.cveiAtivo = cveiAtivo;
	}

	public String getTipoPlaca() {
		return tipoPlaca;
	}

	public void setTipoPlaca(String tipoPlaca) {
		this.tipoPlaca = tipoPlaca;
	}
	
	public String getMaskPlaca() {
		return maskPlaca;
	}

	public void setMaskPlaca(String maskPlaca) {
		this.maskPlaca = maskPlaca;
	}

	public Integer getMaxLenghtInput() {
		return maxLenghtInput;
	}

	public void setMaxLenghtInput(Integer maxLenghtInput) {
		this.maxLenghtInput = maxLenghtInput;
	}

	public void inputMaskPlaca(CadVeiculoFull cadVeiculo) {
		if("Antiga".equals(cadVeiculo.getTipoPlaca())) {
			cadVeiculo.setMaskPlaca("AAA-9999");
			cadVeiculo.setMaxLenghtInput(8);
			
		} else if("Mercosul".equals(cadVeiculo.getTipoPlaca())) {
			cadVeiculo.setMaskPlaca("AAA-9A99");
			cadVeiculo.setMaxLenghtInput(8);
			
		} else {
			cadVeiculo.setMaskPlaca("");
			cadVeiculo.setMaxLenghtInput(0);
		}
	}
}
