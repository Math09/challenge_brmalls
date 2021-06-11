package br.com.brmalls.xpto_client.models;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "empresa" )
@AttributeOverride( name = "id", column = @Column( name = "id_empresa" ) )
public class CompanyModel extends AbstractModel {
	
	@Column( name = "cnpj" )
	public @Getter @Setter String cnpj;
	
	@JsonIgnoreProperties
	@Column( name = "razao_social" )
	public @Getter @Setter String nome;
	
	@JsonIgnoreProperties
	@Column( name = "nome_fantasia" )
	public @Getter @Setter String fantasia;
	
}
