package org.aes.compare.orm.model.enums.configfile;

public enum EnumHibernateConfigFile {
    REAL_PRODUCT("REAL_PRODUCT_HIBERNATE"), JUNIT_TEST("JUNIT_TEST_HIBERNATE");

    final String name;

    EnumHibernateConfigFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
