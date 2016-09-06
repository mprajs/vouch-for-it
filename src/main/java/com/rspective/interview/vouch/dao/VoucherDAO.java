package com.rspective.interview.vouch.dao;

import org.hibernate.SessionFactory;

import com.rspective.interview.vouch.model.Voucher;

import io.dropwizard.hibernate.AbstractDAO;

public class VoucherDAO extends AbstractDAO<Voucher> {

	public VoucherDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
