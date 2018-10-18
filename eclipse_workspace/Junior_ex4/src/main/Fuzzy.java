package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fuzzy {
	//field
	double[][] x; //学習パターン x[pattern][attribute]
	int classes;

	//memberFunction
	//ファイル読み込みメソッド
	public int readFile(String path) throws IOException {
		List<String[]> list = new ArrayList<String[]>();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		line = in.readLine();
		int pattern = Integer.parseInt(line.split(",")[0]);
		int attribute = Integer.parseInt(line.split(",")[1]);
		int classes = Integer.parseInt(line.split(",")[2]);
		x = new double[pattern][attribute];
		while ((line = in.readLine()) != null) {
			list.add(line.split(","));
		}
		in.close();
		for (int i = 0; i < pattern; i++) {
			for (int j = 0; j < attribute; j++) {
				x[i][j] = Double.parseDouble(list.get(i)[j]);
			}
		}
		return classes;
	}

	//メンバシップ関数
	public double memberShip(double x, int division, int k) { //This means the Fuzzy Set
		//使い方
		//	memberShip(x, ファジィ分割数, 選択するファジィ集合番号)
		//k: 0=don't care, 1=small, 2=medium, 3=large;
		//K: ファジィ集合分割数;
		double a = (k - 1.0) / (division - 1.0);
		double b = 1.0 / (division - 1.0);

		if (k == 0) {
			return 1.0; //don't care
		} else {
			return Math.max(1 - Math.abs(a - x) / b, 0.0);
		}
	}

	//適合度計算
	public void calcFit(int[] rule) {
		double fit = 1.0;
		for(int i=0; i<rule.length; i++) {
			if(rule[i]<2) {

			}
		}

	}

	//constractor
	Fuzzy(String path) throws IOException {
		classes = readFile(path);
	}
}
