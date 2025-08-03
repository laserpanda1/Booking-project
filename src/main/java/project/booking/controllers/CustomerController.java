package project.booking.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.booking.data.Customer;
import project.booking.data.CustomerRepository;
import project.booking.dto.CustomerRequest;
import project.booking.exception.CustomerNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //Запрос через curl (curl -v localhost:8080/customers)
    @GetMapping("/customers")
    CollectionModel<EntityModel<Customer>> all() {

        List<EntityModel<Customer>> customers = customerRepository.findAll().stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerController.class).all()).withRel("customers")))
                .collect(Collectors.toList());

        return CollectionModel.of(customers,
                linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @GetMapping("/customers/{id}")
    EntityModel<Customer> one(@PathVariable Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).all()).withRel("customers"));
    }

    @PostMapping("/customers")
    ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody CustomerRequest request) {

        Customer customer = new Customer(
                request.getFirstName(),
                request.getLastName()
        );

        Customer newCustomer = customerRepository.save(customer);

        EntityModel<Customer> entityModel = EntityModel.of(newCustomer,
                linkTo(methodOn(CustomerController.class).one(newCustomer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).all()).withRel("customers"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/customers")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
