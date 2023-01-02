package com.isaac.agenda.factory;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EMFactory {

	public static EntityManager getEntityManager() {

		disableLogging();
		return Persistence.createEntityManagerFactory("snAgendaPersistenceUnit").createEntityManager();
	}

	private static void disableLogging() {

		LogManager logManager = LogManager.getLogManager();
		Logger logger = logManager.getLogger("");
		logger.setLevel(Level.SEVERE); // could be Level.OFF
	}
}
