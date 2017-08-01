package edu.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class TimeFrame {
	
	@Temporal(TemporalType.DATE)
	@Column(name = "StartDate")
	Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EndDate")
	Date endDates;
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDates() {
		return endDates;
	}
	public void setEndDates(Date endDates) {
		this.endDates = endDates;
	}
	

}
