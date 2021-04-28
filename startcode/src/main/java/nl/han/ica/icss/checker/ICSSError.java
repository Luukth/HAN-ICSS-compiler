package nl.han.ica.icss.checker;

public class ICSSError {
	private final String description;
	private ErrorType errorType;

	public ICSSError(String description) {
		this.description = description;
		this.errorType = ErrorType.ERROR;
	}

	public String toString() {
		return errorType.toString() + ": " + description;
	}

    public ICSSError setErrorType(ErrorType errorType) {
        this.errorType = errorType;
        return this;
    }
}

