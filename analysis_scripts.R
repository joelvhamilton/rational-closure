rm(list=ls())

# tests for different numbers of ranks (keep number of statements in each rank (1) and distribution of ranks constant (uniform))

hundred_def_output = read.csv("output100x1Def.csv")
t.test(hundred_def_output$regular, hundred_def_output$binary)
t.test(hundred_def_output$regular, hundred_def_output$regular_with_indexing)
t.test(hundred_def_output$binary, hundred_def_output$binary_with_indexing)

fifty_ranks_output = read.csv("output50ranks.csv")
t.test(fifty_ranks_output$regular, fifty_ranks_output$binary)
t.test(fifty_ranks_output$regular, fifty_ranks_output$regular_with_indexing)
t.test(fifty_ranks_output$binary, fifty_ranks_output$binary_with_indexing)

three_ranks_output = read.csv("outputflyingdutchman.csv")
t.test(three_ranks_output$regular, three_ranks_output$binary)
t.test(three_ranks_output$regular, three_ranks_output$regular_with_indexing)
t.test(three_ranks_output$binary, three_ranks_output$binary_with_indexing)

# tests for different numbers of statements in ranks (keep number of ranks (100) + distribution (uniform) constant here?)

# tests for different distributions of statements in ranks (keep number of ranks (100) constant here?)