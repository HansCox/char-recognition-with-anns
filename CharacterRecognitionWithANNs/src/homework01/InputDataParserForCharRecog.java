package homework01;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputDataParserForCharRecog {

	String inputFilename;
	String inputFilenameJava;
//	String filePathStr = "c:\\Users\\Hans\\workspace_CS_5870\\Homework01\\" + inputFilename;
//	String filePathStrJava = filePathStr.replace("\\", "/");
//	String filePathStr = /*"..\\" + */inputFilename;
	TrainingSet trainingSet;
	
	
	public InputDataParserForCharRecog() {
		inputFilename = "inputTargetUnicode.txt";
		trainingSet = new TrainingSet();
	}
	public InputDataParserForCharRecog(String inputFilename) {

		this.inputFilename = inputFilename;
		this.inputFilenameJava = inputFilename.replace("\\", "/");
		trainingSet = new TrainingSet();
	}
	
	// Reads the file into a List of StringS,
	// extracts input-target pairs,
	// Converts pairs to inputs for input nodes
	// and targets to compare with output of output layer.
	public void parseInputTargetFile() {
		// Read file.
		List<String> linesList = null;
		try {
			linesList = Files.readAllLines(Paths.get(inputFilenameJava),Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Extract each character image (9 lines) into its place.
		
		ArrayList<String> linesArray = new ArrayList<String>(linesList.size());
			for(String line : linesList) {
				linesArray.add(line);
				if(Defines.DEBUG) {
					System.out.println(line);
				}
		}
		ArrayList<ArrayList<String>> images = 
				new ArrayList<ArrayList<String>>(linesArray.size()/Defines.IMAGE_HEIGHT_IN_LINES);
		int i=0;
		int j=0;
		for(String line : linesArray) {
			if(i % Defines.IMAGE_HEIGHT_IN_LINES == 0) {
				images.add(new ArrayList<String>());
			}
			images.get(j).add(line);
			i++;
			if(i % Defines.IMAGE_HEIGHT_IN_LINES == 0) {
				j++;
			}
		}
		// Set input values from image, put in TrainingSet's TrainingInputOutputS
		for(ArrayList<String> image : images) {
			int m=0;
			TrainingInputTarget trainingImage = 
					new TrainingInputTarget(Defines.IMAGE_HEIGHT_IN_LINES*Defines.IMAGE_WIDTH_IN_CHARS,
							Defines.NUMBER_OUTPUTS);
			for(String line : image) {
				for(int p=0;p<line.length();p++) {
					int inputValue;
					if(line.charAt(p) == Defines.NEGATIVE_CHAR) {
						inputValue = -1;
					} else if(line.charAt(p) == Defines.POSITIVE_CHAR) {
						inputValue = 1;
					} else { inputValue = 0; }
					trainingImage.inputVals[p+m*Defines.IMAGE_WIDTH_IN_CHARS] = inputValue;
				}// char in line
				m++;
			}// line in image
			trainingSet.trainingSet.add(trainingImage);
			
			if(Defines.DEBUG) {
				for(int input: trainingImage.inputVals) {
				System.out.print((input < 0) ? input: " " + input);
				}
			}
			System.out.println("");
		}//image
		
	}
	public TrainingSet getTrainingSet() {
		return trainingSet;
	}

	public void setTargets() {
		String targetRun = "ABCDEJK"; // This pattern repeats three times, in the data file.
		int i = 0;
		for(TrainingInputTarget image : trainingSet.trainingSet) {
			image.targetVals[(i<Defines.NUMBER_OUTPUTS)?i:i % Defines.NUMBER_OUTPUTS] = 1;
			image.letter = targetRun.charAt((i<Defines.NUMBER_OUTPUTS)?i:i % Defines.NUMBER_OUTPUTS);
			if(Defines.DEBUG) {
				System.out.println("Inputs set:");
				for(int j=0;j<image.targetVals.length;j++) {
				System.out.print(image.targetVals[j]);
				}
				System.out.println("");
			}
			i++;
		}
		
		
	}
}
