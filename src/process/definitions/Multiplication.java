package process.definitions;

import process.core.AProcessStep;
import process.core.IProcessData;

/**
 * Class performs multiplication
 * 
 */
public class Multiplication extends AProcessStep {

	/**
	 * performs multiplication
	 * 
	 * @throws Exception
	 */
	@Override
	public void perform() throws Exception {
		IProcessData input = getInput();
		double x1 = Double.parseDouble(input.getValue("x1"));
		double x2 = Double.parseDouble(input.getValue("x2"));
		setOutput("y", x1 * x2 + "");
	}

}
