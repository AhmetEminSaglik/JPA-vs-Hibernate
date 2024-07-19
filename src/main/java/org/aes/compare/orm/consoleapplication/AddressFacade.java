package org.aes.compare.orm.consoleapplication;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressFacade {
    Scanner scanner = new Scanner(System.in);
    private final AddressService addressService;

    public AddressFacade(AddressService addressService) {
        this.addressService = addressService;
    }

    private final static String selectOptionText = MetaData.SELECT_ONE_OPTION;

    public Address save() {
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("-) [ADDRESS] Save : "));
        Address address = new Address();

        String tmp;

        System.out.print("Type for Country: ");
        tmp = SafeScannerInput.getStringNotBlank();
        address.setCountry(tmp);

        System.out.print("Type for City: ");
        tmp = SafeScannerInput.getStringNotBlank();
        address.setCity(tmp);

        System.out.print("Type for Street: ");
        tmp = SafeScannerInput.getStringNotBlank();
        address.setStreet(tmp);


        addressService.save(address);
        System.out.println(ColorfulTextDesign.getSuccessColorText("Address is saved : ") + address);
        System.out.println("----------------------------------");
        return address;
    }

    public boolean isAnyAddressSaved() {
        int totalAddress = addressService.findAll().size();
        if (totalAddress == 0) {
            System.out.println(MetaData.NOT_FOUND_ANY_SAVED_ADDRESS);
            return false;
        }
        return true;
    }

    public Address findById() {
        if (!isAnyAddressSaved()) {
            return null;
        }
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("-) [ADDRESS] Find by id : "));
        System.out.print("Id no: ");
//            int id = scanner.nextInt();
        int id = SafeScannerInput.getCertainIntSafe();
        Address address = addressService.findById(id);
//        addressService.findById(id);
        if (address != null) {
            System.out.println(ColorfulTextDesign.getSuccessColorText("Address is Found: ") + address);
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Address is not found with the given id(" + id + "): "));
            return  findById();
        }
        System.out.println("----------------------------------");
        return  address;


    }

    public void findAll() {
        if (!isAnyAddressSaved()) {
            return;
        }
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("[ADDRESS] Find All : "));
        List<Address> addresses = addressService.findAll();
//            System.out.println("Addresses Found: " + addresses);

        if (addresses != null) {
            System.out.println(ColorfulTextDesign.getSuccessColorText("All Address data is retrieved:"));
            printArrWithNo(addresses);
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Has not found any address data."));
        }
        System.out.println("----------------------------------");

    }

    //    public void update() {
    public Address updateAddressProcess() {
        Address address = null;
        if (!isAnyAddressSaved()) {
            return address;
        }
        address = selectAddressToUpdate();
        if (address == null) {
            return address;
        }
        return  updateSelectedAddress(address);

    }

    public Address updateSelectedAddress(Address address) {
        while (true) {
            System.out.print("Current address data : ");
            System.out.println(address);

            List<String> indexes = new ArrayList<>();
            indexes.add("Street");
            indexes.add("City");
            indexes.add("Country");

            /*StringBuilder msg = FacadeUtility.createMsgFromListWithSaveAndCancelExit(indexes);
            msg.insert(0, MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_LIST);
            System.out.println(msg);

            selected = SafeScannerInput.getCertainIntForSwitch(selectOptionText, -1, indexes.size());*/
            int selected = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(MetaData.PROCESS_PREFIX_ADDRESS, indexes);
//            scanner.nextLine();
            switch (selected) {
                case -1:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address is update process is canceled : "));
                    return null;
                case 0:
                    addressService.update(address);
                    System.out.println(MetaData.ADDRESS_IS_UPDATED + address);
                    return address;
                case 1:
                    System.out.print("Type Street to update :");
                    address.setStreet(SafeScannerInput.getStringNotBlank());
                    break;
                case 2:
                    System.out.print("Type City to update :");
                    address.setCity(SafeScannerInput.getStringNotBlank());
                    break;
                case 3:
                    System.out.print("Type Country to update :");
                    address.setCountry(SafeScannerInput.getStringNotBlank());
                    break;
                default:
                    System.out.println("Invalid choice try again");
            }
        }
    }

    private Address selectAddressToUpdate() {
        List<Address> addresses = addressService.findAll();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("-) [ADDRESS] Update : "));

        int id = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        id--;
        if (id == -1) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address Update Process is Canceled"));
            return null;
        }
        Address address = addresses.get(id);
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("[ADDRESS]: Update process is starting : "));
        return address;
    }

    public void delete() {
        if (!isAnyAddressSaved()) {
            return;
        }
        List<Address> addresses = addressService.findAllSavedAndNotMatchedAnyStudentAddress();
        if (addresses.size() == 0) {
            System.out.println("Not found any data to process in Delete Process.\nExiting Delete Process...");
            return;
        }
        System.out.println("NOTE : Each Student has to have one address. Only address that unmatched can be deleted");
        /*StringBuilder sbMsg = new StringBuilder("Select Address no to delete.\n");
//        sbMsg.append(createMsgFromList(addresses));
        sbMsg.append(FacadeUtility.createMsgFromListExit(addresses));
        int result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 0, addresses.size() );*/
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        result--;
        if (result == -1) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address Delete process is Cancelled."));
        } else {
            Address addressToDelete = addresses.get(result);
            addressService.deleteById(addressToDelete.getId());
        }
        System.out.println("----------------------------------");

    }
    /*public void deleteById() {
        ;
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix( "-) [ADDRESS] Delete By Id : "));
        System.out.print("Id no: ");
        int id = scanner.nextInt();
        Address address = addressService.findById(id);
        addressService.findById(id);
        System.out.println("Address is Found: " + address);
        addressService.deleteById(id);
        System.out.println("address is deleted : ");
        System.out.println("----------------------------------");

    }*/

    public void resetTable() {
    }

    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
        }
    }

    private StringBuilder createMsgFromList(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1)).append("-) ").append(list.get(i)).append("\n");
        }
        sb.append((list.size() + 1)).append("-) Exit/Cancel");
        return sb;
    }

}
