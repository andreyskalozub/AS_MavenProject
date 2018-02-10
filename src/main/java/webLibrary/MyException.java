package webLibrary;

public class MyException extends Exception {
	
	 

	public MyException(String msg) {
	        message = msg;
	    }
	 
	    private String message;
	 
	    public String what() {
	       return message;
	    }

}
