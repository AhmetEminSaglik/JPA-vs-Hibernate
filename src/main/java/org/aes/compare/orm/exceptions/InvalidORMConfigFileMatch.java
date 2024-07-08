package org.aes.compare.orm.exceptions;

import org.aes.compare.orm.business.concrete.orm.ORMImplementation;
import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;

public class InvalidORMConfigFileMatch extends Exception{
    public InvalidORMConfigFileMatch(EnumORMConfigFile configFile, ORMImplementation clazz) {
        super("Requested ORM Config File " +configFile.name()+". But "+clazz.getClass().getSimpleName()+" is activated for ORM.");
    }
}
