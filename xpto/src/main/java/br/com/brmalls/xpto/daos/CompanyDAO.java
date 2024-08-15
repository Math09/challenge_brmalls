package br.com.brmalls.xpto.daos;

import br.com.brmalls.xpto.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyDAO extends JpaRepository<CompanyModel, Integer> {

    Optional<CompanyModel> findByCnpj( String cnpj );

}
