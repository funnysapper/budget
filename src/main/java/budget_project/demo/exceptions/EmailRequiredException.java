package budget_project.demo.exceptions;

public class EmailRequiredException extends RuntimeException {
    public EmailRequiredException(String message) {
        super(message);
    }
}
