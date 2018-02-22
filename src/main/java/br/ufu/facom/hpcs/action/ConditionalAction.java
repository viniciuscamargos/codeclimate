package br.ufu.facom.hpcs.action;

public final class ConditionalAction extends AbstractAction {

	private AbstractAction action;
	
	private BooleanExpression expression;
	
	private ConditionalAction(){}
	
	@Override
	protected void action() {
		if (action == null) {
			throw new IllegalArgumentException("Tell a action to be performed, use the addAction method.");
		}
		
		if (expression == null) {
			throw new IllegalArgumentException("Telle conditional expression of Action, use the addConditional method.");
		}
		
		if (expression.conditional()) {
			action.actionPerformed();
		}
	}
	
	public static ConditionalAction build(){
		return new ConditionalAction();
	}
	
	public ConditionalAction addAction(AbstractAction action) {
		this.action = action;
		return this;
	}
	
	public ConditionalAction addConditional(BooleanExpression expression) {
		this.expression = expression;
		return this;
	}
	
}
