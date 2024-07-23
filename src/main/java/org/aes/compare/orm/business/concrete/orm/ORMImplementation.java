package org.aes.compare.orm.business.concrete.orm;

import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public abstract class ORMImplementation {

    protected static EnumORMConfigFile enumORMConfigFile = EnumORMConfigFile.REAL_PRODUCT_JPA;

    protected static int initEntityCounter = 0;
    protected static int closeEntityCounter = 0;

    protected static void setConfigFile(EnumORMConfigFile configFile)/* throws InvalidORMConfigFileMatch */ {
//        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("Preferred CONFIG FILE : " + configFile.getFileName()));git
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(getCurrentORMToolName()+" ORM tool is activated."));
        enumORMConfigFile = configFile;
    }

    public static String getCurrentORMToolName() {
        return enumORMConfigFile.getORMToolName();
    }

    protected abstract void createFactory();

    protected abstract void createTransaction();

    protected abstract void initializeTransaction();

    protected abstract void commit();

    protected abstract void close();

    protected final void printInitializingInfo() {
        initEntityCounter++;
        String explanation = initEntityCounter + "-) Entity is Initializing: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(explanation));
    }

    protected final void printInitializedSuccessfully() {
        String sb = ColorfulTextDesign.getInfoColorTextWithPrefix(initEntityCounter + "-) Entity is ") +
                ColorfulTextDesign.getSuccessColorText("INITIALIZED SUCCESSFULLY") +
                ColorfulTextDesign.getInfoColorText(": " + getClass().getSimpleName());
        System.out.println(sb);
    }

    protected final void printClosingInfo() {
        closeEntityCounter++;
        String explanation = closeEntityCounter + "-) Entity is closing: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(explanation));
    }

    protected final void printClosedSuccessfully() {
        String sb = ColorfulTextDesign.getInfoColorTextWithPrefix(initEntityCounter + "-) Entity is ") +
                ColorfulTextDesign.getSuccessColorText("CLOSED SUCCESSFULLY") +
                ColorfulTextDesign.getInfoColorText(": " + getClass().getSimpleName());
        System.out.println(sb);
    }
}
