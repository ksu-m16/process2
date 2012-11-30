package process.definitions;

import process.core.AProcessStep;
import process.core.IProcessData;

/**
* Class performs square-rooting 
*
*/
public class SquareRoot extends AProcessStep{

	/**
	* performs square-rooting 
	*
	* @throws Exception
	*/
	@Override
	public void perform() throws Exception {
		
		IProcessData input = getInput();
		double x1 = Double.parseDouble(input.getValue("x1"));
		setOutput("y", Math.pow(x1, 0.5) + "");
		
	}

}
