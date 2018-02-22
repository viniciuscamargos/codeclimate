package br.ufu.facom.hpcs.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.ufu.facom.hpcs.controller.PersistenceController;


public final class TransactionalAction extends AbstractAction {

	private AbstractAction action;
	
	private PersistenceController persistenceController;
	
	private TransactionalAction(){}
	
	@Override
	protected void action() {
		if (action == null) {
			throw new IllegalArgumentException("Tell action to be performed .");
		}
		
		if (persistenceController == null) {
            throw new IllegalArgumentException("Tell the owner of the persistence context.");
        }
		
		EntityManager em = persistenceController.getPersistenceContext();
		if (em == null) {
            throw new IllegalArgumentException("Without reference to the persistence manager.");
        }
		
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			action.action();
			tx.commit();
		} catch(Exception ex) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	protected void preAction() {
		if (action != null) {
			action.preAction();
		}
	}
	
	@Override
	protected void posAction() {
		if (action != null) {
			action.posAction();
		}
	}
	
	@Override
	protected void actionFailure() {
		if (action != null) {
			action.actionFailure();
		}
	}
	
	public static TransactionalAction build() {
		return new TransactionalAction();
	}
	
	public TransactionalAction addAction(AbstractAction action) {
		this.action = action;
		return this;
	}
	
	public TransactionalAction persistenceCtxOwner(PersistenceController persistenceController) {
		this.persistenceController = persistenceController;
		return this;
	}
	
}
