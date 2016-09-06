package com.rspective.interview.vouch.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Campaign {

	@Id
	private Long id;
	
	@Column
	private String prefix;
	
	@Column(name = "date_from")
	private Date dateFrom;
	
	@Column(name = "date_to")
	private Date dateTo;
	
	@OneToMany(mappedBy = "campaign")
	private Set<Voucher> vouchers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Set<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(Set<Voucher> vouchers) {
		this.vouchers = vouchers;
	}
}
