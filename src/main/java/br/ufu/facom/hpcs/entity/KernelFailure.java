package br.ufu.facom.hpcs.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufu.facom.osrat.model.AbstractEntity;

@Entity
@Table(name = "kernel_failure")
public class KernelFailure extends AbstractEntity {

	private String code;

	private String name;

	@NotNull
	@Size(min = 10, max = 10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotNull
	@Size(max = 200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "[ " + code + " - " + name + " ]";
	}

}
