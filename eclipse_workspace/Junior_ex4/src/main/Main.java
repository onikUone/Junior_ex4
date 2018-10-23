package main;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ

		MersenneTwisterFast mtf = new MersenneTwisterFast(0);
		String set = "pima";
		String path = "src/" + set + "/a0_0_" + set + "-10tra.dat";
		Fuzzy f = new Fuzzy(path);

		GroupController gc = new GroupController(30, f, mtf);
		gc.testGenerate();
		gc.evaluation();
		System.out.println(gc.individual[0].fitness);
		System.out.println(gc.individual[1].fitness);
		System.out.println(gc.individual[2].fitness);

//		gc.initialGenerate();
//		System.out.println(gc.pDontCare);
//
//		System.out.println("--main--");
//		for(int j=0; j<gc.Npop; j++) {
//			System.out.print(j + ": ");
//			for(int i=0; i<f.attribute; i++) {
//				System.out.print(gc.individual[j].rule[i] + " ");
//			}
//			System.out.println("");
//		}

//		int rule[] = {0,4,0,3,3,0,0,0};
//		Individual indi = new Individual(rule, f.x, f.y, f.classes );
//		for(int i=0; i<indi.rule.length; i++) {
//			System.out.println(indi.rule[i]);
//		}
//		System.out.println("myClass is :" + indi.myClass);
//		System.out.println(indi.trust);
//		System.out.println(indi.weight);

//		//mersennetwisterfastの1～5の乱数発生テスト
//		MersenneTwisterFast mtf = new MersenneTwisterFast(0);
//		for(int i=0; i< 10; i++) {
//			System.out.println((int)(mtf.nextDoubleIE()*ruleNumber) + 1);
//		}

//		MersenneTwisterFast mtf = new MersenneTwisterFast(0);
//		for(int j=0; j<2; j++) {
//			for(int i=0; i<5; i++) {
//				System.out.println(mtf.nextDoubleIE());
//			}
//			System.out.println("");
//			for(int i=0; i<5; i++) {
//				System.out.println(mtf.nextDoubleIE());
//			}
//			System.out.println("------");
//		}
	}

}
