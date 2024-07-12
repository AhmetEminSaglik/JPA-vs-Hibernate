package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ORMConfigSingleton orm = new ORMConfigSingleton();
    public static void main(String[] args) {
        ORMConfigSingleton.enableJPA();
        SubClassAddress classAddress = new SubClassAddress(orm.getAddressService());
        int option = -1;
        while (option != 6) {

            System.out.println("1-) Save");
            System.out.println("2-) Find By Id");
            System.out.println("3-) Find All");
            System.out.println("4-) Update");
            System.out.println("5-) Delete");
            System.out.println("6-) Exit");

            System.out.println("select one option");
            option=SafeScannerInput.getIntRecursive();
//            scanner.nextLine();

            switch (option) {
                case 1:
                    classAddress.save();
                    break;
                case 2:
                    classAddress.findById();
                    break;
                case 3:
                    classAddress.findAll();
                    break;
                case 4:
                    classAddress.update();
                    break;
                case 5:
                    classAddress.deleteById();
                    break;
                case 6:
                    System.out.println("exitting the address Service");
                    break;
                default:
                    System.out.println("Invalid Choose try again");
            }
        }
        /*classAddress.save();

        System.out.println("Press enter  : ");
        scanner.nextLine();

        classAddress.save();

        System.out.println("Press enter  : ");
        scanner.nextLine();

        classAddress.findAll();

        System.out.println("Press enter  : ");
        scanner.nextLine();
*/
       /* classAddress.findById();

        System.out.println("Press enter  : ");
        scanner.nextLine();

        classAddress.update();

        System.out.println("Press enter  : ");
        scanner.nextLine();

        classAddress.deleteById();

        System.out.println("Press enter  : ");
        scanner.nextLine();*/


        /*
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

        Course javaCourse = new JavaCourse();
        javaCourse.addStudent(student);
        orm.getCourseService().save(javaCourse);*/
    }


    static class SubClassAddress {
        private final AddressService addressService;
        private static int counter = 0;

        public SubClassAddress(AddressService addressService) {
            this.addressService = addressService;
        }

        public void save() {
            counter++;
            System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [ADDRESS] Save : "));
            Address address = new Address();
            address.setCity(counter + ". City");
            address.setStreet(counter + ". Street");
            address.setCountry(counter + ". Counter");


            addressService.save(address);
            System.out.println("Address is saved : " + address);
            System.out.println("----------------------------------");
        }

        public void findById() {
            counter++;
            System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [ADDRESS] Find by id : "));
            System.out.print("Id no: ");
//            int id = scanner.nextInt();
            int id = SafeScannerInput.getIntRecursive();
            Address address = addressService.findById(id);
            addressService.findById(id);
            System.out.println("Address is Found: " + address);
            System.out.println("----------------------------------");

        }

        public void findAll() {
            counter++;
            System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [ADDRESS] Find ALL : "));
            List<Address> addresses = addressService.findAll();
//            System.out.println("Addresses Found: " + addresses);
            addresses.forEach(e -> {
                System.out.println(e);
            });
            System.out.println("----------------------------------");

        }

        public void update() {
            counter++;
            System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [ADDRESS] Update : "));
            System.out.print("Address Id no: ");
//            int id = scanner.nextInt();
            int id = SafeScannerInput.getIntRecursive();
            Address address = addressService.findById(id);
            addressService.findById(id);
            System.out.println("Address is Found: " + address);
            System.out.println("Update process is starting : ");


            int selected = 0;
            while (selected != 4) {
                System.out.println("1-) City");
                System.out.println("2-) Street");
                System.out.println("3-) Country");
                System.out.println("4-) Update and Exit");

                System.out.println("Current address data : ");
                System.out.println(address);
                selected = scanner.nextInt();
                scanner.nextLine();
                switch (selected) {
                    case 1:
                        System.out.print("Type City to update :");
                        address.setCity(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Type Street to update :");
                        address.setStreet(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Type Country to update :");
                        address.setCountry(scanner.nextLine());
                        break;
                    case 4:
                        addressService.update(address);
                        System.out.println("Address is updated : " + address);
                        break;
                    default:
                        System.out.println("Invalid choice try again");
                }
            }
            System.out.println("----------------------------------");
        }

        public void deleteById() {
            counter++;
            System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [ADDRESS] Delete By Id : "));
            System.out.print("Id no: ");
            int id = scanner.nextInt();
            Address address = addressService.findById(id);
            addressService.findById(id);
            System.out.println("Address is Found: " + address);
            addressService.deleteById(id);
            System.out.println("address is deleted : ");
            System.out.println("----------------------------------");

        }

        public void resetTable() {
        }
    }
}