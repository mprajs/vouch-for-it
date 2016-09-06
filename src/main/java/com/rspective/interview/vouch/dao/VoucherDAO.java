package com.rspective.interview.vouch.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.rspective.interview.vouch.model.Voucher;

import io.dropwizard.hibernate.AbstractDAO;

public class VoucherDAO extends AbstractDAO<Voucher> {

	public VoucherDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

    public Optional<Voucher> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Voucher create(Voucher voucher) {
        return persist(voucher);
    }
    
    public List<Voucher> findByCampaignPrefix(String prefix) {
    	return list(namedQuery("findByCampaignPrefix").setString("prefix", prefix));
    }

}
