package org.aes.compare.orm.consoleapplication;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerProcessStack;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressFacade {
    private final static String selectOptionText = MetaData.SELECT_ONE_OPTION;
    private final AddressService addressService;
    Scanner scanner = new Scanner(System.in);

    public AddressFacade(AddressService addressService) {
        this.addressService = addressService;
    }

    public Address save() {
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);

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
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);

        FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 2);
        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);

        FacadeUtility.printSlash();
        return address;
    }

    public boolean isAnyAddressSaved() {
        int totalAddress = addressService.findAll().size();
        if (totalAddress == 0) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_READ + MetaData.PROCESS_IS_CANCELLED));
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.NOT_FOUND_ANY_SAVED_ADDRESS));

            return false;
        }
        return true;
    }

    public Address findById() {
        return findByMultipleWay();
    }

    public Address findByMultipleWay() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
//        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_READ + MetaData.PROCESS_STARTS));
        if (!isAnyAddressSaved()) {
            return null;
        }
        Address address = pickAddressFromSwitchCase();

        if (address == null) {
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
        } else {
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 2);
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
        }
        return address;
    }

    private Address pickAddressFromSwitchCase() {
        Address address = null;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Address from List");
        indexes.add("Pick Address by typing Address id");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_ADDRESS, indexes);
        switch (option) {
            case 0:
                break;
            case 1:
                address = pickAddressFromList(addressService.findAll());
                break;
            case 2:
                System.out.print("Type Address id (int): ");
                int id = SafeScannerInput.getCertainIntSafe();
                address = addressService.findById(id);
                if (address == null) {
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_FAILED);
                    FacadeUtility.destroyProcess(ColorfulTextDesign::getErrorColorText, 1);
                    System.out.println(ColorfulTextDesign.getWarningColorText("-> Address is not found with given id(" + id + "). Please try again."));
                    return pickAddressFromSwitchCase();
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return pickAddressFromSwitchCase();
        }
        return address;
    }


    public Address pickAddressForStudentSaveProcess() {
        List<Address> unmatchedAddress = addressService.findAllSavedAndNotMatchedAnyStudentAddress();

//        String processPrefixName = parentProcessName  + MetaData.INNER_PROCESS_PREFIX + MetaData.PROCESS_PREFIX_ADDRESS;
        List<String> indexes = new ArrayList<>();
        indexes.add("Save new Address");
        indexes.add("Select from unmatched address (" + unmatchedAddress.size() + ")");


        int selected = FacadeUtility.getIndexValueOfMsgListIncludesExit("", indexes);
        Address address = null;

        switch (selected) {
            case 0:
                LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
                FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess,1);
//                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(processPrefixName + MetaData.PROCESS_SELECT + MetaData.PROCESS_IS_CANCELLED));
                return null;
            case 1:
//                LoggerProcessStack.add(MetaData.PROCESS_SAVE+MetaData.PROCESS_STARTS);
//                FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess,2);
                address = save();
//                System.out.println(ColorfulTextDesign.getSuccessColorText(processPrefixName + MetaData.PROCESS_SELECT + MetaData.PROCESS_COMPLETED));
//                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
                break;
            case 2:
                if (unmatchedAddress.isEmpty()) {
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
                    FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 1);

//                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(processPrefixName + MetaData.PROCESS_SELECT + MetaData.PROCESS_IS_CANCELLED));
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX)
                            + ColorfulTextDesign.getWarningColorText("Not found any saved unmatched address. Please save address first."));

                } else {
                    address = pickAddressFromList(unmatchedAddress);
//                    System.out.println(ColorfulTextDesign.getSuccessColorText(processPrefixName + MetaData.PROCESS_SELECT + MetaData.PROCESS_COMPLETED));
//                    System.out.println(ColorfulTextDesign.getSuccessColorText(processPrefixName + MetaData.PROCESS_SELECT + MetaData.PROCESS_COMPLETED));
                    if (address != null) {
//                        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
//                    FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 1);
                    } else {
//                        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
//                        FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 1);
                    }
//                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
//        return address;
        if (address == null) {
            return pickAddressForStudentSaveProcess();
        }
        return address;
    }
    public Address pickAddressFromList(List<Address> addresses) {
        LoggerProcessStack.add(MetaData.PROCESS_SELECT);

//        StringBuilder sb = createMsgFromList(addresses);

//        System.out.print(sb);
//        int index = SafeScannerInput.getCertainIntSafe(0, addresses.size());
        int index = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        index--;
        if (index == -1) {
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess,2);
            return null;
        }
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
        FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText,2);
        return addresses.get(index);
    }
    public void findAll() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyAddressSaved()) {
//            LoggerProcessStack.pop();
            return;
        }
