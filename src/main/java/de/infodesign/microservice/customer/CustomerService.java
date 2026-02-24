package de.infodesign.microservice.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CustomerService {

    private static final Logger LOG = Logger.getLogger(CustomerService.class);

    private final Map<String,Customer> customers = new HashMap<>();

    public Customer createCustomer(String name, String email) {
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, name, email);

        customers.put(id, customer);

        LOG.info("Created customer " + customer);

        return customer;
    }

    public Customer getCustomer(String id) {
        if (customers.containsKey(id)) {
            LOG.info("Found customer with id " + id);
        	return customers.get(id);
        } else {
            LOG.info("Customer not found with id " + id);
        	throw new CustomerNotFoundException(id);
        }
    }
}
