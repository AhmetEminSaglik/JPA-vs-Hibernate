package org.aes.compare.orm.model.enums.course;

public enum EnumCourse {

    MATH("Math"), LITERATURE("Literature"), SCIENCE("Science"),
    JAVA("Java"), FLUTTER("Flutter"), REACT("ReactCourse");
    String name;
    EnumCourse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
