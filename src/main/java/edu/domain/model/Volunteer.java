package edu.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name ="Volunteer")
public class Volunteer {
		
	@Id
    @GeneratedValue
    private int volunteerId;
    
    @Column(name = "volunteer_name")
    private String name;
    
    @Column(name = "skill")
    private String Skill;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkill() {
		return Skill;
	}

	public void setSkill(String skill) {
		Skill = skill;
	}


}
