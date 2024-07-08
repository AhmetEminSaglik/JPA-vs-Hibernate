package org.aes.compare.orm.model;

public enum EnumORMConfigFile {
    REAL_PRODUCT_HIBERNATE("hibernate.cfg.xml"), JUNIT_TEST_HIBERNATE("hibernate_test.cfg.xml"),
    REAL_PRODUCT_JPA("persistenceUnitRealProject"), JUNIT_TEST_JPA("persistenceUnitTest");;

    final String name;

    EnumORMConfigFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
