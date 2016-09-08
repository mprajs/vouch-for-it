package com.rspective.interview.vouch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@SequenceGenerator(
		  name = "VOUCHER_SEQ_GENERATOR",
		  sequenceName = "VOUCHER_SEQ",
		  initialValue = 100, allocationSize = 1)
@NamedQueries({
	@NamedQuery(
			name = "findByCampaignPrefix",
			query = "Select v from Voucher v where campaign.prefix = :prefix"
			),
	@NamedQuery(
			name = "findByCode",
			query = "Select v from Voucher v where v.code = :code"
			)
})
public class Voucher {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "VOUCHER_SEQ_GENERATOR")
	private Long id;
	
	@Column(name = "discount_value")
	private Integer discountValue;
	
	@Column
	private String code;
	
	@Column(name = "discount_type")
	private Integer discountType;
	
	@Column
	private Boolean active;
	
	@ManyToOne
	@JoinColumn(name = "campaign_id")
	@JsonIgnore
	private Campaign campaign;
	
	@Transient
	private Double priceAfterDiscount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Integer discountValue) {
		this.discountValue = discountValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(Double priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public static Voucher generateVoucher() {
		Voucher generated = new Voucher();
		generated.setCode("asdfg");
		return generated;
	}
}
