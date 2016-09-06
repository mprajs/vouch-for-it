package com.rspective.interview.vouch.resources;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rspective.interview.vouch.dao.VoucherDAO;

@Path("/voucher")
@Produces(MediaType.APPLICATION_JSON)
public class VoucherResource {

	public VoucherResource(VoucherDAO dao) {
		
	}
}
