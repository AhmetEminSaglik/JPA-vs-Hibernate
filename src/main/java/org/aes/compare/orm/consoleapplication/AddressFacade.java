package org.aes.compare.orm.consoleapplication;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.List;
import java.util.Scanner;

public class AddressFacade {
    Scanner scanner = new Scanner(System.in);
    private final AddressService addressService;
    private static int counter = 0;

    public AddressFacade(AddressService addressService) {
        this.addressService = addressService;
        System.out.println("gelen  addressService " + addressService);
    }

    public Address save() {
        counter++;
        System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [ADDRESS] Save : "));
        Address address = new Address();
        address.setCity(counter + ". City");
        address.setStreet(counter + ". Street");
        address.setCountry(counter + ". Counter");


        addressService.save(address);
        System.out.println("Address is saved : " + address);
        System.out.println("----------------------------------");
        return address;
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
