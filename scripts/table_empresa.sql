CREATE TABLE `empresa` (

	id_empresa INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    cnpj INTEGER(14) NOT NULL,
    razao_social NVARCHAR(255) NOT NULL,
    nome_fantasia NVARCHAR(255) NOT NULL
    
);