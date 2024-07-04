package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.model.Address;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressTestJPA {
    AddressService addressService = new AddressServiceImplJPA();

    @BeforeEach
    public void resetTables() {
        addressService.resetTable();
    }

    @Test
    @Order(1)
    public void testSaveAddress() {
        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        addressService.save(address);
        addressService.save(address2);

    }

    @Test
    @Order(2)
    public void testFindAddress() {
        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        addressService.save(address);
        addressService.save(address2);

        Address retrievedAddress = addressService.findById(2);
        Assertions.assertEquals(address2, retrievedAddress);
    }

    @Test
    @Order(3)
    public void testUpdateAddress() {
        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        System.out.println("addres 1 saved");
        addressService.save(address);
        System.out.println("addres 2 saved");
        addressService.save(address2);

        System.out.println("address datalari savelendi");
        Address retrievedAddress = addressService.findById(2);
        System.out.println("retrievedAddress : "+retrievedAddress);
        retrievedAddress.setCity("Updated City");
        System.out.println("retrievedAddress : "+retrievedAddress);
        addressService.update(retrievedAddress);
        System.out.println("data savelendi");


        Assertions.assertEquals(address2.getId(), retrievedAddress.getId());
        Assertions.assertNotEquals(address2, retrievedAddress);
    }

    @Test
    @Order(4)
    public  void testDeleteAddress(){

        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        Address address3 = new Address("Teknokent", "Izmir", "Japanase");

        System.out.println("addres 1 saved");
        addressService.save(address);
        System.out.println("addres 2 saved");
        addressService.save(address2);

        System.out.println("addres 3 saved");
        addressService.save(address3);

        System.out.println("address datalari savelendi");
        Address retrievedAddress = addressService.findById(2);

        System.out.println("retrievedAddress : "+retrievedAddress);
        retrievedAddress.setCity("Updated City");

        System.out.println("retrievedAddress : "+retrievedAddress);
        addressService.update(retrievedAddress);


        Assertions.assertEquals(address2.getId(), retrievedAddress.getId());
        Assertions.assertNotEquals(address2, retrievedAddress);

        addressService.deleteById(2);

    }



}
