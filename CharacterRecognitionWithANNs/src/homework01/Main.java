package homework01;

public class Main {

	public static void main(String[] args) {

		String inputFilename = "inputTargetANSI.txt";
		InputDataParserForCharRecog parser = new InputDataParserForCharRecog(inputFilename);
		parser.parseInputTargetFile();
		parser.setTargets();
		
		Ann ann = new Ann(Defines.NUMBER_INPUTS, Defines.NUMBER_OUTPUTS);
		ann.train(parser.trainingSet);
//		ann.test(trainingSet);
		
	}

}
