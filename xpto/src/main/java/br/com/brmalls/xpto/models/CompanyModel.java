package br.com.brmalls.xpto.models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "empresa" )
@AttributeOverride( name = "id", column = @Column (name = "id") )
public class CompanyModel extends AbstractModel {

    @Column( name = "cnpj" )
    public @Getter @Setter String cnpj;

    @Column( name = "razao_social" )
    public @Getter @Setter String socialName;

    @Column( name = "nome_fantasia" )
    public @Getter @Setter String fantasyName;

    public String toString() {
        return "CompanyModel{ " + "id=" + getId() + ", cnpj='" + getCnpj() + '\'' + ", socialName='" + getSocialName() + '\'' + ", fantasyName='" + getFantasyName() + '}';
    }

}
