package org.aes.compare.orm.consoleapplication;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.InnerTerminalProcessLayout;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerProcessStack;

import java.util.ArrayList;
import java.util.List;

public class AddressFacade extends TerminalCommandLayout {
    private final AddressService addressService;

    public AddressFacade(AddressService addressService) {
        this.addressService = addressService;
    }

    public Address save() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);
        Address address = new Address();
        String input;

        String title = "Type for Country: ";
        input = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        address.setCountry(input);

        title = "Type for City: ";
        input = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        address.setCity(input);


        title = "Type for Street: ";
        input = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        address.setStreet(input);

        addressService.save(address);
        FacadeUtility.destroyProcessSuccessfully();
        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
        FacadeUtility.printSlash();
        return address;
    }

    public boolean isAnyAddressSaved() {
        return isAnyAddressSaved(addressService.findAll());
    }

    public boolean isAnyAddressSaved(List<Address> addresses) {
        int totalAddress = addresses.size();
        if (totalAddress == 0) {
            FacadeUtility.destroyProcessCancelled();
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
        if (!isAnyAddressSaved()) {
            return null;
        }
        Address address = pickAddressFromSwitchCase();

        if (address == null) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        } else {
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
        }
        return address;
    }

    private Address pickAddressFromSwitchCase() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        Address address = null;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Address from List");
        indexes.add("Pick Address by typing Address id");

        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return null;
        }
        switch (option) {
            case 0:
                break;
            case 1:
                address = pickAddressFromList(interlayout, addressService.findAll());
                break;
            case 2:
                String title = "Type Address id (int): ";
                int id = FacadeUtility.getSafeIntInputFromTerminalProcess(interlayout, title);
                if (FacadeUtility.isCancelledProcess(interlayout)) {
                    return null;
                }

                address = addressService.findById(id);
                if (address == null) {
                    FacadeUtility.destroyProcessFailed(1);
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
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        List<String> indexes = new ArrayList<>();
        indexes.add("Save new Address");
        indexes.add("Select from unmatched address (" + unmatchedAddress.size() + ")");

        int selected = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        Address address = null;
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return null;
        }
        switch (selected) {
            case 0:
                FacadeUtility.destroyProcessCancelled(1);
                return null;
            case 1:
                address = save();
                break;
            case 2:
                if (unmatchedAddress.isEmpty()) {
                    FacadeUtility.destroyProcessCancelled(1);
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX)
                            + ColorfulTextDesign.getWarningColorText("Not found any saved unmatched address. Please save address first."));
                } else {
                    address = pickAddressFromList(interlayout, unmatchedAddress);
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
        if (address == null) {
            return pickAddressForStudentSaveProcess();
        }
        return address;
    }

    private Address pickAddressFromList(TerminalCommandLayout interlayout, List<Address> addresses) {

        int index = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, addresses);

        System.out.println("index : " + index);
        if (FacadeUtility.isCancelledProcess(interlayout)
                || index == 0) {
            return null;
        }
        index--;
        return addresses.get(index);
    }

    public void findAll() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyAddressSaved()) {
            return;
        }
        List<Address> addresses = addressService.findAll();

        if (addresses != null) {
            FacadeUtility.destroyProcessSuccessfully(1);
            FacadeUtility.printArrResult(addresses);
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Has not found any address data."));
        }
        LoggerProcessStack.pop();
        FacadeUtility.printSlash();

    }

    public Address updateAddressProcess() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        Address address = null;
        if (!isAnyAddressSaved()) {
            return address;
        }
        address = selectAddressToUpdate(interlayout);
        if (address == null) {
            return address;
        }
        return updateSelectedAddress(address);
    }

    public Address updateSelectedAddress(Address address) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        while (interlayout.isAllowedCurrentProcess()) {
            FacadeUtility.printInfoResult("Current Address:" + address);
            List<String> indexes = new ArrayList<>();
            indexes.add("Street");
            indexes.add("City");
            indexes.add("Country");

            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistAndCancelFromTerminalProcess(interlayout, indexes);
            if (FacadeUtility.isCancelledProcess(interlayout)) {
                FacadeUtility.destroyProcessCancelled();
                return null;
            }
            String title, input = "";
            switch (option) {
                case -1:
                    FacadeUtility.destroyProcessCancelled();
                    return null;
                case 0:
                    addressService.update(address);
                    FacadeUtility.destroyProcessSuccessfully();
                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
                    return address;
                case 1:
                    title = "Type Street to update: ";
                    input = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    address.setStreet(input);

                    break;
                case 2:
//                    System.out.print("Type City to update: ");
                    title = "Type City to update: ";
                    input = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    address.setCity(input);
                    break;
                case 3:
//                    System.out.print("Type Country to update: ");
                    title = "Type Country to update: ";
                    input = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    address.setCountry(input);
                    break;
                default:
                    System.out.println("Invalid choice try again");
            }
        }
        return null;
    }

    private Address selectAddressToUpdate(TerminalCommandLayout interlayout) {
        List<Address> addresses = addressService.findAll();
        int id = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, addresses);

        if (FacadeUtility.isCancelledProcess(interlayout) || id == 0) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        --id;
        return addresses.get(id);
    }

    public void delete() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyAddressSaved(addressService.findAllSavedAndNotMatchedAnyStudentAddress())) {
            return;
        }
        List<Address> addresses = addressService.findAllSavedAndNotMatchedAnyStudentAddress();

        System.out.println(ColorfulTextDesign.getWarningColorText("NOTE : Each Student must have one address.\nOnly deletable addresses (that are unmatched by any student) are listed below."));

        int result = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, addresses);
        if (FacadeUtility.isCancelledProcess(interlayout)
                || --result == 1) {
            FacadeUtility.destroyProcessCancelled();
            return;
        }

        if (result == -1) {
            FacadeUtility.destroyProcessCancelled(1);
        } else {
            Address addressToDelete = addresses.get(result);
            addressService.deleteById(addressToDelete.getId());
            FacadeUtility.destroyProcessSuccessfully(1);
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "Address(id=" + addressToDelete.getId() + ") is deleted.");
        }
        LoggerProcessStack.pop();
        FacadeUtility.printSlash();
    }
}
