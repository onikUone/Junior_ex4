package main;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		String path = "src/pima/a0_0_pima-10tra.dat";
		Fuzzy f = new Fuzzy(path);
		int rule[] = {0,4,0,3,3,0,0,0};
		Individual indi = new Individual(rule, f.x, f.y, f.classes );
		for(int i=0; i<indi.rule.length; i++) {
			System.out.println(indi.rule[i]);
		}
		System.out.println("myClass is :" + indi.myClass);
		System.out.println(indi.trust);
		System.out.println(indi.weight);


	}

}
