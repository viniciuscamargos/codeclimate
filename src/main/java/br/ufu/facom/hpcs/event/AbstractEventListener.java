package br.ufu.facom.hpcs.event;

public interface AbstractEventListener<M extends AbstractEvent<?>> {

	 public void handleEvent(M event);

}
