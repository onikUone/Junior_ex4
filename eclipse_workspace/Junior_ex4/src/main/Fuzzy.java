package main;

import static main.FuzzySetTriangle.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fuzzy {
	//field
	double[][] x; //学習パターン x[pattern][attribute]
	int[] y;
	int pattern;
	int attribute;
	int classes;

	//memberFunction
	//ファイル読み込みメソッド
	public void readFile(String path) throws IOException {
		List<String[]> list = new ArrayList<String[]>();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		line = in.readLine();
		this.pattern = Integer.parseInt(line.split(",")[0]);
		this.attribute = Integer.parseInt(line.split(",")[1]);
		this.classes = Integer.parseInt(line.split(",")[2]);
		x = new double[pattern][attribute];
		y = new int[pattern];
		while ((line = in.readLine()) != null) {
			list.add(line.split(","));
		}
		in.close();
		for (int i = 0; i < pattern; i++) {
			for (int j = 0; j < attribute; j++) {
				x[i][j] = Double.parseDouble(list.get(i)[j]);
			}
			y[i] = Integer.parseInt(list.get(i)[attribute]);
		}
	}

	//メンバシップ関数
	public static double memberShip(double x, int myFuzzySetNum, int division) { //This means the Fuzzy Set
		//使い方
		//	memberShip(x, ファジィ分割数, 選択するファジィ集合番号)
		//K: ファジィ集合分割数;
		double a = (myFuzzySetNum - 1.0) / (division - 1.0);
		double b = 1.0 / (division - 1.0);

		if (myFuzzySetNum == 0) {
			return 1.0; //don't care
		} else {
			return Math.max(1 - Math.abs(a - x) / b, 0.0);
		}
	}

	//適合度計算
	public static double calcFit(int[] rule, double[] x) {
		double fit = 1.0;
		for (int i = 0; i < rule.length; i++) {
			fit *= memberShip(x[i], TriRule[rule[i]][0], TriRule[rule[i]][1]);
		}
		return fit;
	}

	//信頼度計算
	public static double calcTrust(int[] rule, double[][] x, int[] y, int classIs) {
		double temp1 = 0;
		double temp2 = 0;
		for (int p = 0; p < x.length; p++) {
			if (y[p] == classIs) {
				temp1 += calcFit(rule, x[p]);
			}
			temp2 += calcFit(rule, x[p]);
		}
		return temp1 / temp2;

	}

	//未知パターン推論
	public static int reasoning(double[] x, Individual[] individuals) {
		int reasoningClass = -1;
		double max = 0;
		double comp = 0;
		for (int i = 0; i < individuals.length; i++) {
			if(i == 0) {
				max = calcFit(individuals[i].rule, x) * individuals[i].weight;
				reasoningClass = individuals[i].myClass;
			}
			else {
				comp = calcFit(individuals[i].rule, x) * individuals[i].weight;
				if(comp > max) {
					max = comp;
					reasoningClass = individuals[i].myClass;
				}
			}
		}
		return reasoningClass;
	}

	//constractor
	Fuzzy(String path) throws IOException {
		readFile(path);
	}
}
