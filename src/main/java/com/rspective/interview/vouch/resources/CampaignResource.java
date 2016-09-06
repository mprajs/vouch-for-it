package com.rspective.interview.vouch.resources;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rspective.interview.vouch.dao.CampaignDAO;

@Path("/campaign")
@Produces(MediaType.APPLICATION_JSON)
public class CampaignResource {

	public CampaignResource(CampaignDAO dao) {
		
	}
}
