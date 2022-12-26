package com.isaac.agenda.factory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EMFactory {

	public static EntityManager getEntityManager() {
		
		return Persistence.createEntityManagerFactory("snAgendaPersistenceUnit").createEntityManager();
	}
}
