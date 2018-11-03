package main;

public class Test {
	int i;
	public void a(MersenneTwisterFast mtf) {
		System.out.println(mtf.nextDoubleIE());

	}
	Test(int _i){
		this.i = _i;
	}
	//		GroupController gc = new GroupController(f, mtf);
	//		gc.testGenerate();
	//		gc.evaluation(gc.individual);
	//		System.out.print("誤識別率 : ");
	//		System.out.println((1 - gc.evaluation()) * 100);
	//		System.out.println("-----");
	//		System.out.println(gc.individual[0].fitness);
	//		System.out.println(gc.individual[1].fitness);
	//		System.out.println(gc.individual[2].fitness);
	//		System.out.println("-----");

	//		gc.initialGenerate(Npop);
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

	//mersennetwisterfastのインスタンス引き渡しテスト
	//		Test t = new Test();
	//		mtf.setSeed(0);
	//		for (int i = 0; i < 5; i++) {
	//			t.a(mtf);
	//		}
	//		System.out.println("");
	//		mtf.setSeed(0);
	//		for (int i = 0; i < 5; i++) {
	//			t.a(mtf);
	//		}
}

