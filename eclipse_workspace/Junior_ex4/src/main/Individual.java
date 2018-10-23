package main;

public class Individual {
	//field
	int myClass;
	double weight;
	double trust;
	int[] rule; //rule[n] nは入力パターンの属性数

	//memberFunction

	//自身の結論クラス部決定メソッド
	public void decideMyClass(double[][] x, int[] y, int classes) {
		double max = 0;
		double comp = 0;
		for (int i = 0; i < classes; i++) {
			if (i == 0) {
				max = Fuzzy.calcTrust(rule, x, y, i);
				myClass = i;
			}
			else {
				comp = Fuzzy.calcTrust(rule, x, y, i);
				if (comp > max) {
					max = comp;
					myClass = i;
				}
			}
		}
	}

	//自身のルール重み計算メソッド
	public void calcMyWeight(double[][] x, int[] y, int classes) {
		trust = Fuzzy.calcTrust(rule, x, y, myClass);
		weight = trust;
		for (int i = 0; i < classes; i++) {
			if (i != myClass) {
				weight -= Fuzzy.calcTrust(rule, x, y, i);
			}
		}
	}

	//constractor
	Individual(int[] rule, double[][] x, int[] y, int classes) {
		this.rule = rule;
		decideMyClass(x, y, classes);
		calcMyWeight(x, y, classes);
	}
}
