package org.aes.compare.orm;

import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;

public class Main {
    static ORMConfigSingleton orm = new ORMConfigSingleton();
    public static void main(String[] args) {
//        UIConsoleApp consoleApp = new UIConsoleApp();
//        consoleApp.start();
        ORMConfigSingleton.enableJPA();

        Student student = new Student();
        student.setName("Ahmet");
        student.setGrade(3);

        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        address.setCountry("Country");

        orm.getAddressService().save(address);

        student.setAddress(address);

//        student.addCourse(javaCourse);
        orm.getStudentService().save(student);

//        ORMConfigSingleton.enableJPA();
        Course javaCourse = new JavaCourse();
        javaCourse.addStudent(student);
        orm.getCourseService().save(javaCourse);


    /*


        Thread t1= new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                System.out.println("1");
                System.out.println("1");
                System.out.println("1");
                int val=scanner.nextInt();
                System.out.println("Thread 1 : Input : "+val);
            }
        });

        Thread t2= new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
                System.out.println("2");
                System.out.println("2");
                System.out.println("2");
                System.out.println("2");
                int val=scanner.nextInt();
                System.out.println("Thread 2 : Input : "+val);
            }
        });

        t1.start();
        t2.start();
    */
    }
}