package fr.epf.restaurant.exception;

public class StockInsuffisantException extends RuntimeException {

    public StockInsuffisantException() {
        super();
    }

    public StockInsuffisantException(String message) {
        super(message);
    }

    public StockInsuffisantException(String message, Throwable cause) {
        super(message, cause);
    }
}