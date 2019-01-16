package webLibrary;

public class MyException extends Exception {

	private String message;

	public MyException(String msg) {
		this.message = msg;
	}

	public String what() {
		return "That trouble occured:" + message;
	}

}
