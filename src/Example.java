/* author:Ameya Parab
 * UTD ID:2021166954
 * NET ID:axp132530
 * Course: Machine Learning
 */

public class Example {
	private double attr;
	private int tagetAttr;
	private double probab;

	public Example() {
		this.attr = 0;
		this.tagetAttr = 0;
		this.probab = 0;
	}

	public Example(double attr, int tagetAttr, double probab) {
		this.attr = attr;
		this.tagetAttr = tagetAttr;
		this.probab = probab;
	}

	public double getAttribute() {
		return attr;
	}

	public void setAttribute(double attr) {
		this.attr = attr;
	}

	public int getTagetAttribute() {
		return tagetAttr;
	}

	public void setTagetAttribute(int targetAttr) {
		this.tagetAttr = targetAttr;
	}

	public double getProbability() {
		return probab;
	}

	public void setProbability(double probab) {
		this.probab = probab;
	}

	public Example clone() {
		return new Example(attr,tagetAttr,probab);
	}

	@Override
	public String toString() {
		return "x=" + attr + ", y="
				+ tagetAttr + ", p=" + probab ;
	}
}