package org.ahmeteminsaglik.orm.exceptions;

import org.ahmeteminsaglik.orm.business.concrete.orm.ORMImplementation;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumORMConfigFile;

public class InvalidORMConfigFileMatch extends Exception {
    public InvalidORMConfigFileMatch(EnumORMConfigFile configFile, ORMImplementation clazz) {
        super("Requested ORM Config File " + configFile.name() + ". But " + clazz.getClass().getSimpleName() + " is activated for ORM.");
    }
}
