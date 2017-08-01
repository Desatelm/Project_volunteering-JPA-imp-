package edu.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity
@Table(name = "Project")
public class Project {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "Loaction")
	private String location;
	
	@Embedded
	TimeFrame timeframe;
	
	@OneToMany(mappedBy = "project")	
	private List<Task> tasks;
	
	@OneToMany(mappedBy = "project")
	/*@JoinTable(name = "Project_Beneficiary", 
	           joinColumns = @JoinColumn(name = "id" ),
	           inverseJoinColumns =  @JoinColumn(name = "beneficiaryId") )*/
	private List<Beneficiary> beneficiaries;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project() {
		super();
	}

	public Project(String location, TimeFrame timeframe) {
		super();		
		this.location = location;
		this.timeframe = timeframe;			
	}
	
	public TimeFrame getTimeframe() {
		return timeframe;
	}
	public void setTimeframe(TimeFrame timeframe) {
		this.timeframe = timeframe;
	}
	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}
	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
