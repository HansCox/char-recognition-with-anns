package homework01;

public class TrainingInputTarget {
	int numInputs;
	int numOutputs;
	int [] inputVals;
	int [] targetVals;
	char letter;
	TrainingInputTarget(int numInputs, int numOutputs) {
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		inputVals = new int [numInputs];
		targetVals = new int [numOutputs];
	}
}
