package project.booking.Services;

import org.springframework.stereotype.Service;
import project.booking.data.Customer;
import project.booking.data.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService (CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> checkAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer addCustomer (Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return this.customerRepository.findById(id);
      }


}
