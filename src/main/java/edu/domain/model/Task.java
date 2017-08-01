package edu.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class Task {
	
	@Id
	@GeneratedValue
	private int taskId;
	
	@Column(name = "status" )
	private String status;
	
	@Embedded
	private TimeFrame timeframe;
	
	@OneToMany(mappedBy="task")	
	private List<Resource> resource;
    
	public Task() {
		super();
	}
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Project project;
	
	
	public Task(String status, TimeFrame timeframe, Project project) {
		super();
		this.status = status;
		this.timeframe = timeframe;		
		this.project = project;
	}
	public int getId() {
		return taskId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public void setId(int id) {
		this.taskId = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TimeFrame getTimeframe() {
		return timeframe;
	}
	public void setTimeframe(TimeFrame timeframe) {
		this.timeframe = timeframe;
	}
	public List<Resource> getResource() {
		return resource;
	}
	public void setResource(List<Resource> resource) {
		this.resource = resource;
	}
	
}
