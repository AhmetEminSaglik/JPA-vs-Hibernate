package org.aes.compare.orm.business.concrete.orm;

import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public abstract class ORMImplementation {

    protected static EnumORMConfigFile enumORMConfigFile=EnumORMConfigFile.REAL_PRODUCT_JPA;

    protected static int initEntityCounter = 0;
    protected static int closeEntityCounter = 0;

    protected static void setConfigFile(EnumORMConfigFile configFile)/* throws InvalidORMConfigFileMatch */ {
//        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("Preferred CONFIG FILE : " + configFile.getFileName()));
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("Preferred ORM Tool is: " +getCurrentORMToolName()));
        enumORMConfigFile = configFile;
    }

    protected abstract void createFactory();

    protected abstract void createTransaction();

    protected abstract void initializeTransaction();

    protected abstract void commit();

    protected final void printInitializingInfo() {
        initEntityCounter++;
        String explanation = initEntityCounter + "-) Entity is Initializing: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(explanation));
    }

    protected final void printInitializedSuccessfully() {
        StringBuilder sb = new StringBuilder();
        sb.append(ColorfulTextDesign.getInfoColorTextWithPrefix(initEntityCounter + "-) Entity is "))
                .append(ColorfulTextDesign.getSuccessColorText("INITIALIZED SUCCESSFULLY"))
                .append(ColorfulTextDesign.getInfoColorText(": " + getClass().getSimpleName()));
        System.out.println(sb);
    }

    protected final void printClosingInfo() {
        closeEntityCounter++;
        String explanation = closeEntityCounter + "-) Entity is closing: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(explanation));
    }

    protected final void printClosedSuccessfully() {
        StringBuilder sb = new StringBuilder();
        sb.append(ColorfulTextDesign.getInfoColorTextWithPrefix(initEntityCounter + "-) Entity is "))
                .append(ColorfulTextDesign.getSuccessColorText("CLOSED SUCCESSFULLY"))
                .append(ColorfulTextDesign.getInfoColorText(": " + getClass().getSimpleName()));
        System.out.println(sb);
    }

    public static String getCurrentORMToolName() {
        return enumORMConfigFile.getORMToolName();
    }
}
