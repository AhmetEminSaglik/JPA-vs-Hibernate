package org.aes.compare.orm.business.concrete.comparator;

import org.aes.compare.orm.model.Address;

import java.util.Comparator;

public class AddressComparator implements Comparator<Address> {

    @Override
    public int compare(Address a1, Address a2) {
        return Integer.compare(a1.getId(), a2.getId());
    }

}
