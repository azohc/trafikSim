package control;

@SuppressWarnings("serial")
public class SimulatorError extends RuntimeException {

	public SimulatorError() {
		super("Unknown simulator error!");
	}
	public SimulatorError(String message){
		super(message);
	}
	public SimulatorError(String message, Throwable cause){
		super(message, cause);
	}
	public SimulatorError(Throwable cause){
		super(cause);
	}
	
}
