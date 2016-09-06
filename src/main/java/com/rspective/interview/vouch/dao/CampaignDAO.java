package com.rspective.interview.vouch.dao;

import org.hibernate.SessionFactory;

import com.rspective.interview.vouch.model.Campaign;

import io.dropwizard.hibernate.AbstractDAO;

public class CampaignDAO extends AbstractDAO<Campaign> {

	public CampaignDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

    public Campaign findById(Long id) {
        return get(id);
    }

    public Campaign create(Campaign campaign) {
        return persist(campaign);
    }

}
