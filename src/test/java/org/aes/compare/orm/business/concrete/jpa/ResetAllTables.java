package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;

public class ResetAllTables {
    private static StudentService studentService = new StudentServiceImpJPA();
    private static CourseService courseService = new CourseServiceImplJPA();
    private static AddressService addressService = new AddressServiceImplJPA();
    private static ExamResultService examResultService = new ExamResultServiceImplJPA();

    public static void resetAll() {/*
        System.out.println("RESET Table examResultService");
        examResultService.resetTable();
        System.out.println("RESET Table studentService");
        studentService.resetTable();
        System.out.println("RESET Table addressService");
        addressService.resetTable();
        System.out.println("RESET Table courseService");
        courseService.resetTable();
    */}
}
