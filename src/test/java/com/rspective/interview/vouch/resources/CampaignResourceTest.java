package com.rspective.interview.vouch.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.CampaignRequestObject;

public class CampaignResourceTest {

	private static final CampaignDAO dao = mock(CampaignDAO.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new CampaignResource(dao))
		.build();
	
	private static Campaign campaign = new Campaign();
	
	static {
		
		campaign.setPrefix("ABCD");
	}
	
	@Before
	public void setup() {
		when(dao.findByPrefix(eq("ABCD"))).thenReturn(campaign);
		when(dao.createOrUpdate(any(Campaign.class))).thenReturn(campaign);
	}
	
	@After
	public void tearDown() {
		reset(dao);
	}
	
	@Test
	public void testFetchByPrefix() {
		assertThat(resources.client().target("/campaign?prefix=ABCD").request().get(Campaign.class).getPrefix()).isEqualTo("ABCD");
	}
	
	@Test
	public void testCreateCampaign() {
		CampaignRequestObject request = new CampaignRequestObject();
		request.setPrefix("ABCD");
		request.setQuantity(5);
		Response response = resources.client().target("/campaign").request().post(Entity.json(request), Response.class);
		assertThat(response).isNotNull();
		String location = response.getHeaders().get("Location").get(0).toString();
		assertThat(location).isNotNull();
		assertThat(location).contains("prefix=ABCD");
	}
}
