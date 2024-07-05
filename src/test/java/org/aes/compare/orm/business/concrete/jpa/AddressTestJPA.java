package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.model.Address;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressTestJPA {
    static AddressService addressService = new AddressServiceImplJPA();

    @BeforeAll
    public static void resetAddressTableBeforeAll() {
        addressService.resetTable();
    }

   /* @AfterAll
    public static void resetAddressTableAfterAll() {
        addressService.resetTable();
        ResetAllTables.resetAll();
    }*/

    @Test
    @Order(1)
    public void testSaveAddress() {
        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        addressService.save(address);
        addressService.save(address2);

        Address retrievedAddress = addressService.findById(2);
        Assertions.assertEquals(address2, retrievedAddress);
    }

    @Test
    @Order(2)
    public void testUpdateAddress() {
        Address address = addressService.findById(1);
        address.setCity("Updated City");
        addressService.update(address);

        Address retrievedAddress = addressService.findById(1);
        Assertions.assertEquals(address, retrievedAddress);
    }

    @Test
    @Order(3)
    public  void testDeleteAddress(){
        addressService.deleteById(1);
        int expected = 1;
        int actual = addressService.findAll().size();
        Assertions.assertEquals(expected, actual);
    }
}
