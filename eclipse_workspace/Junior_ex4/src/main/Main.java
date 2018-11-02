package main;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		//初期値
		int Npop = 30;
		int Nrep = 10;
		int n_generation = 10000;

		//1施行
		MersenneTwisterFast mtf = new MersenneTwisterFast(0);
		String set = "australian";
		String path = "src/" + set + "/a0_0_" + set + "-10tra.dat";
		Fuzzy f = new Fuzzy(path);

//		GroupController gc = new GroupController(f, mtf);
//		gc.testGenerate();
//		gc.evaluation(gc.individual);
//		System.out.print("誤識別率 : ");
//		System.out.println((1 - gc.evaluation(gc.individual)) * 100);
//		System.out.println("-----");
//		System.out.println(gc.individual[0].fitness);
//		System.out.println(gc.individual[1].fitness);
//		System.out.println(gc.individual[2].fitness);
//		System.out.println("-----");

		GroupController gc = new GroupController(f, mtf);
		gc.initialGenerate(Npop);
		for (int i = 0; i < n_generation; i++) {
			gc.nextGeneration(Nrep);
		}
		System.out.println("End.");
		System.out.println(gc.evaluation(gc.bestGeneration));
	}

}
