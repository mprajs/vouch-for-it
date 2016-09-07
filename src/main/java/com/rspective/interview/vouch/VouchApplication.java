package com.rspective.interview.vouch;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.rspective.interview.vouch.auth.User;
import com.rspective.interview.vouch.auth.VoucherAuthenticator;
import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.dao.VoucherDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.Voucher;
import com.rspective.interview.vouch.resources.CampaignResource;
import com.rspective.interview.vouch.resources.VoucherResource;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class VouchApplication extends Application<VouchConfiguration> {

	public static void main(String[] args) throws Exception {
		new VouchApplication().run(args);
	}

	private final HibernateBundle<VouchConfiguration> hibernateBundle = new HibernateBundle<VouchConfiguration>(
			Campaign.class, Voucher.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(VouchConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};
	
	@Override
	public void initialize(Bootstrap<VouchConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}

	@Override
	public void run(VouchConfiguration configuration, Environment environment) throws Exception {

		final VoucherDAO voucherDao = new VoucherDAO(hibernateBundle.getSessionFactory());
		final CampaignDAO campaignDao = new CampaignDAO(hibernateBundle.getSessionFactory());

		final VoucherResource voucherResource = new VoucherResource(voucherDao, campaignDao);
		final CampaignResource campaignResource = new CampaignResource(campaignDao);
		environment.jersey().register(voucherResource);
		environment.jersey().register(campaignResource);

		environment.jersey()
				.register(new AuthDynamicFeature(
						new BasicCredentialAuthFilter.Builder<User>().setAuthenticator(new VoucherAuthenticator())
								// .setAuthorizer(new ExampleAuthorizer())
								.setRealm("VouchForItUser").buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
	}

}
