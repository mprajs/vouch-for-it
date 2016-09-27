package com.rspective.interview.vouch.resources;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.rspective.interview.vouch.dao.CampaignDAO;
import com.rspective.interview.vouch.dao.VoucherDAO;
import com.rspective.interview.vouch.model.Campaign;
import com.rspective.interview.vouch.model.Voucher;

public class VoucherResourceTest {
	
	private static final CampaignDAO cDao = mock(CampaignDAO.class);
	private static final VoucherDAO vDao = mock(VoucherDAO.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new VoucherResource(vDao, cDao))
		.build();

	@Before
	public void setup() {
		when(vDao.createOrUpdate(any(Voucher.class)));
		when(vDao.findByCampaignPrefix(any(String.class)));
		when(vDao.findByCode(any(String.class)));
		when(cDao.findByPrefix(any(String.class)));
		when(cDao.createOrUpdate(any(Campaign.class)));
	}

	@After
	public void tearDown() {
		reset(vDao);
		reset(cDao);
	}
	
	@Test
	public void testFindByCampaignPrefix() {
	}
	
	@Test
	public void testAddVouchers() {
		
	}
	
	@Test
	public void testUseVoucher() {
		
	}
	
	@Test
	public void testFindByCode() {
		
	}
}
