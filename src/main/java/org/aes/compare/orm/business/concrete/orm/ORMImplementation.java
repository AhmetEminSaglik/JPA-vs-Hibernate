package org.aes.compare.orm.business.concrete.orm;

import org.aes.compare.orm.model.EnumORMConfigFile;

public abstract class ORMImplementation<T> {
    //    protected static EnumORMConfigFile enumORMConfigFile = EnumORMConfigFile.REAL_PRODUCT_JPA;
    protected static EnumORMConfigFile enumORMConfigFile=EnumORMConfigFile.REAL_PRODUCT_JPA;

    protected static void setConfigFile(EnumORMConfigFile configFile)/* throws InvalidORMConfigFileMatch */ {
        System.out.println("GUNCELLENEN CONFIGF FILE : "+configFile.getName());
        enumORMConfigFile = configFile;
    }

    protected abstract void createFactory();

    protected abstract void createTransaction();

    protected abstract void initializeTransaction();


}
