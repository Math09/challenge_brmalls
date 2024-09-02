package br.com.brmalls.xpto.daos;

import br.com.brmalls.xpto.models.CompanyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@ExtendWith( SpringExtension.class )
@AutoConfigureTestDatabase( replace = Replace.NONE )
public class CompanyDAOTest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>( "mysql:8.0" )
            .withDatabaseName( "xpto" )
            .withUsername( "PLACEHOLDER_DB_USERNAME" )
            .withPassword( "PLACEHOLDER_DB_PASSWORD" );

    @DynamicPropertySource
    static void registerMySQLProperties( DynamicPropertyRegistry registry ) {
        registry.add( "spring.datasource.url", () -> "jdbc:mysql://localhost:3306/xpto" );
        registry.add( "spring.datasource.username", mySQLContainer::getUsername );
        registry.add( "spring.datasource.password", mySQLContainer::getPassword );
    }

    @Autowired
    private CompanyDAO companyDAO;

    @BeforeEach
    void setUp() {
        // ---
    }

    @Test
    void existCNPJInDB() {
        final Optional<CompanyModel> company = companyDAO.findByCnpj( "CNPJ_PLACEHOLDER" );

        assertTrue( company.isPresent(), "The company must exist in the database" );
        assertEquals( "Empresa LMN ME", company.get().getSocialName(), "Company social name should match" );
    }

    @Test
    void notExistCNPJInDB() {
        final Optional<CompanyModel> company = companyDAO.findByCnpj( "INVALID_CNPJ_PLACEHOLDER" );
        assertTrue( company.isEmpty(), "The company must not exist in the database." );
    }

}
