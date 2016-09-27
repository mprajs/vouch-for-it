package com.rspective.interview.vouch.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.CampaignRequestObject;
import com.rspective.interview.vouch.model.Voucher;

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
	public Response getByPrefix(@QueryParam("prefix") String prefix) {
		Campaign result = dao.findByPrefix(prefix);
		if (result == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(result).build();
	}
	
	@POST
	@UnitOfWork
	@Timed
	public Response createCampaign(CampaignRequestObject request) {
		Campaign c = new Campaign();
		c.setPrefix(request.getPrefix());
		Set<Voucher> vouchers = new HashSet<>();
		for (int i = 0; i < request.getQuantity(); i++) {
			vouchers.add(Voucher.generateVoucher());
		}
		c.setVouchers(vouchers);
		Campaign created = dao.createOrUpdate(c);
		if (created == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.created(URI.create("?prefix="+request.getPrefix())).build();
	}
}
