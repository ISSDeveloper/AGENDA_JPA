package com.isaac.agenda.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTATO")
public class Contato implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codContato;
	private String nome;
	private Long telefone;
	
	public Contato() {
		
	}
	public Contato(Long codContato) {
		this.codContato = codContato;
	}

	public Contato(String nome, Long telefone) {
		this.nome = nome;
		this.telefone = telefone;
	}

	public Contato(Long codContato, String nome, Long telefone) {
		this.codContato = codContato;
		this.nome = nome;
		this.telefone = telefone;
	}

	@Id
	
	@Column(name = "COD_CONTATO")
	public Long getCodContato() {
		return codContato;
	}

	public void setCodContato(Long codContato) {
		this.codContato = codContato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codContato, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contato other = (Contato) obj;
		return Objects.equals(codContato, other.codContato) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefone, other.telefone);
	}
}
