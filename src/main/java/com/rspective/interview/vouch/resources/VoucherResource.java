package com.rspective.interview.vouch.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.dao.VoucherDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.Voucher;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/voucher")
@Produces(MediaType.APPLICATION_JSON)
public class VoucherResource {

	private VoucherDAO dao;
	private CampaignDAO cDao;
	
	public VoucherResource(VoucherDAO dao, CampaignDAO cDao) {
		this.dao = dao;
		this.cDao = cDao;
	}
	
	@GET
	@Timed
	@UnitOfWork
	public List<Voucher> getByCampaignPrefix(@QueryParam("prefix") String prefix) {
		return dao.findByCampaignPrefix(prefix);
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("get")
	public Voucher getByCode(@QueryParam("code") String code) {
		return dao.findByCode(code);
	}
	
	@GET
	@Timed
	@UnitOfWork
	@Path("use")
	public Voucher useVoucher(@QueryParam("code") String code, @QueryParam("price") Double price) {
		Voucher v = dao.findByCode(code);
		if (!v.getActive().booleanValue()) {
			return null;
		}
		v.setActive(false);
		dao.createOrUpdate(v);
		v.setPriceAfterDiscount(calculatePrice(price, v));
		return v;
	}
	
	private Double calculatePrice(Double price, Voucher v) {
		Double discountedPrice;
		if (v.getDiscountType() == 0) {
			discountedPrice = price-price*(v.getDiscountValue()/100);
		} else {
			discountedPrice = price - v.getDiscountValue();
		}
		return discountedPrice;
	}

	public Set<Voucher> addVouchers(@QueryParam("campaign") String campaign, @QueryParam("quantity") int quantity) {
		Campaign c = cDao.findByPrefix(campaign);
		Set<Voucher> vouchers = new HashSet<>();
		for (int i = 0; i < quantity; i++) {
			vouchers.add(Voucher.generateVoucher());
		}
		if (c == null) {
			c = new Campaign();
			c.setId(0L);
			c.setVouchers(vouchers);
		} else {
			c.getVouchers().addAll(vouchers);
		}
		cDao.createOrUpdate(c);
		return vouchers;

	}
}
