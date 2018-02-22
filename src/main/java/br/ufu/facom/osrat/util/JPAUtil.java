package br.ufu.facom.osrat.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class JPAUtil {
	
	private static Logger log = Logger.getLogger(JPAUtil.class);

	private static final String PERSISTENCE_UNIT_NAME = "appOSRat";
	
	private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
        	log.error("Could not load the EntityManagerFactory: "+ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static EntityManager createEntityManager() {
    	if (!emf.isOpen())
    		throw new RuntimeException("EntityManagerFactory is closed!");
    	return emf.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
    	if (emf.isOpen()) {
    		emf.close();
    	}
    }

}
