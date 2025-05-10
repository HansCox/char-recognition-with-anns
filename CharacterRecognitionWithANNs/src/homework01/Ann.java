package homework01;

import java.util.ArrayList;
public class Ann {

	private int numInputs;
	private int numOutputs;
	private InputNode bias;
	private final double BIAS_VAL = 1.0;
	private final double THRESHOLD = 10.0;
	public ArrayList<InputNode> inputNodes;
	public ArrayList<Neuron> outputNodes;
	private double[] outputVals;
	private TrainingSet trainingSet;

	public Ann(int numInputs, int numOutputs) {
		this.numInputs = numInputs; //bias?
		this.numOutputs = numOutputs;
		inputNodes = new ArrayList<InputNode>(numInputs+1);
		outputNodes = new ArrayList<Neuron>(numOutputs);
		outputVals = new double [Defines.NUMBER_OUTPUTS];
		bias = new BiasNode(BIAS_VAL);
		init();
	}
	// Adjusts the weights of the inputs if necessary
	private void adjustWts(Neuron neuron, int targetValue) {
			int j=0;
			for(InputNode inNode : inputNodes) {
				neuron.weights.set(j, new Double(neuron.weights.get(j).doubleValue() +
						inNode.inputVal*neuron.activation));
				j++;
			}
	}
	// Initializes input and output nodes and connects network.
	private void init() {
		inputNodes.add(bias);
		for(int i=1;i<numInputs+1;i++) {
			inputNodes.add(new InputNode());
		}
		for(int i=0;i<numOutputs;i++) {
			Neuron outputNode = new Neuron(numInputs, THRESHOLD);
			outputNode.inputNodes.addAll(inputNodes);
			outputNode.initWts();
			outputNodes.add(outputNode);
		}
//		setAllToAllConnected();
	}
	// Prints, to standard output, a report on the network's ability to categorize inputs.
	public void printTestingReport() {
		System.out.println("Testing Report:...");
	}
	// Builds the network, connecting every input to every neuron/output unit
	public void setAllToAllConnected() {
		int i=0;
		for(Neuron outputNode : outputNodes) {
			outputNode.inputNodes.addAll(inputNodes);
			i++;
		}
		
	}
	// Runs a training epoch
	public void train(TrainingSet trainingSet) {
		this.trainingSet = trainingSet;
		if(Defines.DEBUG) {
			System.out.println("Training...");
		}
		for(int z=0;z<trainingSet.trainingSet.size();z++) {
			TrainingInputTarget currentInputTarget = trainingSet.trainingSet.get(z);
			// Set inputs
			for(int i=1;i<numInputs+1;i++) {
				inputNodes.get(i).setInputVal(currentInputTarget.inputVals[i-1]);
			}
			// Compute input sums
			for(Neuron outputNode : outputNodes) {
				outputNode.computeSum();
			}
			// Compute outputs
			int i=0;
			for(Neuron output : outputNodes) {
				outputVals[i] = output.getActivationBinaryHard();
				if(Defines.DEBUG) {
					System.out.println("Letter is: " + currentInputTarget.letter);
				}
				if(Defines.DEBUG) {
					System.out.println("Activation: output " + i + " = " + outputVals[i]);
				}
				if(output.activation != currentInputTarget.targetVals[i]) {
					if(Defines.DEBUG) { System.out.println("Adjusting weights..."); }
					adjustWts(output, currentInputTarget.targetVals[i]);
				}
				
				i++;
			}
			// Adjust weights
			for(Neuron output : outputNodes) {
				output.adjustWtsHebb();
			}
		}
	}
	
	// Presents inputs to neuron and records differences between targets and outputs.
	// Generates a report on the network's ability to categorize inputs.
	public void test(TrainingSet testSet) {
		// TODO Auto-generated method stub
		if(Defines.DEBUG) {
			System.out.println("Testing...");
		}
	}
	
	
}
