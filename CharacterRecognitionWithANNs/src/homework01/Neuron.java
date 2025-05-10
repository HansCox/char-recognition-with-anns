package homework01;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {

	int numInputs;
	double thresholdTheta;
	double inputSum;
	ArrayList<InputNode> inputNodes;
	ArrayList<Double> weights;
	int activation;
	TrainingInputTarget trainingInputTarget;
	
	// Think!
	public Neuron() {
		numInputs = 0;
		inputNodes = new ArrayList<InputNode>(1);
		weights = new ArrayList<Double>(1);
		thresholdTheta = 1;
		inputSum = 0.0;
	}
	
	// Think with an object in mind
	public Neuron(int numInputs, double threshold) {
		this.numInputs = numInputs;
		inputNodes = new ArrayList<InputNode>(numInputs+1);
		weights = new ArrayList<Double>(numInputs + 1);
		this.thresholdTheta = threshold;
		inputSum = 0.0;
	}
	
	public double getActivationBinaryHard() {
		
		if(inputSum > thresholdTheta) {
			activation = 1;
		} else if (inputSum < -thresholdTheta) {
			activation = -1;
		} else { activation = 0; }
		return activation;
	}
	
	public void adjustWtsHebb() {
		
	}

	// Sums the weighted inputs to the neuron and returns the sum;
	// Also, sets internal value of inputSum field
	public double computeSum() {
		inputSum = 0;
		int i = 0;
		if(Defines.DEBUG) {
			System.out.println("In computeSum there are " + inputNodes.size() + " input nodes");
		}
		for(InputNode inputNode : inputNodes) {
			inputSum = inputSum + inputNode.inputVal * weights.get(i).doubleValue();
			i++;
		}
		if(Defines.DEBUG) {
			System.out.println("Input sum: " + inputSum);
		}
		return inputSum;
	}
	
	// Gets the threshold value of the neuron
	public double getThreshold() {
		return thresholdTheta;
	}
	
	// Initializes weights with some small, random values
	public void initWts() {
		Random rand = new Random();
		double newWt;
		double posNeg;
		if(Defines.DEBUG) {
			System.out.println("Weights for neuron");
		}
		int i = 0;
		if(Defines.DEBUG) {
			System.out.println("There are " + inputNodes.size() + " input nodes");
		}
		for(InputNode inNode : inputNodes) {
			newWt = rand.nextDouble() * 2.0;
			posNeg = rand.nextDouble();
			if(posNeg<0.5) {
				posNeg = -1;
			} else {
				posNeg = 1;
			}
			weights.add(new Double(posNeg*newWt));
			if(Defines.DEBUG) {
				if(posNeg*newWt > 0 && posNeg*newWt < 1) {System.out.print(" "); }
				System.out.print(weights.get(i).doubleValue() + " ");
			}
			i++;
		}
		if(Defines.DEBUG) {
			System.out.println("");
		}
	}
}
