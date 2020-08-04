# DevCloud

- DevCloud는 개발을 하면서 생기는 궁금증을 해결하기 위해 만들어진 사이트입니다.

- 얼마든지 자유롭게 질문하세요.

- 다른 사람의 질문에 답하고 채택을 받으면 점수를 획득할 수 있고, 점수 랭킹을 올릴 수 있습니다.


## Setting up the Backend Server(Spring-boot)

+ **Setting up the docker**
    ```bash
    > docker run --name maria-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD=<YOUR_DB_PASSWORD> -d mariadb
    > docker exec -it maria-db mysql -u root -p
    ```

+ **Create Maria-DB database**
    ```bash
    maria-db> create database ssafydb
    ```

+ **Configure database**
    ```yml
    # backend/src/main/resources/application.yml
    spring:
        datasource:
            url: jdbc:mysql://localhost:3306/spring_social?useSSL=false
            username: <YOUR_DB_USERNAME>
            password: <YOUR_DB_PASSWORD>
    ```

+ **Specify OAuth2 Provider ClientId and ClientSecrets**
	```yml
    security:
      oauth2:
        client:
          registration:
            google:
              clientId: <GOOGLE_CLIENT_ID>
              clientSecret: <GOOGLE_CLIENT_SECRET>
              redirectUriTemplate: "{baseUrl}/oauth2/callback/{registrationId}"
              scope:
                - email
                - profile
	```

+ **Run backend server**
	```bash
	> mvn spring-boot:run
	```

## Setting up the Frontend Server(Vue.js)

```bash
cd frontend
npm run serve
```



## License

##### Spring Boot

Apache 2.0 license.

##### Vue.js

MIT
Copyright (c) 2013-present, Yuxi (Evan) You

##### Bootstrap-vue

Released under the MIT License. Copyright (c) BootstrapVue.

##### MariaDB

GNU GPL 2 license.

##### Docker5

Apache 2.0 license.