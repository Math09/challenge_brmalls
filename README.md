# Desafio desenvolvido para a empresa brMalls.

<p align="center" width="100%">
    <img src="./images/logo_brmalls.png"> 
</p>

**Informações** </br>
 * **Vaga:** Dev Java
 * **Entrega:** 09/06/2021

---

A empresa XPTO está desenvolvendo um novo sistema de vendas e solicita ao *Time de Integração* uma *API* para recuperar os dados dos clientes no momento de criar uma ordem de venda. A *API* segue as seguintes regras:

 - Deve retornar os seguintes dados: Razão Social e Nome Fantasia;
 - O parâmetro informado para a busca será o CNPJ da empresa, sem caracteres especiais;
 - As informações devem ser buscadas primariamente na tabela "EMPRESA" do banco de dados do sistema de cadastro de clientes;
 - Quando o CNPJ não for encontrado no banco de dados, deve-se tentar buscar essas informações em algum serviço de terceiros, criando esse novo registro na tabela "EMPRESA" antes de retornar na requisição.
 
Você não é proibido de colocar "algo a mais" no seu projeto, fique à vontade para nos mostrar toda a sua criatividade e conhecimento, mas é importante ter em mente que o que está sendo avaliado não é a quantidade de features. Para esse teste é **obrigatório** do uso da **linguagem Java** e do **framework Spring** mas qualquer **banco dados relacional** é **permitido**. Caso não conheça nenhum serviço público de consulta de dados via CNPJ, sugerimos o ReceitaWS (https://www.receitaws.com.br/) que nesse momento oferece uma opção gratuita de utilização do serviço suficiente para este teste. O código fonte deve ser compartilhado em um repositório git.
