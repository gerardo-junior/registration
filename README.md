
# [User Register and auth](https://github.com/gerardo-junior/registration)

Este é um projeto Spring Boot configurado para ser executado em contêineres Docker usando Docker Compose. Ele fornece um ambiente de desenvolvimento consistente e facilita a implantação em diferentes ambientes.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina antes de começar:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Configuração

1. Clone o repositório para a sua máquina local:

```bash
   git clone https://github.com/gerardo-junior/registration.git
```
Navegue até o diretório do projeto:

```bash
cd registration
```

Executando o projeto com Docker Compose
No diretório do projeto, execute o seguinte comando para criar e iniciar os contêineres:

```bash
docker compose up -d
```

Aguarde até que os contêineres sejam iniciados e o aplicativo esteja pronto para uso.

Acesse o aplicativo em http://localhost:8080.

Parando e Removendo os Contêineres
Para parar e remover os contêineres, execute o seguinte comando:

```bash
docker compose down
```
Isso encerrará os contêineres e removerá os recursos associados.

## Como usar
uma vez os containers rodando para criar usuários para executar esse request

```bash
curl --location 'http://localhost:8080/api/v1/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstname": "Xablau",
    "lastname": "da silva",
    "dateOfBirth": "1991-04-01",
    "document": "89016061002",
    "email": "xabla@example.com",
    "password": "12xablaU@",
    "address": "R. sei la onde, 69",
    "mobileNumber": "1199999999",
    "gender": "MALE"
}'

# {
#     "meta": {
#         "code": "UserCreated",
#         "message": "User created successfully"
#     },
#     "data": {
#         "access_token": "...",
#         "refresh_token": "..."
#     }
# }
```
no campo `access_token` retorna jwt que é utilizado como token de autenticação que deve ser passado no header `authentication`.  para facilitar é recomendado adicionar esse token em uma variável de ambiente `export TOKEN=...`

caso o token expirar basta executar esse request:

```bash
curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "xabla@example.com",
    "password": "12xablaU@"
}'

# {
#     "meta": {
#         "code": "AuthSuccessfully",
#         "message": "Authentication completed successfully"
#     },
#     "data": {
#         "access_token": "...",
#         "refresh_token": "..."
#     }
# }

```

Recomendo adicionar o conteudo do campo `access_token` em uma varaivel `export TOKEN=<access_token>`

Para pesquisar por algum usuário:

```bash
curl --location --request GET 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $TOKEN" \
--data '{
    "fullName": "Xablau"
}'

# {
#  "meta": {
#  "code": "UserListed",
#  "message": "User Listed successfully",
#  "pageable": {
#  		...
# 	},
#  "data": [
#  		...
# 	]
# }
```

Detalhes de usuários

```bash
curl --location 'http://localhost:8080/api/v1/users/89016061002' \
--header "Authorization: Bearer $TOKEN"

# {
# 	"meta": {
# 		"code": "UserFound",
# 		"message": "User found successfully"
# 	},
# 	"data": {
# 		...
# 	}
# }
```
Update de usuario.

```bash
curl --location --request PUT 'http://localhost:8080/api/v1/users/89016061002' \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $TOKEN" \
--data  '{
    "firstname": "xablau",
    "lastname": "da silva",
    "password": "xabl!A2",
    "address": "R. sei la onde, 69",
    "mobileNumber": "2197677274",
    "gender": "MALE"
}'

# {
# 	"meta":  {
# 		"code":  "UserUpdated",
# 		"message":  "User updated successfully"
# 	},
# 	"data":  {
# 		...
# 	}
# }
```

para mais informações basta acessar o swagger do projeto: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Contribuindo
Se você quiser contribuir para este projeto, por favor, siga estas etapas:

Fork do repositório
Crie uma branch para sua contribuição: ```git checkout -b feature/nova-feature```
Faça suas alterações e faça commit: ```git commit -m 'Adiciona nova feature'```
Envie suas alterações: ```git push origin feature/nova-feature```
Abra uma solicitação de pull
Obrigado por contribuir!

Licença
Este projeto é licenciado sob a Licença MIT.
