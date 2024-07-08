package org.aes.compare.orm.business.concrete.orm;

import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public abstract class ORMImplementation {

    protected static EnumORMConfigFile enumORMConfigFile=EnumORMConfigFile.REAL_PRODUCT_JPA;

    protected static int initEntityCounter = 0;
    protected static int closeEntityCounter = 0;

    protected static void setConfigFile(EnumORMConfigFile configFile)/* throws InvalidORMConfigFileMatch */ {
        System.out.println(ColorfulTextDesign.getInfoColorText("Preferred CONFIG FILE : "+configFile.getName()));
        enumORMConfigFile = configFile;
    }

    protected abstract void createFactory();

    protected abstract void createTransaction();

    protected abstract void initializeTransaction();

    protected abstract void commit();


}
