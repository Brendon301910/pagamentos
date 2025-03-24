
# **Pagamentos API**

Este projeto fornece uma API para gerenciar **pagamentos**, com funcionalidades de **criação**, **atualização de status**, **exclusão lógica** e **listagem de pagamentos**. A API foi construída com foco em segurança, boas práticas e princípios como **SOLID**.

## **Tecnologias Utilizadas**

- **Spring Boot** - Framework para desenvolvimento de aplicações Java
- **H2 Database** - Banco de dados em memória utilizado para persistência de dados
- **JUnit** - Framework para testes unitários
- **Mockito** - Framework para simulação de objetos em testes
- **Spring Data JPA** - Biblioteca para interação com o banco de dados
- **Lombok** - Biblioteca para reduzir o código boilerplate
- **MapStruct** - Ferramenta para mapeamento de objetos DTO e entidades

## **Pré-requisitos**

Certifique-se de ter as seguintes ferramentas instaladas:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/) (Caso não tenha, instale com `apt-get install maven` ou `brew install maven`)

## **Instalação**

1. Clone o repositório:

   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd <diretorio_do_repositorio>
   ```

2. Instale as dependências utilizando o Maven:

   ```bash
   mvn install
   ```

## **Configuração do Ambiente**

Este projeto utiliza variáveis de ambiente para configurar a conexão com o banco de dados. Crie um arquivo `.env` na raiz do projeto com as variáveis a seguir:

```env
# Configuração do banco de dados H2
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

## **Como rodar o projeto**

1. Para rodar a aplicação localmente, execute o seguinte comando:

   ```bash
   mvn spring-boot:run
   ```

Isso irá iniciar o servidor na porta `8080` por padrão.

## **Testes**

Para rodar os testes da aplicação, utilize o seguinte comando:

```bash
mvn test
```

Isso vai executar todos os testes unitários e de integração configurados para a aplicação.

## **Endpoints**

### **POST /pagamentos**

Cria um novo pagamento.

#### Exemplo de Request:

```json
{
  "codigoDebito": 123456,
  "cpfCnpj": "12345678900",
  "metodoPagamento": "Cartão",
  "numeroCartao": "4111111111111111",
  "valor": 100.0
}
```

#### Exemplo de Response:

```json
{
  "id": 1,
  "codigoDebito": 123456,
  "cpfCnpj": "12345678900",
  "metodoPagamento": "Cartão",
  "valor": 100.0,
  "status": "PENDENTE"
}
```

### **PUT /pagamentos/status**

Atualiza o status de um pagamento.

#### Exemplo de Request:

```json
{
  "id": 1,
  "status": "PROCESSADO_SUCESSO"
}
```

#### Exemplo de Response:

```json
{
  "id": 1,
  "codigoDebito": 123456,
  "cpfCnpj": "12345678900",
  "metodoPagamento": "Cartão",
  "valor": 100.0,
  "status": "PROCESSADO_SUCESSO"
}
```

### **GET /pagamentos**

Lista todos os pagamentos com filtros opcionais:

- **codigoDebito** (Integer)
- **cpfCnpj** (String)
- **status** (StatusPagamento)

#### Exemplo de Request:

```bash
GET /pagamentos?codigoDebito=123456
```

#### Exemplo de Response:

```json
[
  {
    "id": 1,
    "codigoDebito": 123456,
    "cpfCnpj": "12345678900",
    "metodoPagamento": "Cartão",
    "valor": 100.0,
    "status": "PENDENTE"
  }
]
```

### **DELETE /pagamentos/{id}**

Exclui logicamente um pagamento (atualiza o status para INATIVO).

#### Exemplo de Request:

```bash
DELETE /pagamentos/1
```

#### Exemplo de Response:

```json
{
  "id": 1,
  "codigoDebito": 123456,
  "cpfCnpj": "12345678900",
  "metodoPagamento": "Cartão",
  "valor": 100.0,
  "status": "INATIVO"
}
```

## **Licença**

Este projeto está licenciado sob a [MIT License](LICENSE).
