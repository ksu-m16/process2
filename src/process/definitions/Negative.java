package process.definitions;

import process.core.AProcessStep;
import process.core.IProcessData;

/**
 * Class performs negation
 * 
 */
public class Negative extends AProcessStep {

	/**
	 * performs negation
	 * 
	 * @throws Exception
	 */
	@Override
	public void perform() throws Exception {
		IProcessData input = getInput();
		double x1 = Double.parseDouble(input.getValue("x1"));
		setOutput("y", x1 * (-1) + "");
	}

}
