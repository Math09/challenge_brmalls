package br.com.brmalls.xpto_client.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


public abstract class AbstractService<T> {
	
	@Transactional
	public T create( final T model ) {
		return this.dao.save( model );
	}
	
	public List<T> list() {
		final List<T> list = this.dao.findAll();
		return list;
	}
	
	@Autowired
	protected JpaRepository<T, Integer> dao;
	
}
