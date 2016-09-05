package com.rspective.interview.vouch;


import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.rspective.interview.vouch.auth.User;
import com.rspective.interview.vouch.auth.VoucherAuthenticator;
import com.rspective.interview.vouch.resources.CampaignResource;
import com.rspective.interview.vouch.resources.VoucherResource;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Environment;

public class VouchApplication extends Application<VouchConfiguration> {

	
	public static void main(String[] args) throws Exception {
		new VouchApplication().run(args);
	}
	
	@Override
	public void run(VouchConfiguration configuration, Environment environment) throws Exception {
	    final VoucherResource voucherResource = new VoucherResource();
	    final CampaignResource campaignResource = new CampaignResource();
	    environment.jersey().register(voucherResource);
	    environment.jersey().register(campaignResource);
	    
	    environment.jersey().register(new AuthDynamicFeature(
	            new BasicCredentialAuthFilter.Builder<User>()
	                .setAuthenticator(new VoucherAuthenticator())
	                //.setAuthorizer(new ExampleAuthorizer())
	                .setRealm("VouchForItUser")
	                .buildAuthFilter()));
	    environment.jersey().register(RolesAllowedDynamicFeature.class);
	    //If you want to use @Auth to inject a custom Principal type into your resource
	    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));	}

}
