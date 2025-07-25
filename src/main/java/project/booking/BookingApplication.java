package project.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import project.booking.data.Customer;
import project.booking.data.CustomerRepository;

@SpringBootApplication
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(BookingApplication.class);

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository) {
		return (args -> {
				customerRepository.save(new Customer("Jack", "Robee"));
				customerRepository.save(new Customer("Nico", "Redro"));
				customerRepository.save(new Customer("Rose", "Fib"));

				log.info("Customer found with findAll()");
				log.info("-------------------------------");
				customerRepository.findAll().forEach(customer -> {
					log.info(customer.toString());
				});

				log.info("");

				log.info("Customer find with findByLastName");
				log.info("----------------------------------");
				customerRepository.findByLastName("Redro").forEach(n -> {
					log.info(n.toString());
				});
		});
	}
}
