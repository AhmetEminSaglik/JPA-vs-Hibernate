package org.aes.compare.orm.model;

public enum EnumPersistanceType {
    REAL_PRODUCT("persistenceUnitRealProject"), JUNIT_TEST("persistenceUnitTest");

    final String name;

    EnumPersistanceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
