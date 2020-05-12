package io.chetan.exception;

//laoding from another micro serivce Pgservic so it is checked exception
//so subclass of Exception
public class CouldNotLoadYourPGException extends Exception {

	public CouldNotLoadYourPGException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouldNotLoadYourPGException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public CouldNotLoadYourPGException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CouldNotLoadYourPGException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CouldNotLoadYourPGException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
	
}
