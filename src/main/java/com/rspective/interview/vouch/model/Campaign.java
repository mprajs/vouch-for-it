package com.rspective.interview.vouch.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
		@NamedQuery(
				name = "findByPrefix",
				query = "Select c From Campaign c Where c.prefix = :prefix"
				)
	}
)
@SequenceGenerator(
		  name = "CAMPAIGN_SEQ_GENERATOR",
		  sequenceName = "CAMPAIGN_SEQ",
		  initialValue = 1, allocationSize = 1)

public class Campaign {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CAMPAIGN_SEQ_GENERATOR")
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