//        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("[ADDRESS] Find All : "));
        List<Address> addresses = addressService.findAll();
//            System.out.println("Addresses Found: " + addresses);

        if (addresses != null) {
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 1);
            printArrResult(addresses);
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Has not found any address data."));
        }
        LoggerProcessStack.pop();
        System.out.println("----------------------------------");

    }

    //    public void update() {
    public Address updateAddressProcess() {
//        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_UPDATE+ MetaData.PROCESS_STARTS));
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        Address address = null;
        if (!isAnyAddressSaved()) {
            return address;
        }
        address = selectAddressToUpdate();
        if (address == null) {
//            LoggerProcessStack.add(MetaData.PROCESS_IS_CANCELLED);
//            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
            return address;
        }
        return updateSelectedAddress(address);

//        return  address;

    }

    public Address updateSelectedAddress(Address address) {
        while (true) {
            System.out.print("Current address data : ");
            System.out.println(address);

            List<String> indexes = new ArrayList<>();
            indexes.add("Street");
            indexes.add("City");
            indexes.add("Country");

            int selected = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(MetaData.PROCESS_PREFIX_ADDRESS, indexes);

            switch (selected) {
                case -1:
//                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_UPDATE + MetaData.PROCESS_IS_CANCELLED));
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
                    FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
                    return null;
                case 0:
                    addressService.update(address);
//                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.ADDRESS_IS_UPDATED) + address);
//                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_PREFIX_ADDRESS+MetaData.PROCESS_UPDATE+MetaData.PROCESS_COMPLETED));
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
                    FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 2);
                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX)+ address);
                    return address;
                case 1:
                    System.out.print("Type Street to update: ");
                    address.setStreet(SafeScannerInput.getStringNotBlank());
                    break;
                case 2:
                    System.out.print("Type City to update: ");
                    address.setCity(SafeScannerInput.getStringNotBlank());
                    break;
                case 3:
                    System.out.print("Type Country to update: ");
                    address.setCountry(SafeScannerInput.getStringNotBlank());
                    break;
                default:
                    System.out.println("Invalid choice try again");
            }
        }
    }

    private Address selectAddressToUpdate() {
        List<Address> addresses = addressService.findAll();
//        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("-) [ADDRESS] Update : "));

        int id = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        id--;
        if (id == -1) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_UPDATE+ MetaData.PROCESS_IS_CANCELLED));
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address Update Process is Canceled"));
            return null;
        }
        Address address = addresses.get(id);
//        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("[ADDRESS]: Update process is starting : "));
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_STARTS);
        FacadeUtility.destroyProcess(ColorfulTextDesign::getInfoColorTextWithPrefix, 1);
        return address;
    }

    public void delete() {
//        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_DELETE + MetaData.PROCESS_STARTS));
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyAddressSaved()) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_DELETE+ MetaData.PROCESS_IS_CANCELLED));
            return;
        }
        List<Address> addresses = addressService.findAllSavedAndNotMatchedAnyStudentAddress();
        if (addresses.size() == 0) {
            System.out.println("Not found any data to process in Delete Process.\nExiting Delete Process...");
            return;
        }
        System.out.println(ColorfulTextDesign.getWarningColorText("NOTE : Each Student must have one address.\nOnly deletable addresses that are unmatched by any student are listed below."));
        /*StringBuilder sbMsg = new StringBuilder("Select Address no to delete.\n");
//        sbMsg.append(createMsgFromList(addresses));
        sbMsg.append(FacadeUtility.createMsgFromListExit(addresses));
        int result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 0, addresses.size() );*/
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        result--;
        if (result == -1) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address Delete process is Cancelled."));
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_ADDRESS+MetaData.PROCESS_DELETE+MetaData.PROCESS_IS_CANCELLED));
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 1);
        } else {
            Address addressToDelete = addresses.get(result);
            addressService.deleteById(addressToDelete.getId());
//            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_PREFIX_ADDRESS+MetaData.PROCESS_DELETE+MetaData.PROCESS_COMPLETED));
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 1);
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "Address(id=" + addressToDelete.getId() + ") is deleted.");
        }
        LoggerProcessStack.pop();
        System.out.println("----------------------------------");

    }
    public void resetTable() {
    }

    private  void printArrResult(List<?>list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) +(i + 1) + "-) " + list.get(i));
        }
    }
}
