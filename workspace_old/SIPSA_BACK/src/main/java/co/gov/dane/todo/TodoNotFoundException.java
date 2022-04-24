package co.gov.dane.todo;

/**
 * This exception is thrown when the requested todo entry is not found.
 * @author Petri Kainulainen
 */
public class TodoNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3872726497649348014L;

	public TodoNotFoundException(String id) {
        super(String.format("No todo entry found with id: <%s>", id));
    }
}
