package main;


/* FuzzySetTriangle クラス
 *     これは定数クラスとして扱う．
 *     import static main.FuzzyConst.*;
 *     で，TriRuleが使えるようになる．
 *
 */
public final class FuzzySetTriangle {
	public static final int ruleNumber = 5;
	public static final int[][] TriRule = new int[1+ruleNumber][2];	//triRule[don'tCare + 2division + 3division][k and K]
	static {
		TriRule[0][0] = 0; TriRule[0][1] = 0;		//don't care

		TriRule[1][0] = 1; TriRule[1][1] = 2;		//k = 1, K = 2
		TriRule[2][0] = 2; TriRule[2][1] = 2;		//k = 2, K = 2

		TriRule[3][0] = 1; TriRule[3][1] = 3;		//k = 1, K = 3
		TriRule[4][0] = 2; TriRule[4][1] = 3;		//k = 2, K = 3
		TriRule[5][0] = 3; TriRule[5][1] = 3;		//k = 3, K = 3		
	}

	//constractor
	private FuzzySetTriangle() {}
}
