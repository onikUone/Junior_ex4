package main;

import static main.FuzzySetTriangle.*;

public class GroupController {
	//member field
	int Npop;
	int attribute;	//扱うデータセットの属性数
	double pDontCare;
	Individual[] individual;
	Fuzzy f;
	MersenneTwisterFast mtf;

	//member function
	public void initialGenerate() {
		int[] setRule = new int[attribute];
		for(int i=0; i<Npop; i++) {
			for(int j=0; j<attribute; j++) {
				setRule[j] = (int)(mtf.nextDoubleIE()*ruleNumber) + 1;
			}
			for(int j=0; j<attribute; j++) {
				if(mtf.nextDoubleIE() < pDontCare) {
					setRule[j] = 0;
				}
			}
			individual[i] = new Individual(setRule, f);
		}

	}

	//constractor
	GroupController(int Npop, Fuzzy f, int seed){
		this.mtf = new MersenneTwisterFast(seed);
		this.Npop = Npop;
		this.individual = new Individual[this.Npop];
		this.attribute = f.attribute;
		this.pDontCare = (double)(this.attribute - ruleNumber) / (double)f.attribute;
		this.f = f;
	}
}
