### How to run
- Install XAMPP or MySQL DB
- Update application.properties file to appropirate db details, below properties need to change according to DB 
spring.datasource.url=jdbc:mysql://localhost:3306/storetrade?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
- Run storetrade.sql inside the DB to create schema and tables
- Install maven
- Run command - mvn clean install
- Run command - mvn clean package
- Run command - mvn spring-boot:run
- Access it using the below endpoints

### Endpoint

- To get trade details - Method GET - http://localhost:8080/trade/{tradeId}
- To save trade details - Method POST - http://localhost:8080/trade
- To update trade details - Method PUT - http://localhost:8080/trade
- To delete trade details - Method DELETE - http://localhost:8080/trade
