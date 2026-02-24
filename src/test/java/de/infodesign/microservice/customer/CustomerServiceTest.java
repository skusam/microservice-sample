package de.infodesign.microservice.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerServiceTest {

    CustomerService customerService = new CustomerService();

    @Test
    void getCustomerWhereCustomerNotExists() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(""));
    }

    @Test
    void getCustomerWhereCustomerExists() {
        String id = customerService.createCustomer("Tester", "tester@test.com").getId();

        Customer customer = customerService.getCustomer(id);
        assertNotNull(customer);
        assertEquals("Tester", customer.getName());
        assertEquals("tester@test.com",  customer.getEmail());
    }

}