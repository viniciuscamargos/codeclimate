package br.ufu.facom.hpcs.controller;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import br.ufu.facom.osrat.util.JPAUtil;


public abstract class PersistenceController extends AbstractController {

	private static Logger log = Logger.getLogger(PersistenceController.class);

    private EntityManager persistenceContext;
    private boolean ownsPersistenceContext;
	
    public PersistenceController(){ }
    
    public PersistenceController(AbstractController parent){
    	super(parent);
    }
    
    public void loadPersistenceContext() {
    	loadPersistenceContext(null);
    }
    
    protected void loadPersistenceContext(EntityManager persistenceContext) {
    	if (persistenceContext == null) {
            log.debug("Creating a persistence context (EntityManager).");
            this.persistenceContext = JPAUtil.createEntityManager();
            this.ownsPersistenceContext = true;
        } else {
            log.debug("Using persistence context (EntityManager) existing.");
            this.persistenceContext = persistenceContext;
            this.ownsPersistenceContext = false;
        }
    }
    
    public EntityManager getPersistenceContext() {
		return this.persistenceContext;
	}
    
    @Override
    protected void cleanUp() {
        if (ownsPersistenceContext && getPersistenceContext().isOpen()) {
            log.debug("Closing the persistence context (EntityManager).");
            getPersistenceContext().close();
        }
        super.cleanUp();
    }
}
