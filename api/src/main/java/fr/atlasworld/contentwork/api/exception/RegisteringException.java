package fr.atlasworld.contentwork.api.exception;

/**
 * Exception is thrown when something went wrong in the registration process
 */
public class RegisteringException extends IllegalStateException {
    public RegisteringException(String message) {
        super(message);
    }
}
