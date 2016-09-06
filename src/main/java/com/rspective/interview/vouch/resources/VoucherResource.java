package com.rspective.interview.vouch.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.rspective.interview.vouch.dao.VoucherDAO;
import com.rspective.interview.vouch.model.Voucher;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/voucher")
@Produces(MediaType.APPLICATION_JSON)
public class VoucherResource {

	private VoucherDAO dao;
	
	public VoucherResource(VoucherDAO dao) {
		this.dao = dao;
	}
	
	@GET
	@Timed
	@UnitOfWork
	public List<Voucher> getByCampaignPrefix(@QueryParam("prefix") String prefix) {
		return dao.findByCampaignPrefix(prefix);
	}
}
