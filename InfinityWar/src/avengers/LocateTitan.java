package avengers;
import java.util.*;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan3.in locatetitan3.out
 *
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];

        //input file arguements
        StdIn.setFile(locateTitanInputFile);
        int numOfGen = StdIn.readInt();

        //reads Generators and Associated Value
        ArrayList<Node> generators = new ArrayList<>();
        Node val;
        int gen;
        double func;
        for(int i = 0; i < numOfGen; i++){
            val = new Node();
            gen = StdIn.readInt();
            func = StdIn.readDouble();
            val.setNum(gen);
            val.setFunc(func);
            generators.add(val);
        }

        int weight;
        double p;
        int total;
        int[][] adjMatrix = new int[numOfGen][numOfGen];
        for(int i = 0; i < adjMatrix.length; i++){
            for(int j = 0; j < adjMatrix[0].length; j++){
                weight = StdIn.readInt();
                p = generators.get(i).getFunc() * generators.get(j).getFunc();
                total = (int)(weight/p);
                adjMatrix[i][j] = total;
            }
        }


        StdOut.setFile(locateTitanOutputFile);
        
        int minCost[] = new int[numOfGen];
        boolean[] DijkstraSet = new boolean[numOfGen];

        for(int i = 0; i < minCost.length;i++){
            if(i == 0){
                minCost[i] = 0;
            }else{
                minCost[i] = Integer.MAX_VALUE;
            }
        }

        for(int i = 0; i < numOfGen-1; i++){
            //getMinCodeNode
            int min = Integer.MAX_VALUE;
            int currentSource = 0;
            for(int j = 0; j < minCost.length; j++){
                if(j == 0 && DijkstraSet[j] == false){
                    currentSource = j;
                }
                if(minCost[j] < min && DijkstraSet[j] == false){
                    min = minCost[j];
                    currentSource = j;
                }
            }

            DijkstraSet[currentSource] = true;

            //number of neighbors
            ArrayList<Integer> neighbor = new ArrayList<>();
            for(int z = 0; z < adjMatrix[currentSource].length; z++){
                neighbor.add(adjMatrix[currentSource][z]);
            }

            for(int a = 0; a < neighbor.size(); a++){
                if(neighbor.get(a) != 0){
                    if(DijkstraSet[a] == false && minCost[currentSource] != Integer.MAX_VALUE && minCost[a] > (minCost[currentSource] + neighbor.get(a))){
                        minCost[a] = minCost[currentSource] + neighbor.get(a);
                    }
                }
            }


        }

        int minDistance = minCost[numOfGen-1];
        

        StdOut.print(minDistance);
        

    }
}
