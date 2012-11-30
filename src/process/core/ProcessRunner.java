package process.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
* Class implements functionality of process running: configuration reading,
* instantiation of process step classes, writing result to file
*
*/
public class ProcessRunner {
	ProcessData processData;
	ProcessData processPropierties;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[1]; 
		args[0] = ".//process.properties";
		
//		%JAVA_HOME%/bin/java –Dinput.file=process.properties –jar process.jar
		
		ProcessRunner pr = new ProcessRunner();
		try {
			pr.loadProcessPropierties(args[0]);
			pr.loadInputProperties();
			
			ArrayList<StepData> steps = pr.parseStepsConfig();
			
			System.out.println("!");
			
			while (steps.size() > 0) {
				pr.performStep(steps.remove(0));
			}
			
			pr.writeResultsToFile(pr.processData);
			
			System.out.println("result1: " + pr.processData.getValue("result1")
					+ "; result2: " + pr.processData.getValue("result2"));
			
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void loadProcessPropierties(String propFilePath) throws IOException {
		Properties propierties = new Properties();
		processPropierties = new ProcessData();
		InputStream is = new FileInputStream(propFilePath);
		propierties.load(is);
		is.close();
		Set<String> propNames = propierties.stringPropertyNames();
		for (String s: propNames) {
			processPropierties.put(s,propierties.getProperty(s));
		}
	}
	
	private void loadInputProperties() throws IOException {
		processData = new ProcessData();
		Properties inputPropierties = new Properties();
		InputStream is = new FileInputStream(".//" + processPropierties.getValue("process.input"));
		inputPropierties.load(is);
		is.close();
		
		Set<String> propNames = inputPropierties.stringPropertyNames();
		
		for (String s: propNames) {
			processData.put(s,inputPropierties.getProperty(s));
		}
	}
	
	private ArrayList<StepData> parseStepsConfig() throws ClassNotFoundException {
		ArrayList<StepData> stepsData = new ArrayList<StepData>();
		int stepNum = 1;
		while (processPropierties.has("step" + stepNum + ".class")) {
			StepData data = new StepData();
			
			Class<?> c = Class.forName(processPropierties.getValue("step" + stepNum + ".class"));
			Class<? extends AProcessStep> ce = null;
			data.stepClass = c.asSubclass(ce);
			
//			data.setStepClass((Class<AProcessStep>) Class.forName(processPropierties.getValue("step" + stepNum + ".class")));
			ProcessData stepInput = parseIOData(processPropierties.getValue("step" + stepNum + ".input"));
			data.inputDescription = stepInput;
			data.outputDescription = parseIOData(processPropierties.getValue("step" + stepNum + ".output"));
			stepsData.add(data);			
			stepNum++;
		}
		return stepsData;
	}
	
	private ProcessData parseIOData(String s) {
		ProcessData data = new ProcessData();
		for (String pair: s.split(",")){
			data.put(pair.substring(0, pair.indexOf(':')), 
					pair.substring(pair.indexOf(':') + 1));
		}
		return data;
	}
	
	private void performStep(StepData stepData) throws Exception {
		
		AProcessStep step = (AProcessStep) stepData.stepClass.newInstance();
		
		ProcessData inputData = new ProcessData();
		for (Map.Entry<String, String> e : stepData.inputDescription.entrySet()) {
			inputData.put(e.getValue(), processData.getValue(e.getKey()));
		}
		step.setInput(inputData);
		
		step.perform();
		
		ProcessData outputData = (ProcessData)step.getOutput();
		for (Map.Entry<String, String> e : stepData.outputDescription.entrySet()) {
			processData.put(e.getValue(), outputData.getValue(e.getKey()));
		}
	}
	
	private void writeResultsToFile(ProcessData data) throws FileNotFoundException {
		PrintWriter out = null;
		try {
			out = new PrintWriter(processPropierties.getValue("process.output"));
			out.println("result1: " + data.getValue("result1"));
			out.println("result2: " + data.getValue("result2"));
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	* Class contains information about every step: class, input, output
	* 
	*/
	private class StepData {
		private Class<? extends IProcessStep> stepClass;
		private ProcessData inputDescription;
		private ProcessData outputDescription;
		
		/**
		 * converts the object to a string.
		 */
		@Override
		public String toString(){
			return "Class: " + stepClass.toString() + "; input: " + inputDescription.toString()
					+ "; output: " + outputDescription.toString();	
		}
	}
	
}
