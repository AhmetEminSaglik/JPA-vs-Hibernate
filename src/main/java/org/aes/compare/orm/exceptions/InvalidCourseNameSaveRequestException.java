package org.ahmeteminsaglik.orm.exceptions;

import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;

public class InvalidCourseNameSaveRequestException extends Exception {
    public InvalidCourseNameSaveRequestException(String courseName) {
        super(
                ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.COURSE_NAME_MUST_BE_UNIQUE)
                        + ColorfulTextDesign.getInfoColorText('[' + courseName + ']')
                        + ColorfulTextDesign.getErrorColorText(MetaData.IS_SAVED_ALREADY + " Please try with another name again.")
        );
    }
}
