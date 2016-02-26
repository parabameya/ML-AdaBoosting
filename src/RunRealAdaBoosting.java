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

public class RunRealAdaBoosting {
	private static  String INPUT_FILE = "input2/adaboost-1.dat";
	static int noOfIter;
	static int noOfEx;
	static double epsilon;
	static List<Example> exList;
	static List<Classifier> hyp;

	public static void main(String[] args) throws IOException {
		readInputFile(INPUT_FILE);
		generateAllHypothesis(exList);
		System.out.println("numberOfExamples = " + noOfEx);
		System.out.println("numberOfIteration = " + noOfIter);
		System.out.println("epsilon = " + epsilon);
		RealAdaBoosting adaBoost = new RealAdaBoosting(noOfIter, noOfEx, epsilon, exList, hyp);
		adaBoost.iterate(noOfIter);	
	}

	private static List<Classifier> generateAllHypothesis(List<Example> exampleList) throws IOException {
		hyp = new ArrayList<>();
		double firstDecisionPoint = exampleList.get(0).getAttribute()-1.0;
		hyp.add(new Classifier(true, firstDecisionPoint, exampleList));
		hyp.add(new Classifier(false, firstDecisionPoint, exampleList));
		for(int i = 0; i < exampleList.size()-1; i++){	
			double decisionPoint = (exampleList.get(i).getAttribute() + exampleList.get(i+1).getAttribute()) / 2.0 ; 
			hyp.add(new Classifier(true, decisionPoint, exampleList));
			hyp.add(new Classifier(false, decisionPoint, exampleList));
		}
		double lastDecisionPoint = exampleList.get(exampleList.size()-1).getAttribute()+1.0;
		hyp.add(new Classifier(true, lastDecisionPoint, exampleList));
		hyp.add(new Classifier(false, lastDecisionPoint, exampleList));
		return hyp;
	}

	private static void readInputFile(String inputFile) throws IOException {
		Scanner scan = new Scanner(new File(inputFile));		
		noOfIter = scan.nextInt();
		noOfEx = scan.nextInt();
		epsilon = scan.nextDouble();
		exList = new ArrayList<Example>(noOfEx);
		for(int i = 0; i < noOfEx; i++){
			Example example  = new Example();
			example.setAttribute(scan.nextDouble());
			exList.add(example);
		}
		for(int i = 0; i < noOfEx; i++){
			exList.get(i).setTagetAttribute(scan.nextInt());
		}
		for(int i = 0; i < noOfEx; i++){
			exList.get(i).setProbability(scan.nextDouble());
		}	
		scan.close();
	}
}