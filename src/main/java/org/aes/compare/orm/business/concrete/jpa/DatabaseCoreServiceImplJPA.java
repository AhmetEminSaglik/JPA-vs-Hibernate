package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.Query;
import org.aes.compare.orm.business.abstracts.DatabaseCoreService;

public class DatabaseCoreServiceImplJPA extends JpaImplementation<Object> implements DatabaseCoreService {
    private final String databaseName = "demo_user_db";

    @Override
    public void reset() {
        initializeTransaction();


        getEntityManager().createNativeQuery("DROP DATABASE IF EXISTS " + databaseName)
                .executeUpdate();
        System.out.println("Database(" + databaseName + ") is DROPPED");

        getEntityManager().createNativeQuery("CREATE DATABASE " + databaseName)
                .executeUpdate();
        System.out.println("Database(" + databaseName + ") is CREATED");
    }
}
