# โ๏ธDevCloud

> [DevCloud](https://i3c202.p.ssafy.io)

## ๐ ๋ชฉ์ฐจ

- [ํ๊ฒ ์ฌ์ฉ์](#ํ๊ฒ-์ฌ์ฉ์)
- [์ฃผ์ ๊ธฐ๋ฅ](#์ฃผ์-๊ธฐ๋ฅ)
- [์๋ฒ ํ๊ฒฝ ์ธํ](#์๋ฒ-ํ๊ฒฝ-์ธํ)
- [๋ผ์ด์ ์ค](#๋ผ์ด์ ์ค)



## ๐ก ํ๊ฒ ์ฌ์ฉ์

- ํ๊ตญ์ด ๋ต๋ณ์ด ํ์ํ ๊ฐ๋ฐ์
- ๊ฒ์์ผ๋ก ์๊ฒ ๋ ์ ๋ณด๋ฅผ ์ ๋ฆฌํ๊ธฐ ๊ท์ฐฎ์ ๊ฐ๋ฐ์



## ๐ ์ฃผ์ ๊ธฐ๋ฅ

> DevCloud๋ ๊ฐ๋ฐ์ ํ๋ฉด์ ์๊ธฐ๋ ๊ถ๊ธ์ฆ์ ํด๊ฒฐํ๊ธฐ ์ํด ๋ง๋ค์ด์ง ์ฌ์ดํธ์๋๋ค.

1. ๋ณ๋์ ํ์๊ฐ์ ์์ด ๊ตฌ๊ธ ๋ก๊ทธ์ธ์ผ๋ก ์ฌ์ดํธ๋ฅผ ์ด์ฉํ์ธ์.

   ![](./gif/๊ตฌ๊ธ๋ก๊ทธ์ธ.gif)

2. ํค์๋, ํ๊ทธ๋ก ์ํ๋ ๋ด์ฉ์ ๊ฒ์ํด๋ณด์ธ์.

   ![](./gif/๊ฒ์.gif)

3. ๋ด ์ง๋ฌธ, ๋ต๋ณ์ ๋ํ ์ค์๊ฐ ์๋ฆผ์ ๋ฐ์๋ณด์ธ์.

   ![](./gif/์๋ฆผ.gif)

4. ํ๋กํ ํ์ด์ง์์ ๋ด ์ง๋ฌธ, ๋ต๋ณ์ ํ ๋์ ํ์ธํ์ธ์.

   ![](./gif/ํ๋กํ.gif)

5. ๋ค๋ฅธ ์ฌ๋์ ์ง๋ฌธ์ ๋ตํ๊ณ  ์ ์๋ฅผ ํ๋ํ์ธ์.

   ![](./gif/๋ต๋ณ ํ ์ ์ ์์น.gif)

6. ๋ด ์ง๋ฌธ์ ๋ํ ๋ต์ ์ฑํํ์ธ์.

   ![](./gif/์ฑํ+์ข์์.gif)


## โ๏ธ ์๋ฒ ํ๊ฒฝ ์ธํ

### ๋ฐฑ์๋ ์๋ฒ(Spring-boot)

+ **Setting up the docker**
    ```bash
    > docker run --name maria-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD=<YOUR_DB_PASSWORD> -d mariadb
    > docker exec -it maria-db mysql -u root -p
    ```

+ **Create Maria-DB database**
  
    ```bash
    maria-db> create database ssafydb
    ```
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

### ํ๋ก ํธ์๋ ์๋ฒ(Vue.js)

```bash
cd frontend
npm install
npm run serve
```



## โ๏ธ ๋ผ์ด์ ์ค

### Spring Boot

Apache 2.0 license.

### Vue.js

MIT
Copyright (c) 2013-present, Yuxi (Evan) You

### MariaDB

GNU GPL 2 license.

### Docker5

Apache 2.0 license.