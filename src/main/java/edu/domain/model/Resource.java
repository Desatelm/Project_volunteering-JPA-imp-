package edu.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Resource")
public class Resource {
   
	public Resource() {
		super();
	}
	@Id
	@GeneratedValue
	private int resourceId;
	
	@Column(name = "Skill")
	private String skill;
	
	@Column(name = "Assest")
	private String assest;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Task task; 
	
	public Resource(String skill, String assest, Task task) {
		super();
		this.skill = skill;
		this.assest = assest;
		this.task = task;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int id) {
		this.resourceId = id;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getAssest() {
		return assest;
	}
	public void setAssest(String assest) {
		this.assest = assest;
	}
}
