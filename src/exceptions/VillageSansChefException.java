package exceptions;

public class VillageSansChefException extends RuntimeException{
	
	public VillageSansChefException() {
		super();
	}
	
	public VillageSansChefException(String errorMessage) {
		super(errorMessage);
	}
	
	public VillageSansChefException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public VillageSansChefException(Throwable cause) {
        super(cause);
    }
}