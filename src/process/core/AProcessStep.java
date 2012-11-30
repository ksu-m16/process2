package process.core;

/**
 * Abstract class for process step - implements common methods used in every
 * process step implementation
 * 
 */
public abstract class AProcessStep implements IProcessStep {
	private IProcessData input;
	private IProcessData output;

	/**
	 * invoked by process runner - transfer input parameters to step
	 * 
	 * @param inputData
	 *            - container of key-value pairs according to process
	 *            configuration file
	 */
	public void setInput(IProcessData inputData) {
		input = inputData;
	}

	/**
	 * invoked by class extends ProcessStep - get input to that non-abstract
	 * class
	 * 
	 * @return container of key-value pairs according to process configuration
	 *         file
	 */
	protected IProcessData getInput() {
		return input;
	}

	/**
	 * invoked by process runner - transfer output parameters back to process
	 * 
	 * @return container of key-value pairs according to process configuration
	 *         file
	 */
	public IProcessData getOutput() {
		return output;
	}

	/**
	 * invoked by ProcessStep class - set output parameters before process
	 * runner will take it
	 * 
	 * @param key
	 *            , value - output key-value pair generated while step
	 *            processing
	 */
	protected void setOutput(String key, String value) {
		ProcessData outputData = new ProcessData();
		outputData.put(key, value);
		output = outputData;
	}

}