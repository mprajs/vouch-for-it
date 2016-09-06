package com.rspective.interview.vouch.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.Voucher;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/campaign")
@Produces(MediaType.APPLICATION_JSON)
public class CampaignResource {

	private CampaignDAO dao;
	
	public CampaignResource(CampaignDAO dao) {
		this.dao = dao;
	}
	
	@GET
	@Timed
	@UnitOfWork
	public Campaign getByPrefix(@QueryParam("prefix") String prefix) {
		Campaign result = dao.findByPrefix(prefix);
		return result;
	}
	
	@GET
	@Path("create")
	@UnitOfWork
	public Campaign createCampaign(@QueryParam("quantity") int quantity, @QueryParam("prefix") String prefix) {
		Campaign c = new Campaign();
		c.setPrefix(prefix);
		Set<Voucher> vouchers = new HashSet<>();
		for (int i = 0; i < quantity; i++) {
			vouchers.add(Voucher.generateVoucher());
		}
		c.setVouchers(vouchers);
		return dao.create(c);
	}
}
