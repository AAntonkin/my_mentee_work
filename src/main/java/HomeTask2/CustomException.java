package HomeTask2;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message, Object... args) {
        this(String.format(message, args));
    }

}
