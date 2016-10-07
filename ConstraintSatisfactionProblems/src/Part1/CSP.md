# Constraint Satisfaction Problem's and Games

***

### Part 1: Constraint Satisfaction Problem (Graduation Dinner)

######Explanation/Setting the Scene:
You are helping to figure out the seating at your graduation dinner table. Your mom has already worked out seating on your side of the table, but wants you to decide where to put the relatives on the other side of the table. The relatives are your cousin Jasmine, your cousin Jason, your aunt Trudy, her husband Randy, and your aunt Misty. There are 5 seats, numbered 1 through 5 with 1 being the seat closest to the kitchen. 

######Constraints:
* No two relatives can sit in the same seat.   
* Your cousins can't be seated next to each other because they will start a food fight.   
* Jason as the eldest cousin gets the privilege of being seated closer to the kitchen than his sister Jasmine so he can get to the food first.   
* Your aunt Misty shouldn't be seated next to Jason, Trudy or Randy because she invariably instigates an argument about politics and disrupts dinner.   
* Additionally, Misty and Trudy have to be seated with at least 2 seats between them as a buffer.   

######Problem/Solution:

a) **Formulate this problem as a binary CSP where the variables are relatives. In particular, state the domains and binary constraints on variables (e.g. different(A,B), notAdjacent(A,B), A<B, etc ...).**
  * **Variables:**   
  X is a set of variables {X<sub>1</sub>, X<sub>2</sub>, ..., X<sub>N</sub>}. Therefore to simplify the variable set {Jasmine, Jason, Trudy, Randy, Misty} we will be using the set X = {I,O,T,R,M} as described by the table below. 

| Name     | Variable |
| -------- | -------- |
| Jasmine  |    I     |
| Jason    |    O     |
| Trudy    |    T     |
| Randy    |    R     |
| Misty    |    M     |   
  
  * **Domains:**   
  D is a set of domains {D<sub>1</sub>, D<sub>2</sub>, ..., D<sub>N</sub>}, one for each variable.  
  For this problem the domains are the seats in which an individuals can sit in. Therefore the set of domains of seats as described by the problem where seat number 1 is the seat closest to the kitchen is as follows D<sub>i</sub> = {1,2,3,4,5}.   
  * **Variable Constraints:**   
  C is a set of constrainsts that specify allowable combinations of values. The constraints for this problem uses the global constraint Alldiff: one individual for each seat in the domain.
   + Alldiff(I,O,T,R,M)   
   + NotAdjacent(I,O)
   + NotAdjacent(M,O)
   + NotAdjacent(M,R)
   + NotAdjacent(M,T)
   + O<I (Jason < Jasmine)
   + abs(M-T)>=2 (Misty and Trudy have to be seated with at least 2 seats between)

b) **If you ran an arc consistency algorithm on this problem what would the domains be for each variable?**    
Before the arc consistency algorithm is run, the variables contains the appropriate domains. After the Arc consistency algorithm has run on this problem, the following domains would be available for each variable. Thus, based on the conclusion of the algorithm, Jasmine and Misty's positions at the table are fixed at position 4 and 5 respectfully if a solution is desired. 

| Name     | Domains Before Arc Consistency |
| -------- | --------- |
| Jasmine  |   2,3,4,5 |
| Jason    | 1,2,3,4   |
| Trudy    | 1,2,3,4,5 |
| Randy    | 1,2,3,4,5 |
| Misty    | 1,2,3,4,5 |

| Name     | Domains After Arc Consistency |
| -------- | -------- |
| Jasmine  |    4     |
| Jason    |  1,2,3   |
| Trudy    |   1,2    |
| Randy    |  1,2,3   |
| Misty    |    5     |

c) **Which variable or variables would be assigned first according to MRV using the arc-consistent domains from part b?**   

 The first assigned variable according to the MRV in using part b would be Jasmine to 4 and Misty to 5 because each only has one possible value.

d) **If we assume that Randy is seated in the middle (seat 3) and run arc consistency, what will the remaining domains be?**   

With Randy in seat three, in order to maintain arc consistency Jason's domains reduce to 1 and 2. Thus allowing two different solutions with Jason and Trudy alternating positions at the table. The order of the solulions is detailed below.

| Name     | Domains After Arc Consistency |
| -------- | -------- |
| Jasmine  |    4     |
| Jason    |   *3*    |
| Trudy    |   1,2    |
| Randy    |   1,2    |
| Misty    |    5     |  

e) **List all solutions to this CSP with Randy in seat 3, or state that none exist.**    
 * Solution 1: Trudy, Jason, Randy, Jasmine, Misty (T,O,R,I,M)
 * Solution 2: Jason, Trudy, Randy, Jasmine, Misty (O,T,R,I,M) 

