package br.com.brmalls.xpto_client.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brmalls.xpto_client.models.CompanyModel;

@Repository
public interface CompanyDao extends JpaRepository<CompanyModel, Integer> {
	
}
