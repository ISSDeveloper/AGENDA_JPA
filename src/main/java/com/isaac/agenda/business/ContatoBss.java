package com.isaac.agenda.business;

import java.util.List;

import com.isaac.agenda.domain.Contato;
import com.isaac.agenda.exception.BssException;

public class ContatoBss extends Bss<Contato> {

	private static final long serialVersionUID = 1L;

	public Contato getEntity(Long codContato) throws BssException {

		try {
			return dao.getEntity(codContato);

		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public List<Contato> getList() throws BssException {

		try {
			return dao.getList("order by codContato");

		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public Contato create(Contato contato) throws BssException {

		try {
			contato.setCodContato(Long.valueOf(dao.getNextCodInt("codContato")));

			dao.persist(contato);

			return contato;
		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public void update(Contato contato) throws BssException {

		try {
			dao.merge(contato);
		} catch (Exception e) {
			throw new BssException(e);
		}
	}

	public void remove(Long codContato) throws BssException {
		try {
			dao.remove(codContato);
		} catch (Exception e) {
			throw new BssException(e);
		}
	}

}
