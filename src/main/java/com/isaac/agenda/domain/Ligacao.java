package com.isaac.agenda.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LIGACAO")
public class Ligacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LigacaoId id;

	@Column(name = "DATA_HORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	@Column(name = "OBSERVACAO")
	private String observacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_CONTATO", insertable = false, updatable = false)
	private Contato contato;

	public Ligacao() {
	}

	public Ligacao(Contato contato, String observacao, Date dataHora) {
		this.dataHora = dataHora;
		this.observacao = observacao;
		this.contato = contato;
	}

	public Ligacao(Contato contato) {
		this.contato = contato;
	}

	public LigacaoId getId() {
		return id;
	}

	public void setId(LigacaoId id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contato, dataHora, id, observacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ligacao other = (Ligacao) obj;
		return Objects.equals(contato, other.contato) && Objects.equals(dataHora, other.dataHora)
				&& Objects.equals(id, other.id) && Objects.equals(observacao, other.observacao);
	}

}
