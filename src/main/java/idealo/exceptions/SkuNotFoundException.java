package idealo.exceptions;

public class SkuNotFoundException extends RuntimeException {

    public SkuNotFoundException(String message) {
        super(message);
    }
}
