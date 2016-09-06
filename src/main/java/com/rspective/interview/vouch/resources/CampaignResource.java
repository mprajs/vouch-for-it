package com.rspective.interview.vouch.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.model.Campaign;

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
	public Campaign getCampaign(String code) {
		Campaign result = dao.findById(1L);
		System.out.println(result.getVouchers());
		return result;
	}
}
