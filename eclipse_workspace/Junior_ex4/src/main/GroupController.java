package main;

import static main.FuzzySetTriangle.*;

public class GroupController {
	//member field
	int Npop = 3;
	int attribute; //扱うデータセットの属性数
	double pDontCare;
	Individual[] individual;
	Fuzzy f;
	MersenneTwisterFast mtf;

	//member function

	//test個体群
	public void testGenerate() {
		int setRule1[] = {0,0,0,2,0,0,0,0};
		int setRule2[] = {0,4,0,0,3,0,0,4};
		int setRule3[] = {0,1,0,3,1,2,0,0};
		individual[0] = new Individual(setRule1,f);
		individual[1] = new Individual(setRule2,f);
		individual[2] = new Individual(setRule3,f);

	}


	//初期個体群の生成
	public void initialGenerate() {
		int[] setRule = new int[attribute];
		for (int i = 0; i < Npop; i++) {
			for (int j = 0; j < attribute; j++) {
				setRule[j] = (int) (mtf.nextDoubleIE() * ruleNumber) + 1;
			}
			for (int j = 0; j < attribute; j++) {
				if (mtf.nextDoubleIE() < pDontCare) {
					setRule[j] = 0;
				}
			}
			individual[i] = new Individual(setRule, f);
		}

	}



	//個体の評価
	public void evaluation() {
		int useRuleIndex = -1;
		double max = 0;
		double comp = 0;
		recog: for (int p = 0; p < f.x.length; p++) {
			for (int i = 0; i < Npop; i++) {
				if (i == 0) {
					max = Fuzzy.calcFit(individual[i].rule, f.x[p]) * individual[i].weight;
					useRuleIndex = i;
				} else {
					if (individual[i].trust <= 0.5) {
						continue recog;
					}
					comp = Fuzzy.calcFit(individual[i].rule, f.x[p]) * individual[i].weight;
					if(comp == max) {//同じかつ結論部が違う処理追加
						continue recog;
					}
					if (comp > max) {
						max = comp;
						useRuleIndex = i;
					}
				}
			}
			if(individual[useRuleIndex].myClass == f.y[p]) {
				individual[useRuleIndex].fitness++;
			}
		}

	}

	//constractor
	GroupController(int Npop, Fuzzy f, MersenneTwisterFast mtf) {
		this.mtf = mtf;
//		this.Npop = Npop;
		this.individual = new Individual[this.Npop];
		this.attribute = f.attribute;
		this.pDontCare = (double) (this.attribute - ruleNumber) / (double) f.attribute;
		this.f = f;
	}
}