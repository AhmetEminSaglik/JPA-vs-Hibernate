package org.aes.compare.orm.model.enums.configfile;

public enum EnumORMConfigFile {
    REAL_PRODUCT_HIBERNATE("Hibernate", "hibernate.cfg.xml"), JUNIT_TEST_HIBERNATE("Hibernate", "hibernate_test.cfg.xml"),
    REAL_PRODUCT_JPA("JPA", "persistenceUnitRealProject"), JUNIT_TEST_JPA("JPA", "persistenceUnitTest");

    final String ORMToolName;
    final String fileName;

    EnumORMConfigFile(String ORMToolName, String fileName) {
        this.ORMToolName = ORMToolName;
        this.fileName = fileName;
    }

    public String getORMToolName() {
        return ORMToolName;
    }

    public String getFileName() {
        return fileName;
    }
}
