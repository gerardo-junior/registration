# [User Register and auth](https://github.com/gerardo-junior/registration)

Este é um projeto Spring Boot configurado para ser executado em contêineres Docker usando Docker Compose. Ele fornece um ambiente de desenvolvimento consistente e facilita a implantação em diferentes ambientes.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina antes de começar:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Configuração

1. Clone o repositório para a sua máquina local:

```bash
   git clone [https://github.com/seu-usuario/spring-boot-docker-compose](https://github.com/gerardo-junior/registration)
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
