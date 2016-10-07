# Part 2: Hide and Seek
***
##Local Search:
Emma, a freshman computer science student at UNC, has N friends. Her friends want to play "hide and seek" in a forest (a grid of size N x N) near campus. To start the game, each one of her N friends wants to stand in the forest (at some grid square)
such that no one can see each other. All of her friends can see only in straight lines, in the 8 possible directions. The forest also contains trees. Friends cannot stand on tree grid squares or see past them. Your job is to help Emma write code to help her friends find places to hide – ie put each friend on a grid location such that no other friend can see them.    

Let’s define the CSP such that variables are grid locations, ie {A11,A12,A13,....} with domains states of the 
grid,  i.e.  {friend,  tree,  empty}.  Describe  the  constraints  on  the  variables. Then, implement local search (code)
to find an assignment that satisfies this problem (see below for a description of the problem input and  format  of  the  solution). You  should  initialize  the  locations  of  the  friends  randomly  (one  per  column) and experiment with different methods for selecting which friend to move next.    

In your report, explain your approach and  describe  the  selection  methods  you  tried. 
Run  your  algorithm  on  different  random initializations  and several different  input  forests (for  example  experiment  with  differently  sized  grids  or fewer/more trees). Include all of these in your report as well as the solutions found by your local search algorithm  and  the number  of  iterations  executed  by  local  search for  each  case.  Note,  sometimes  local search can get stuck in local minima. Describe any inputs-initializations where this was the case.     



**Input:** 
A  text  file  input.txt  where  the  first  line  of the  input  contains 2  integers  where  the  first  value corresponds to N (the number of friends) and the second value corresponds to K (the number of trees in the park). 
The next K lines contain the row and column locations of the trees in the park.   

**Output:** 
Print out the row and column locations of each of the N friends.   

##Explanation   
For our implementation for the question, we programmed in Java. 
