package com.isaac.agenda.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Dao<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;

	private Class<T> clazz;

	public Dao(Class<T> entityClass, EntityManager em) {

		this.clazz = entityClass;
		this.em = em;
	}

	public void persist(T t) {
		
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}

	public T merge(T t) {
		
		em.getTransaction().begin();
		t = em.merge(t);
		em.getTransaction().commit();
		
		return t;
	}

	public void detach(T d) {
		
		em.getTransaction().begin();
		em.detach(d);
		em.getTransaction().commit();
	}

	public void refresh(T t) {
		
		em.getTransaction().begin();
		em.refresh(t);
		em.getTransaction().commit();
	}
	
	public void remove(Object pk) {

		em.getTransaction().begin();
		em.remove(getEntity(pk));
		em.getTransaction().commit();
	}

	public T getEntity(Object pk) {

		return (T) em.find(clazz, pk);
	}

	public T getEntityByCond(String comp) {

		try {
			TypedQuery<T> query = em.createQuery("select o from " + clazz.getName() + " o where " + comp, clazz);
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<T> getList() {

		TypedQuery<T> query = em.createQuery("select o from " + clazz.getName() + " o ", clazz);
		return query.getResultList();
	}

	public List<T> getList(String comp) {

		TypedQuery<T> query = em.createQuery("select o from " + clazz.getName() + " o " + comp, clazz);
		return query.getResultList();
	}

	public List<T> getListByCond(String comp) {

		TypedQuery<T> query = em.createQuery("select o from " + clazz.getName() + " o where " + comp, clazz);
		return query.getResultList();
	}

	public void execute(String sql) {

		Query query = em.createQuery(sql);
		query.executeUpdate();
	}
	
	public void executeNativeQuery(String sql) {

		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
	}

	public List<Object> getListDistinct(String campo) {

		TypedQuery<Object> query = em.createQuery("select distinct(" + campo + ") from " + clazz.getName(),
				Object.class);
		return query.getResultList();
	}

	public List<Integer> getListDistinct(String campo, String comp) {

		TypedQuery<Integer> query = em
				.createQuery("select distinct(" + campo + ") from " + clazz.getName() + " o " + comp, Integer.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T getEntityByNativeQuery(String sql) {

		Query query = em.createNativeQuery(sql, clazz);
		return (T) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getListByNativeQuery(String sql) {

		Query query = em.createNativeQuery(sql, clazz);
		return query.getResultList();
	}

	public Object getSigleByNativeQueryTypeless(String condicao) {

		Query query = em.createNativeQuery(condicao);
		return query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getListByNativeQueryTypeless(String condicao) {

		Query query = em.createNativeQuery(condicao);
		return query.getResultList();
	}

	public Integer getMaxValInt(String campo) {

		Query query = em.createQuery("select max(" + campo + ") from " + clazz.getName());
		Object cod = query.getSingleResult();

		if (cod == null)
			return 0;

		if (cod instanceof Integer)
			return (Integer) cod;

		if (cod instanceof Long)
			return ((Long) cod).intValue();

		if (cod instanceof Short)
			return ((Short) cod).intValue();

		return (Integer) cod;
	}

	public Integer getMaxValInt(String campo, String cond) {

		Query query = em.createQuery("select max(" + campo + ") from " + clazz.getName() + " " + cond);
		Object cod = query.getSingleResult();

		if (cod == null)
			return 0;

		if (cod instanceof Integer)
			return (Integer) cod;

		if (cod instanceof Long)
			return ((Long) cod).intValue();

		if (cod instanceof Short)
			return ((Short) cod).intValue();

		return (Integer) cod;
	}

	public Integer getNextCodInt(String campo) {

		return getMaxValInt(campo) + 1;
	}

	public Integer getNextCodInt(String campo, String cond) {

		return getMaxValInt(campo, cond) + 1;
	}

	public boolean codIsFree(String campo, Integer cod) {

		Query query = em.createQuery("select 1 from " + clazz.getName() + " where " + campo + " = " + cod);
		return query.getResultList().size() == 0;
	}

	public Long countLong(String condicao) {

		Query query = em.createQuery("select count(*) from " + clazz.getName() + " where " + condicao);
		Object cod = query.getSingleResult();

		if (cod instanceof Long)
			return (Long) cod;

		if (cod instanceof Integer)
			return ((Integer) cod).longValue();

		if (cod instanceof Short)
			return ((Short) cod).longValue();

		return (Long) cod;
	}
	
	public Long countLongByNativeQuery(String condicao) {

		Query query = em.createNativeQuery(condicao);
		Object cod = query.getSingleResult();

		return ((BigDecimal) cod).longValue();
	}
}
