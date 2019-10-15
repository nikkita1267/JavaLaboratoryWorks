package code.exceptions;

public class BackpackOverflowException extends RuntimeException {
    public BackpackOverflowException() {
        super("You can't pack more than volume");
    }
}
