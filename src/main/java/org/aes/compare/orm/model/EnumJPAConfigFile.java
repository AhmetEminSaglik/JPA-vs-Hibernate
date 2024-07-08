package org.aes.compare.orm.model;

public enum EnumJPAConfigFile {
    REAL_PRODUCT("REAL_PRODUCT_JPA"), JUNIT_TEST("JUNIT_TEST_JPA");

    final String name;

    EnumJPAConfigFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
