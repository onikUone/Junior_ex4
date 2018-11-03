package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
		//初期値
		int Npop = 30;
		int Nrep = 10;
		int n_generation = 100;
		int TEN_CV = 10;
		int TRIAL = 5;
		int seed = 0;
		String[] setName = { "pima", "vehicle", "australian" };

		MersenneTwisterFast mtf = new MersenneTwisterFast();

		//1施行
		double[] recogRate_tra = new double[TRIAL];
		double[] recogRate_tst = new double[TRIAL];
		double[] ave_tra = new double[setName.length];
		double[] ave_tst = new double[setName.length];
		Arrays.fill(recogRate_tra, 0);
		Arrays.fill(recogRate_tst, 0);
		Arrays.fill(ave_tra, 0);
		Arrays.fill(ave_tst, 0);


		for (int nowDataset = 0; nowDataset < setName.length; nowDataset++) {
			String outPath = "src/output/" + setName[nowDataset] + ".dat";
			PrintWriter outPrint = new PrintWriter(new BufferedWriter(new FileWriter(outPath)));
			for (int nowTRY = 0; nowTRY < TRIAL; nowTRY++) {
				for (int nowCV = 0; nowCV < TEN_CV; nowCV++) {
					mtf.setSeed(seed);
					//学習用データ学習 - 識別器獲得
					String path = "src/" + setName[nowDataset] + "/a" + nowTRY + "_" + nowCV + "_" + setName[nowDataset] + "-10tra.dat";
					Fuzzy f_train = new Fuzzy(path);

					GroupController gc = new GroupController(f_train, mtf);
					gc.initialGenerate(Npop);
					for (int nowGene = 0; nowGene < n_generation; nowGene++) {
						gc.nextGeneration(Nrep);
					}
					System.out.println(setName[nowDataset] + nowTRY + "_" + nowCV + " " + "End.");
					gc.getClassifier();		//不要ルール削除
					//評価用データ識別
					path = "src/" + setName[nowDataset] + "/a" + nowTRY + "_" + nowCV + "_" + setName[nowDataset] + "-10tst.dat";
					Fuzzy f_test = new Fuzzy(path);
					double e = gc.evaluation(gc.classifier, f_train);
					outPrint.println(String.valueOf(e));
					recogRate_tra[nowTRY] += 1 - e;
					recogRate_tst[nowTRY] += 1 - gc.evaluation(gc.classifier, f_test);
					seed++;
				}
				recogRate_tra[nowTRY] /= TEN_CV;
				recogRate_tst[nowTRY] /= TEN_CV;
				ave_tra[nowDataset] += recogRate_tra[nowTRY];
				ave_tst[nowDataset] += recogRate_tst[nowTRY];
				System.out.println("学習用 誤識別率 : " + recogRate_tra[nowTRY]);
				System.out.println("評価用 誤識別率 : " + recogRate_tst[nowTRY]);
			}
			outPrint.close();
			ave_tra[nowDataset] /= TRIAL;
			ave_tst[nowDataset] /= TRIAL;
			System.out.println(setName[nowDataset] + " : 学習用 誤識別率 ave = " + (ave_tra[nowDataset] * 100));
			System.out.println(setName[nowDataset] + " : 評価用 誤識別率 ave = " + (ave_tst[nowDataset] * 100));
			System.out.println("-------" + setName[nowDataset] + " End.");
		}
		System.out.println("seed : " + seed);
	}

}
