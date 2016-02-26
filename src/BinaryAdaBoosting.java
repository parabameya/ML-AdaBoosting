/* author:Ameya Parab
 * UTD ID:2021166954
 * NET ID:axp132530
 * Course: Machine Learning
 */


import java.util.LinkedList;
import java.util.List;

public class BinaryAdaBoosting {
	int noOfEx;
	double epsilon;
	List<Example> exSet;
	List<Classifier> classSet;
	List<Classifier> selClass;
	
	public BinaryAdaBoosting(int numberOfIteration, int noOfEx,
			double eps, List<Example> exampleList, List<Classifier> hypothesis) {
		this.noOfEx = noOfEx;
		this.epsilon = eps;
		this.exSet = exampleList;
		this.classSet = hypothesis;
		this.selClass = new LinkedList<>();
	}
	
	public void iterate(int numberOfIteration){
		double boundOnNormalFact = 1;
		
		for(int i = 1; i <= numberOfIteration; i++){
			Classifier bestClass = selectBestClassifier();
			bestClass.computeAlpha();
			double normalFac = updateProbab(bestClass);
			boundOnNormalFact *= normalFac;
			//classifierSet.remove(bestClassifier);
			selClass.add(bestClass.clone());			
			System.out.println("\n\n--------------------------------------------------");
			System.out.println("Iterartion " + i);
			System.out.println("--------------------------------------------------");
			System.out.println("The selected weak classifier: " + bestClass);
			System.out.println("The error of ht: " + bestClass.epsilon);
			System.out.println("The weight of ht: " + bestClass.alpha);
			System.out.println("The probabilities normalization factor: " + normalFac);
			System.out.print("The probabilities after normalization: ");
			for(int j = 0; j < exSet.size(); j++){
				System.out.print(exSet.get(j).getProbability() + ", ");
			}
			System.out.print("\nThe boosted classifier: ");
			for(int k = 0; k < selClass.size(); k++){
				System.out.print(selClass.get(k).alpha +"*" + selClass.get(k) + " + ");
			}
			System.out.println("\nThe error of the boosted classifier: " + (getErrCountOfBoostClass()/(double)exSet.size()));
			System.out.println("The bound on Et : " + boundOnNormalFact);
		}
	}

	public Classifier selectBestClassifier(){
		Classifier bestClassfr = null;
		double minEpsilon = 1.0;
		for(int i = 0 ; i < classSet.size(); i++){
			Classifier classfr = classSet.get(i);
			classfr.computeEpsilon(exSet);
			if(classfr.epsilon < minEpsilon ){
				minEpsilon = classfr.epsilon;
				bestClassfr = classfr;
			}
		}
		return bestClassfr;
	}
	
	private double updateProbab(Classifier classifier) {
		double normalFact = 0, q;
		for(int i = 0; i < exSet.size(); i++){	
			if(exSet.get(i).getTagetAttribute() == classifier.classdEx.get(i).getTagetAttribute()){
				q = Math.pow(Math.E, -1 * classifier.alpha);
			} else {
				q = Math.pow(Math.E, classifier.alpha);
			}
			exSet.get(i).setProbability(q*exSet.get(i).getProbability());
			normalFact += exSet.get(i).getProbability();
		}	
		for(int i = 0; i < exSet.size(); i++){
			exSet.get(i).setProbability(exSet.get(i).getProbability() / normalFact);
		}
		return normalFact;
	}
	
	private int getErrCountOfBoostClass() {
	int errorCount = 0;
		for(int i = 0; i < exSet.size(); i++){
			double targetAttr = 0;
			for(Classifier classifier : selClass){
				targetAttr += classifier.alpha * classifier.classdEx.get(i).getTagetAttribute();	
			}
			
			if((exSet.get(i).getTagetAttribute()==-1 && targetAttr >= 0.0) ||
			   (exSet.get(i).getTagetAttribute()== 1 && targetAttr < 0.0)	) {
				errorCount++;
			}
		}	
		return errorCount;
	}
}