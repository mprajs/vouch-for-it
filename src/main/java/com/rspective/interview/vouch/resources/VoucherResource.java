package com.rspective.interview.vouch.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.dao.VoucherDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.Voucher;
import com.rspective.interview.vouch.model.VoucherRequestObject;

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
	public Response getByCampaignPrefix(@QueryParam("prefix") String prefix) {
		List<Voucher> result = dao.findByCampaignPrefix(prefix); 
		if (result == null || result.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(result).build();
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("get")
	public Response getByCode(@QueryParam("code") String code) {
		Voucher result = dao.findByCode(code);
		if (result == null) {
			return Response.status(Response.Status.NOT_FOUND).build(); 
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Timed
	@UnitOfWork
	@Path("use")
	public Response useVoucher(VoucherRequestObject request) {//@RequestParam("code") String code, @QueryParam("price") Double price) {
		Voucher v = dao.findByCode(request.getCode());
		if (v == null || !v.getActive().booleanValue() || new Date().after(v.getCampaign().getDateTo())) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Double result = calculatePrice(request.getPrice(), v); 
		v.setActive(false);
		Voucher updated = dao.createOrUpdate(v);
		if (updated == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(result).build();
	}
	
	private Double calculatePrice(Double price, Voucher v) {
		Double discountedPrice;
		if (v.getDiscountType() == 0) {
			discountedPrice = price-price*(v.getDiscountValue().doubleValue()/100.0);
		} else {
			discountedPrice = price - v.getDiscountValue();
		}
		return discountedPrice;
	}

	@POST
	@Timed
	@UnitOfWork
	public Response addVouchers(VoucherRequestObject request) {
		Campaign c = cDao.findByPrefix(request.getCampaign());
		Set<Voucher> vouchers = new HashSet<>();
		for (int i = 0; i < request.getQuantity(); i++) {
			vouchers.add(Voucher.generateVoucher());
		}
		if (c == null) {
			c = new Campaign();
			c.setId(0L);
			c.setVouchers(vouchers);
		} else {
			c.getVouchers().addAll(vouchers);
		}
		Set<Voucher> result = cDao.createOrUpdate(c).getVouchers();
		if (result == null || result.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.created(URI.create("?prefix="+request.getCampaign())).build();
	}
}
