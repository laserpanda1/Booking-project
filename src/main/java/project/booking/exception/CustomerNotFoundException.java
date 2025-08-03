package project.booking.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Not found customer id : " + id);
    }
}
