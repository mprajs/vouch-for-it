package com.rspective.interview.vouch;


import com.rspective.interview.vouch.auth.User;
import com.rspective.interview.vouch.auth.VoucherAuthentication;
import com.rspective.interview.vouch.resources.CampaignResource;
import com.rspective.interview.vouch.resources.VoucherResource;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class VouchApplication extends Application<VouchConfiguration> {

	
	public static void main(String[] args) throws Exception {
		new VouchApplication().run(args);
	}
	
	@Override
	public void run(VouchConfiguration configuration, Environment environment) throws Exception {
	    final VoucherResource voucherResource = new VoucherResource();
	    final CampaignResource campaignResource = new CampaignResource();
	    final BasicAuthProvider<User> authProvider = new BasicAuthProvider<User>(new VoucherAuthentication(),
                "SUPER SECRET STUFF");
	    environment.jersey().register(voucherResource);
	    environment.jersey().register(campaignResource);
	    environment.jersey().register(authProvider);
	}

}
