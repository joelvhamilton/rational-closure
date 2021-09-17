rm(list=ls())

performTests = function(data_frame) {
  print("Regular vs Indexing")
  print(wilcox.test(data_frame$regular, data_frame$regular_with_indexing, paired=TRUE))
  
  print("Regular vs Binary")
  print(wilcox.test(data_frame$regular, data_frame$binary, paired=TRUE))
  
  print("Regular vs Binary with Indexing")
  print(wilcox.test(data_frame$regular, data_frame$binary_with_indexing, paired=TRUE))
}

# tests for different numbers of ranks (keep number of statements in each rank (1) and distribution of ranks constant (uniform))
hundred_rank_diff_ante = read.csv("outputranks100queries_DIFFANTE.csv")
hundred_rank_same_ante = read.csv("outputranks100queries_SAME_ANTE.csv")
hundred_rank_half_repeated_ante = read.csv("outputranks100queries_half_repeated_ante.csv")
hundred_rank_1st_rank = read.csv("outputranks100queries_1strank.csv")
hundred_rank_100th_rank = read.csv("outputranks100queries_100thrank.csv")

fifty_rank_diff_ante = read.csv("outputranks50queries_DIFFANTE.csv")
fifty_rank_same_ante = read.csv("outputranks50queries_SAME_ANTE.csv")
fifty_rank_half_repeated_ante = read.csv("outputranks50queries_half_repeated_ante.csv")
fifty_rank_1st_rank = read.csv("outputranks50queries_1strank.csv")
fifty_rank_50th_rank = read.csv("outputranks50queries_50thrank.csv")

ten_rank_diff_ante = read.csv("outputranks10queries_DIFFANTE.csv")
ten_rank_same_ante = read.csv("outputranks10queries_SAME_ANTE.csv")
ten_rank_half_repeated_ante = read.csv("outputranks10queries_half_repeated_ante.csv")
ten_rank_1st_rank = read.csv("outputranks10queries_1strank.csv")
ten_rank_10th_rank = read.csv("outputranks10queries_10thrank.csv")

performTests(hundred_rank_diff_ante)
performTests(hundred_rank_same_ante)
performTests(hundred_rank_half_repeated_ante)
performTests(hundred_rank_1st_rank)
performTests(hundred_rank_100th_rank)

performTests(fifty_rank_diff_ante)
performTests(fifty_rank_same_ante)
performTests(fifty_rank_half_repeated_ante)
performTests(fifty_rank_1st_rank)
performTests(fifty_rank_50th_rank)

performTests(ten_rank_diff_ante)
performTests(ten_rank_same_ante)
performTests(ten_rank_half_repeated_ante)
performTests(ten_rank_1st_rank)
performTests(ten_rank_10th_rank)

# tests for different numbers of statements in ranks (keep number of ranks (100) + distribution (uniform) constant here?)

# uniform results over 50 ranks are taken from the "fifty rank" data of the ranks comparison

norm_50ranks_diff_ante = read.csv("outputdist_50queries_DIFFANTE1.csv")
norm_50ranks_same_ante = read.csv("outputdist_50queries_SAME_ANTE1.csv")
norm_50ranks_half_repeated_ante = read.csv("outputdist_50queries_half_repeated_ante1.csv")
norm_50ranks_1st_rank = read.csv("outputdist_50queries_1strank1.csv")
norm_50ranks_50th_rank = read.csv("outputdist_50queries_50thrank1.csv")

expo_50ranks_diff_ante = read.csv("outputdist_50queries_DIFFANTE2.csv")
expo_50ranks_same_ante = read.csv("outputdist_50queries_SAME_ANTE2.csv")
expo_50ranks_half_repeated_ante = read.csv("outputdist_50queries_half_repeated_ante2.csv")
expo_50ranks_1st_rank = read.csv("outputdist_50queries_1strank2.csv")
expo_50ranks_50th_rank = read.csv("outputdist_50queries_50thrank2.csv")

performTests(norm_50ranks_diff_ante)
performTests(norm_50ranks_same_ante)
performTests(norm_50ranks_half_repeated_ante)
performTests(norm_50ranks_1st_rank)
performTests(norm_50ranks_50th_rank)

performTests(expo_50ranks_diff_ante)
performTests(expo_50ranks_same_ante)
performTests(expo_50ranks_half_repeated_ante)
performTests(expo_50ranks_1st_rank)
performTests(expo_50ranks_50th_rank)

# tests for different distributions of statements in ranks (keep number of ranks (100) constant here?)
