package main;

import static main.FuzzySetTriangle.*;

public class GroupController {
	//member field
	int Npop;
	int Nrep;
	int attribute; //扱うデータセットの属性数
	double pDontCare;
	double pCross = 0.9;
	double pMutation;
	double recogRateMax;

	Individual[] bestGeneration;
	Individual[] individual;
	Individual[] classifier;
	Fuzzy f;
	MersenneTwisterFast mtf;

	//member function

	//test個体群
	public void testGenerate() {
		Npop = 3;
		this.individual = new Individual[this.Npop];
		int setRule1[] = { 0, 0, 0, 2, 0, 0, 0, 0 };
		int setRule2[] = { 0, 4, 0, 0, 3, 0, 0, 4 };
		int setRule3[] = { 0, 1, 0, 3, 1, 2, 0, 0 };
		individual[0] = new Individual(setRule1, f);
		individual[1] = new Individual(setRule2, f);
		individual[2] = new Individual(setRule3, f);

	}

	//初期個体群の生成
	public void initialGenerate(int _Npop) {
		this.Npop = _Npop;
		this.individual = new Individual[this.Npop];
		this.bestGeneration = new Individual[this.Npop];
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
		recogRateMax = evaluation(this.individual, this.f);
		for(int i = 0; i < individual.length; i++) {
			bestGeneration[i] = new Individual(individual[i].rule, f);
		}
	}

	//個体の評価 - および適応度(fitness)の計算 - 識別率の計算
	public double evaluation(Individual[] _individual, Fuzzy _f) {
		int flg;
		int recognized = 0;
		int maxRuleIndex = -1;
		double max = 0;
		double comp = 0;
		for(int i = 0; i < _individual.length; i++) {
			_individual[i].clearFitness();
		}
		for (int p = 0; p < _f.x.length; p++) {
			flg = 0;
			for (int i = 0; i < _individual.length; i++) {
				if (_individual[i].trust <= 0.5) {
					continue;
				}
				if (flg == 0) {
					max = Fuzzy.calcFit(_individual[i].rule, _f.x[p]) * _individual[i].weight;
					maxRuleIndex = i;
					flg = 1;
				} else {
					comp = Fuzzy.calcFit(_individual[i].rule, _f.x[p]) * _individual[i].weight;
					if (comp == max) {
						if (_individual[maxRuleIndex].myClass != _individual[i].myClass || comp == 0) {//最大値が等しく && (結論部が違う || ルールにフィットしていない)
							flg = -1;
							continue;
						}
					}
					if (comp > max) {
						max = comp;
						maxRuleIndex = i;
						flg = 1;
					}
				}
			}
			if (flg != -1 && _individual[maxRuleIndex].myClass == _f.y[p]) {
				_individual[maxRuleIndex].fitness++;
				recognized++;
			}
		}
		return (double) recognized / _f.y.length;
	}

	//新規 子ルール生成
	public void nextChildren() {
		Individual[] children = new Individual[this.Nrep];
		int mother, motherFalse;
		int father, fatherFalse;
		int[] motherRule = new int[attribute];
		int[] fatherRule = new int[attribute];
		int[] childRule;

		for (int i = 0; i < this.Nrep; i++) {
			//親の決定
			mother = (int) (mtf.nextDoubleIE() * individual.length);
			while (true) {
				motherFalse = (int) (mtf.nextDoubleIE() * individual.length);
				if (motherFalse == mother) {
					continue;
				} else {
					break;
				}
			}
			if (individual[mother].fitness < individual[motherFalse].fitness) {
				mother = motherFalse;
			}
			while (true) {
				father = (int) (mtf.nextDoubleIE() * individual.length);
				if (father == mother) {
					continue;
				} else {
					break;
				}
			}
			while (true) {
				fatherFalse = (int) (mtf.nextDoubleIE() * individual.length);
				if (fatherFalse == father || fatherFalse == mother) {
					continue;
				} else {
					break;
				}
			}
			if (individual[father].fitness < individual[fatherFalse].fitness) {
				father = fatherFalse;
			}

			//一様交叉
			if (mtf.nextDoubleIE() < pCross) {
				for (int j = 0; j < attribute; j++) {
					if (mtf.nextDoubleIE() < 0.5) {
						motherRule[j] = individual[father].rule[j];
						fatherRule[j] = individual[mother].rule[j];
					} else {
						motherRule[j] = individual[mother].rule[j];
						fatherRule[j] = individual[father].rule[j];
					}
				}
			} else {
				for (int j = 0; j < attribute; j++) {
					motherRule[j] = individual[mother].rule[j];
					fatherRule[j] = individual[father].rule[j];
				}
			}
			if (mtf.nextDoubleIE() < 0.5) {
				childRule = motherRule;
			} else {
				childRule = fatherRule;
			}

			//突然変異
			for (int j = 0; j < attribute; j++) {
				if (mtf.nextDoubleIE() < pMutation) {
					childRule[j] = (int) (mtf.nextDoubleIE() * (ruleNumber + 1));
				}
			}

			//子の生成
			children[i] = new Individual(childRule, this.f);
		}

		//世代交代
		bubbleSort(individual);
		for(int i = 0; i < this.Nrep; i++) {
			this.individual[i] = new Individual(children[i].rule, f);
		}
	}

	//次世代更新
	public void nextGeneration(int _Nrep) {
		this.Nrep = _Nrep;
		double recogRate;
		nextChildren();
		recogRate = evaluation(this.individual, this.f);
		if(recogRate > recogRateMax) {
			recogRateMax = recogRate;
			for(int i = 0; i < individual.length; i++) {
				this.bestGeneration[i] = new Individual(this.individual[i].rule, f);
			}
//			System.out.println(recogRate * 100);
		}
//		System.out.println(recogRate * 100);
	}

	public void bubbleSort(Individual[] _individual) {
		boolean flg = true;
		while(flg) {
			flg = false;
			for(int i = 1; i < _individual.length; i++) {
				if(_individual[i-1].fitness > _individual[i].fitness) {
					Individual tmp = _individual[i];
					_individual[i] = _individual[i-1];
					_individual[i-1] = tmp;
					flg = true;
				}
			}
		}
	}

	//識別器の獲得
	public void getClassifier() {
		int n_rule = 0;
		int tmp = 0;
		evaluation(bestGeneration, this.f);
		for(int i = 0; i < bestGeneration.length; i++) {
			if(bestGeneration[i].fitness != 0) {
				n_rule++;
			}
		}
		classifier = new Individual[n_rule];

		for(int i = 0; i < bestGeneration.length; i++) {
			if(bestGeneration[i].fitness != 0) {
				classifier[tmp] = new Individual(bestGeneration[i].rule, f);
				tmp++;
			}
		}
	}

	//constractor
	GroupController(Fuzzy f, MersenneTwisterFast mtf) {
		this.mtf = mtf;
		this.attribute = f.attribute;
		this.pDontCare = (double) (this.attribute - ruleNumber) / (double) f.attribute;
		this.f = f;
		this.pMutation = (double) 1 / (double) attribute;
		this.recogRateMax = 0;
	}
}
