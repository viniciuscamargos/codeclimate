package br.ufu.facom.hpcs.agent.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;

import br.ufu.facom.hpcs.entity.Sdf;
import br.ufu.facom.osrat.model.AbstractEntity;
import br.ufu.facom.osrat.model.TypeLog;

@Entity
@Table(name = "tb_record")
public class RecordBean extends AbstractEntity {

	//private Integer id;
	private String computerName;
	private int eventIdentifier;
	private List<String> insertionStrings;
	private String logfile;
	private String message;
	private String productName;
	private long recordNumber;
	private String sourceName;
	private long timeGenerated;
	private String user;
	private String insertion;

	private TypeLog typeLog;
	private Sdf sdf;

	public RecordBean() {
		insertionStrings = new ArrayList<String>();

		setComputerName("");
		setEventIdentifier(-1);
		setLogfile("");
		setMessage("");
		setProductName("");
		setRecordNumber(-1);
		setSourceName("");
		setTimeGenerated(-1);
		setUser("");
	}

	/*public  Integer getId() {
		return id;
	}

	public  void setId( Integer id) {
		this.id = id;
	}*/

	@Column
	public  String getComputerName() {
		return computerName;
	}

	public  void setComputerName( String computerName) {
		this.computerName = computerName;
	}

	@Column
	public  int getEventIdentifier() {
		return eventIdentifier;
	}

	public  void setEventIdentifier( int eventIdentifier) {
		this.eventIdentifier = eventIdentifier;
	}

	@Transient
	public  List<String> getInsertionStrings() {
		return insertionStrings;
	}

	public  void addInsertionString( String insertionStrings) {
		this.insertionStrings.add(insertionStrings);
		if(this.insertion == null){
			insertion = "";
		}
		this.insertion += (insertionStrings + "\n");
	}

	@Column
	public  String getLogfile() {
		return logfile;
	}

	public  void setLogfile( String logfile) {
		this.logfile = logfile;
	}

	@Column(length = 2000)
	public  String getMessage() {
		return message;
	}

	public  void setMessage( String message) {
		this.message = message;
	}

	@Column
	public  String getProductName() {
		return productName;
	}

	public  void setProductName( String productName) {
		this.productName = productName;
	}

	@Column
	public  long getRecordNumber() {
		return recordNumber;
	}

	public  void setRecordNumber( long recordNumber) {
		this.recordNumber = recordNumber;
	}

	@Column
	public  String getSourceName() {
		return sourceName;
	}

	public  void setSourceName( String sourceName) {
		this.sourceName = sourceName;
	}

	@Column
	public  long getTimeGenerated() {
		return timeGenerated;
	}

	public  void setTimeGenerated( long timeGenerated) {
		this.timeGenerated = timeGenerated;
	}

	@Column
	public  String getUser() {
		return user;
	}

	public  void setUser( String user) {
		this.user = user;
	}

	@Enumerated(EnumType.STRING)
	@Column
	public  TypeLog getTypeLog() {
		return typeLog;
	}

	public  void setTypeLog(TypeLog typeLog) {
		this.typeLog = typeLog;
	}
	
	@Column(length = 4000)
	public String getInsertion() {
		return insertion;
	}
	
	public void setInsertion(String insertion) {
		this.insertion = insertion;
	}
	
	@ManyToOne
	public Sdf getSdf() {
		return sdf;
	}
	
	public void setSdf(Sdf sdf) {
		this.sdf = sdf;
	}
	
	/*@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder().append(this.getId(), ((RecordBean)obj).getId());   			
		return builder.isEquals();
	}*/

}
