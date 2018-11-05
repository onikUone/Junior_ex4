# -*- coding:utf-8 -*-
import sys
import time
import numpy as np
import scipy.stats

#コマンドライン引数読み込み
argvs = sys.argv
argc = len(argvs)
if argc != 3:
	print("比較したい２つのファイル名を実行時に引数で指定してください．")
else:
	fileName1 = argvs[1]
	fileName2 = argvs[2]
	print("比較するファイル：", fileName1, fileName2)

#ファイル読み込み，float変換
file1 = open(fileName1, 'r')
file2 = open(fileName2, 'r')

line1 = file1.readlines()
line2 = file2.readlines()

nums1 = [0] * 50
nums2 = [0] * 50

for i in range(50):
	nums1[i] = float(line1[i])
	nums2[i] = float(line2[i])

#ウィルコクソンの符号順位検定（wilcoxon signed rank test)
T, p = scipy.stats.wilcoxon(nums1, nums2)

print("T = %(T)s" %locals() )
print("p = %(p)s" %locals() )

# 統計学的有意水準 0.05 より高いかどうか調べ
print("有意水準５％で２つの結果には… ")

time.sleep(3.0)
if p >= 0.025 and p <= 0.975 :
    print("　　差がありません！(≧∇≦)b")
else:
    print("　　差があります…　(´；ω；｀)")