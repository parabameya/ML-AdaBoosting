/* author:Ameya Parab
 * UTD ID:2021166954
 * NET ID:axp132530
 * Course: Machine Learning
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RunBinaryAdaBoosting {

	private static  String INPUT_FILE = "input2/adaboost-2.dat";	
	static int noOfItertn;
	static int noOfExamp;
	static double epsilon;
	static List<Example> exList;
	static List<Classifier> hypothesis;

	public static void main(String[] args) throws IOException {
		readInputFile(INPUT_FILE);		
		generateAllHypothesis(exList);
		System.out.println("numberOfExamples = " + noOfExamp);
		System.out.println("numberOfIteration = " + noOfItertn);
		System.out.println("epsilon = " + epsilon);
		BinaryAdaBoosting adaBoost = new BinaryAdaBoosting(noOfItertn, noOfExamp, epsilon, exList, hypothesis);
		adaBoost.iterate(noOfItertn);	
	}
	
	private static List<Classifier> generateAllHypothesis(List<Example> exLst) throws IOException {
		hypothesis = new ArrayList<>();
		double firstDecsPnt = exLst.get(0).getAttribute()-1.0;
		hypothesis.add(new Classifier(true, firstDecsPnt, exLst));
		hypothesis.add(new Classifier(false, firstDecsPnt, exLst));
		for(int i = 0; i < exLst.size()-1; i++){	
			double decisionPoint = (exLst.get(i).getAttribute() + exLst.get(i+1).getAttribute()) / 2.0 ; 
			hypothesis.add(new Classifier(true, decisionPoint, exLst));
			hypothesis.add(new Classifier(false, decisionPoint, exLst));
		}
		double lastDecsPnt = exLst.get(exLst.size()-1).getAttribute()+1.0;
		hypothesis.add(new Classifier(true, lastDecsPnt, exLst));
		hypothesis.add(new Classifier(false, lastDecsPnt, exLst));
		return hypothesis;
	}
	
	private static void readInputFile(String inFile) throws IOException {
		Scanner scan = new Scanner(new File(inFile));		
		 noOfItertn = scan.nextInt();
		 noOfExamp = scan.nextInt();
		 epsilon = scan.nextDouble();
		 exList = new ArrayList<Example>(noOfExamp);
		for(int i = 0; i < noOfExamp; i++){
			Example example  = new Example();
			example.setAttribute(scan.nextDouble());
			exList.add(example);
		}
		for(int i = 0; i < noOfExamp; i++){
			exList.get(i).setTagetAttribute(scan.nextInt());
		}
		for(int i = 0; i < noOfExamp; i++){
			exList.get(i).setProbability(scan.nextDouble());
		}	
		scan.close();
	}
}