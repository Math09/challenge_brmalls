package br.com.brmalls.xpto_client.models;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "empresa" )
@AttributeOverride( name = "id", column = @Column( name = "id_empresa" ) )
public class CompanyModel extends AbstractModel {
	
	@Column( name = "cnpj" )
	public @Getter @Setter String cnpj;
	
	@Column( name = "razao_social" )
	public @Getter @Setter String socialName;
	
	@Column( name = "nome_fantasia" )
	public @Getter @Setter String fantasyName;
	
}
