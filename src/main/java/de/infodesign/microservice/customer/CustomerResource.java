package de.infodesign.microservice.customer;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/customers") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

	@Inject
    private CustomerService service;

    public CustomerResource(CustomerService service) {
        this.service = service;
    }

    @POST
    public Customer create(CreateCustomerRequest req) {
        return service.createCustomer(req.name(), req.email());
    }

    @GET
    @Path("/{id}")
    public Customer get(@PathParam("id") String id) {
        return service.getCustomer(id);
    }
}


