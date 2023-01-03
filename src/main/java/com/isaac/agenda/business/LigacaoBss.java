package com.isaac.agenda.business;

import java.util.List;

import com.isaac.agenda.domain.Ligacao;
import com.isaac.agenda.domain.LigacaoId;
import com.isaac.agenda.exception.BssException;

public class LigacaoBss extends Bss<Ligacao> {

	private static final long serialVersionUID = 1L;

	public Ligacao getEntity(Long codLigacao) throws BssException {

		try {
			return dao.getEntity(codLigacao);

		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public List<Ligacao> getList() throws BssException {

		try {
			return dao.getList("order by contato.nome, id.codLigacao");

		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public Ligacao create(Ligacao ligacao) throws BssException {

		try {
			LigacaoId id = new LigacaoId();
			id.setCodLigacao(Long
					.valueOf(dao.getNextCodInt("id.codLigacao", "id.codContato = " + ligacao.getContato().getCodContato())));
			id.setCodContato(ligacao.getContato().getCodContato());
			ligacao.setId(id);

			dao.persist(ligacao);

			return ligacao;
		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public void update(Ligacao Ligacao) throws BssException {

		try {
			dao.merge(Ligacao);
		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public void remove(LigacaoId id) throws BssException {
		try {
			dao.remove(id);
		} catch (Exception e) {
			throw new BssException(e);
		}
	}
}
