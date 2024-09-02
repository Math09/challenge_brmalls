package br.com.brmalls.xpto.dtos;

import lombok.Data;

@Data
public class CompanyDataResponseDTO {

    public String status;

    public String cnpj;

    public String nome;

    public String fantasia;

    public CompanyDataResponseDTO() {
        // ---
    }

    public CompanyDataResponseDTO( String status, String cnpj, String nome, String fantasia ) {
        this.status = status;
        this.cnpj = cnpj;
        this.nome = nome;
        this.fantasia = fantasia;
    }

    @Override
    public String toString() {
        return "CompanyDataResponseDTO{" +
                "status='" + status + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", fantasia='" + fantasia + '\'' +
                '}';
    }

}
