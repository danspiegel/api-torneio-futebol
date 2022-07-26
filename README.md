## Processo para rodar a aplicação local

- Faça um clone do projeto para a sua máquina.
- Na pasta raiz do projeto, rode o comando "mvn clean install" para gerar as classes do Swagger que são necessárias para o funcionamento da aplicação.
- Caso a IDE não reconheça os imports das classes do Swagger após rodar o comando "mvn clean install", deve-se rodar o reload das dependencias do maven no caso do IntelliJ ou "update project" do maven no Eclipse.
- Navegue até a pasta "docker" e rode o comando "docker-compose up" para que suba os containers do RabbitMQ e o MySQL que são necessários.
- Faça o start da aplicação.
- Acesse a URL http://localhost:8080/swagger-ui.html para testar.
