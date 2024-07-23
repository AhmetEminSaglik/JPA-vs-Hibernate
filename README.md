<!-- run mysql : 
```docker
docker run -d --name mysql-root -p 3307:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=1 mysql:latest
``` -->

# Hibernate-vs-Jpa
* Hibernate implementation is added. `Save` and `find` process are added as an example using.
  * Keywords :
    * hibernate.cfg.xml
    * SessionFactory
* JPA implementation is added. `Save` and `find` process are added as an example using.
  * Keywords :
    * META-INF\persistence.xml
    * EntityManagerFactory
