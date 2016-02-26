/* author:Ameya Parab
 * UTD ID:2021166954
 * NET ID:axp132530
 * Course: Machine Learning
 */

import java.util.ArrayList;
import java.util.List;

public class Classifier {
	boolean isLwrPos;
	double decPnt; 
	List<Example> classdEx;
	double epsilon,alpha;
	double p_plus_right, p_minus_right, p_plus_wrong, p_minus_wrong, G, c_plus, c_minus;

	public Classifier(boolean isLowerPositive, double decisionPoint,
			List<Example> originalList) {
		this.isLwrPos = isLowerPositive;
		this.decPnt = decisionPoint;
		this.classdEx = classifyAllExamples(originalList);
	}

	public List<Example> classifyAllExamples(List<Example> origList) {
		classdEx = new ArrayList<>(origList.size());
		for(int i = 0; i < origList.size(); i++){
			Example ex = origList.get(i).clone();
			classify(ex);
			classdEx.add(ex);
		}
		return classdEx;
	}

	private void classify(Example exmpl) {
		exmpl.setTagetAttribute(getTargetAttribute(exmpl));
	}

	private int getTargetAttribute(Example exmpl) {
		if(exmpl.getAttribute() < decPnt){
			return isLwrPos ? 1 : -1;
		}	
		return isLwrPos ? -1 : 1;
	}

	public double computeAlpha() {	
		alpha = 0.5 * Math.log( (1 - epsilon) / epsilon);
		return alpha;
	}

	public void computeEpsilon(List<Example> exmplSet){
		epsilon = 0;
		for(int i = 0; i < exmplSet.size(); i++){
			if(classdEx.get(i).getTagetAttribute() != exmplSet.get(i).getTagetAttribute()){
				epsilon += exmplSet.get(i).getProbability();
			}
		}
	}

	public double computeG( ){
		G = Math.sqrt(p_plus_right * p_minus_wrong) + Math.sqrt(p_plus_wrong * p_minus_right);
		return G;
	}

	public void computeC(double  epsilon) {
		c_plus = 0.5 * Math.log((p_plus_right + epsilon) / (p_minus_wrong + epsilon) );
		c_minus = 0.5 * Math.log((p_plus_wrong + epsilon) / (p_minus_right + epsilon) );
	}

	public void computeP(List<Example>  exmplSet) {
		p_plus_right = p_plus_wrong = p_minus_right = p_minus_wrong = 0;
		for(int i = 0; i < exmplSet.size(); i++){	
			if(exmplSet.get(i).getTagetAttribute() == 1){
				if(classdEx.get(i).getTagetAttribute()==1){
					p_plus_right += exmplSet.get(i).getProbability();
				} else {
					p_plus_wrong += exmplSet.get(i).getProbability();
				}
			} else {
				if(classdEx.get(i).getTagetAttribute()==-1){
					p_minus_right += exmplSet.get(i).getProbability();
				} else {
					p_minus_wrong += exmplSet.get(i).getProbability();
				}
			}
		}
	}

	@Override
	public String toString() {
		if(isLwrPos){
			return "I(x < " + decPnt+")";
		}
		return "I(x > " + decPnt+")";
	}

	public Classifier clone() {
		Classifier clond  =  new Classifier(isLwrPos, decPnt,classdEx);
		clond.epsilon = epsilon;
		clond.alpha = alpha;
		clond.p_plus_right = p_plus_right; 
		clond.p_plus_wrong = p_plus_wrong;
		clond.p_minus_right = p_minus_right; 
		clond.p_minus_wrong = p_minus_wrong;
		clond.G = G;
		clond.c_plus = c_plus;
		clond.c_minus = c_minus;
		return clond;
	}
}