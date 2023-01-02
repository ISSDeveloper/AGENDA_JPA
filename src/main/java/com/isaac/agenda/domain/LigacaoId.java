package com.isaac.agenda.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LigacaoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "COD_LIGACAO")
	private Long codLigacao;

	@Column(name = "COD_CONTATO")
	private Long codContato;

	public LigacaoId() {
	}

	public LigacaoId(Long codLigacao, Long codContato) {
		this.codLigacao = codLigacao;
		this.codContato = codContato;
	}

	public Long getCodLigacao() {
		return codLigacao;
	}

	public void setCodLigacao(Long codLigacao) {
		this.codLigacao = codLigacao;
	}

	public Long getCodContato() {
		return codContato;
	}

	public void setCodContato(Long codContato) {
		this.codContato = codContato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codContato, codLigacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigacaoId other = (LigacaoId) obj;
		return Objects.equals(codContato, other.codContato) && Objects.equals(codLigacao, other.codLigacao);
	}
}
