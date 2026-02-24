package de.infodesign.microservice.customer;

public class CustomerNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String id) {
        super("Customer not found: " + id);
    }
}
