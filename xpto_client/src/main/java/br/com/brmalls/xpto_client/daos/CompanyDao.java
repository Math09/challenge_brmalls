package br.com.brmalls.xpto_client.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.brmalls.xpto_client.dtos.CompanyDTO;
import br.com.brmalls.xpto_client.models.CompanyModel;

@Repository
public interface CompanyDao extends JpaRepository<CompanyModel, Integer> {
	
	@Query( value = "select razao_social as socialName, nome_fantasia as fantasyName from empresa where cnpj = :cnpj", nativeQuery = true )
	CompanyDTO getCompanyByCnpj( @Param( value = "cnpj" ) String cnpj );
	
}
