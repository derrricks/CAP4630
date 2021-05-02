# CAP4630 - Intro to Artificial Intelligence

# Assignment 1: 
Find the shortest path (using A* algorithm) between two points on a plane that has convex polygonal obstacles as shown in Figure 3.31

<img width="556" alt="AI Assignment 1 Problem" src="https://user-images.githubusercontent.com/34103060/116801263-25de9700-aad6-11eb-975b-ea0902356b7a.png">


# Assignment 2: 
Given an environment with polygonal obstacles, a maximum budget C (i.e., path length), and {S, G}, your goal would be to find the shortest path between S and G such that the path cost is less than C using Algorithm 1 in [Stern 2014].
Test your program with the environment shown in Fig. 3.31 first.

  - Algorithm Source: https://escholarship.org/content/qt4ct236k1/qt4ct236k1.pdf
  
# RRT Visual: (AI Final Project)
  - Implementation of Rapidly-Exploring Random Tree Algorithm
  - Featues live visualization of algoirthm
  - Featues random start and goal node with three different environments to test with
  - Sources: 
    - https://en.wikipedia.org/wiki/Rapidly-exploring_random_tree
    - http://msl.cs.uiuc.edu/~lavalle/papers/LavKuf01.pdf

  - Environment 1 Samples:
<img width="998" alt="RRT Sample Output Enviroment 1" src="https://user-images.githubusercontent.com/34103060/116826483-363c5380-ab62-11eb-8f99-604cbdbdac68.png">


# Overall Improvements to Consider: 
  - add method to find neighboring nodes without hardcoding (Assignment 1 and 2)
  - add live visualization (Assignment 1 and 2)
  - budget issue (Assignment 2)
