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

public class AddressFacade {
    private final AddressService addressService;

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

        FacadeUtility.destroyProcessSuccessfully();
        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);

        FacadeUtility.printSlash();
        return address;
    }

    public boolean isAnyAddressSaved() {
        int totalAddress = addressService.findAll().size();
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
        } else {
            FacadeUtility.destroyProcessSuccessfully();
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
//                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_FAILED);
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

        List<String> indexes = new ArrayList<>();
        indexes.add("Save new Address");
        indexes.add("Select from unmatched address (" + unmatchedAddress.size() + ")");


        int selected = FacadeUtility.getIndexValueOfMsgListIncludesExit("", indexes);
        Address address = null;

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
                    address = pickAddressFromList(unmatchedAddress);
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
    private Address pickAddressFromList(List<Address> addresses) {
//        LoggerProcessStack.add(MetaData.PROCESS_SELECT);
        int index = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        index--;
        if (index == -1) {
//            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess,2);
//            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess,1);
//            FacadeUtility.destroyProcessSuccessfully(1);
            return null;
        }
//        FacadeUtility.destroyProcessSuccessfully();
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
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        Address address = null;
        if (!isAnyAddressSaved()) {
            return address;
        }
        address = selectAddressToUpdate();
        if (address == null) {
            return address;
        }
//        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        return updateSelectedAddress(address);
    }

    public Address updateSelectedAddress(Address address) {
        while (true) {
//            System.out.print("Current address data : ");
            FacadeUtility.printInfoResult("Current Address:"+address);

            List<String> indexes = new ArrayList<>();
            indexes.add("Street");
            indexes.add("City");
            indexes.add("Country");

            int selected = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(MetaData.PROCESS_PREFIX_ADDRESS, indexes);

            switch (selected) {
                case -1:
                    FacadeUtility.destroyProcessCancelled();
                    return null;
                case 0:
                    addressService.update(address);
                    FacadeUtility.destroyProcessSuccessfully();
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
        int id = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        id--;
        if (id == -1) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
//        Address address = addresses.get(id);
//        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_STARTS);
//        FacadeUtility.destroyProcess(ColorfulTextDesign::getInfoColorTextWithPrefix, 1);
//        return address;
        return addresses.get(id);
    }

    public void delete() {
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyAddressSaved()) {
            return;
        }
        List<Address> addresses = addressService.findAllSavedAndNotMatchedAnyStudentAddress();

        System.out.println(ColorfulTextDesign.getWarningColorText("NOTE : Each Student must have one address.\nOnly deletable addresses (that are unmatched by any student) are listed below."));
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, addresses);
        result--;
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
