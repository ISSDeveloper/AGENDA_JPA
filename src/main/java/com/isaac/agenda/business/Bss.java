package com.isaac.agenda.business;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import com.isaac.agenda.factory.EMFactory;
import com.isaac.agenda.persistence.Dao;

public abstract class Bss<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected EntityManager em;

	protected Dao<T> dao;
	
	@SuppressWarnings("unchecked")
	public Bss() {
		em = EMFactory.getEntityManager();
		
		Class<T> persistClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		dao = new Dao<T>(persistClass, em);
	}

	public T getEntity(Object id) {

		return dao.getEntity(id);
	}

	public T merge(T t) {

		return dao.merge(t);
	}

	public void persist(T t) {

		dao.persist(t);
	}
}
