rm(list=ls())

# tests for different numbers of ranks (keep number of statements in each rank (1) and distribution of ranks constant (uniform))
hundred_rank_output = read.csv("output100ranks.csv")
shapiro.test(hundred_rank_output$regular - hundred_rank_output$binary)
shapiro.test(hundred_rank_output$regular - hundred_rank_output$regular_with_indexing)
t.test(hundred_rank_output$regular, hundred_rank_output$binary, paired=TRUE, alternative="two.sided")
t.test(hundred_rank_output$regular, hundred_rank_output$regular_with_indexing, paired=TRUE, alternative="two.sided")

t.test(hundred_rank_output$regular, hundred_rank_output$regular_with_indexing, paired=TRUE)
t.test(hundred_rank_output$binary, hundred_rank_output$binary_with_indexing, paired=TRUE)

fifty_ranks_output = read.csv("output50ranks.csv")
t.test(fifty_ranks_output$regular, fifty_ranks_output$binary)
t.test(fifty_ranks_output$regular, fifty_ranks_output$regular_with_indexing)
t.test(fifty_ranks_output$binary, fifty_ranks_output$binary_with_indexing)

three_ranks_output = read.csv("outputflyingdutchman.csv")
t.test(three_ranks_output$regular, three_ranks_output$binary)
t.test(three_ranks_output$regular, three_ranks_output$regular_with_indexing)
t.test(three_ranks_output$binary, three_ranks_output$binary_with_indexing)

same_antecdent_50ranks_output = read.csv("output50ranks.csv")
t.test(same_antecdent_50ranks_output$regular, same_antecdent_50ranks_output$binary, alternative="two.sided")
plot(density(same_antecdent_50ranks_output$regular - same_antecdent_50ranks_output$binary))

# tests for different numbers of statements in ranks (keep number of ranks (100) + distribution (uniform) constant here?)

# tests for different distributions of statements in ranks (keep number of ranks (100) constant here?)