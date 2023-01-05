package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE

        StdIn.setFile(file); // reads file
        
        int r = StdIn.readInt(); // reads # of rows
        int c = StdIn.readInt(); // reads # of columns

        grid = new boolean[r][c]; // creates grid

        //Intialize grid with false and true values respectively
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                grid[i][j] = StdIn.readBoolean();
            }
        }

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        // update this line, provided so that code compiles

        if(grid[row][col] == true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE

        for(int i = 0; i < 5; i ++){
            for(int j = 0; j < 5; j++){
                if(grid[i][j] == true){
                    return true;
                }
            }
        }

        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int count = 0;
        
        //Special Cases
        if((row == 0 || row == grid.length-1 || col == 0 || col == grid[0].length-1)){
            // #1
            if(grid[(row-1+grid.length)% grid.length][col] == true){
                count++;
            }
            //#2
            if(grid[(row+1)% grid.length][col] == true){
                count++;
            }
            //#3
            if(grid[row][(col-1+grid[0].length)%grid[0].length] == true){
                count++;
            }
            //#4
            if(grid[row][(col+1)%grid[0].length] == true){
                count++;
            }

            //Edges
            //#5
            if(grid[(row-1+grid.length)% grid.length][(col-1+grid[0].length)%grid[0].length] == true){
                count++;
            }
            //#6
            if(grid[(row+1)% grid.length][(col-1+grid[0].length)%grid[0].length] == true){
                count++;
            }
            //#7
            if(grid[(row-1+grid.length)% grid.length][(col+1)%grid[0].length] == true){
                count++;
            }
            //#8
            if(grid[(row+1)% grid.length][(col+1)%grid[0].length] == true){
                count++;
            }


        }else{
        
            //Top Row
            if(grid[row-1][col-1]==true){
                count++;
            }
            if(grid[row-1][col]==true){
                count++;
            }
            if(grid[row-1][col+1]==true){
                count++;
            }

            //Middle Row
            if(grid[row][col-1]==true){
                count++;
            }
            if(grid[row][col+1]==true){
                count++;
            }

            //Bottom Row
            if(grid[row+1][col-1]==true){
                count++;
            }
            if(grid[row+1][col]==true){
                count++;
            }
            if(grid[row+1][col+1]==true){
                count++;
            }
        }
        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        boolean[][] grid2 = new boolean[grid.length][grid[0].length];
        
        //Copy grid Array into grid2
        for(int a = 0; a < grid.length;a++){
            for(int b = 0; b < grid[0].length;b++){
                grid2[a][b] = grid[a][b];
            }
        }

        // Rule 1: A cell with one neighboor or no neighbor dies
        // Rule 2: Dead cells with excalty three numbers becomes alive
        // Rule 3: Alive cells with two or three neighbors die
        // Rule 4: Alive cells with 4 or more neighboors dies

        for(int i = 0;i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                //Rule #1
                if(grid[i][j] == true && numOfAliveNeighbors(i, j) <= 1){
                    grid2[i][j] = false;
                }
                
                //Rule#2
                if(grid[i][j] == false && numOfAliveNeighbors(i, j) == 3){
                    grid2[i][j] = true;
                }
                
                //Rule#3
                if(grid[i][j] == true &&(numOfAliveNeighbors(i, j) >= 2 || numOfAliveNeighbors(i, j) <=3)){
                    //nothing happens
                }

                //Rule#4
                if(grid[i][j] == true && numOfAliveNeighbors(i, j) >=4){
                    grid2[i][j] = false;
                }
            }
        }

        
        return grid2; // update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        grid = computeNewGrid();
        totalAliveCells = getTotalAliveCells();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE

        //runs "n" number of times
        for(int i = 0; i < n; i++){
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE


        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(grid.length,grid[0].length);

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length;j++){
                if(grid[i][j] == true){
                    if((i == 0 || i == grid.length-1 || j == 0 || j == grid[0].length-1)){
                        // #1
                        
                        if(grid[(i-1+grid.length)% grid.length][j] == true){
                            uf.union(i, j, (i-1+grid.length)% grid.length, j);
                        }
                        //#2
                        if(grid[(i+1)% grid.length][j] == true){
                            uf.union(i, j, ((i+1)% grid.length), j);
                        }
                        //#3
                        if(grid[i][(j-1+grid[0].length)%grid[0].length] == true){
                            uf.union(i, j, i, (j-1+grid[0].length)%grid[0].length);
                        }
                        //#4
                        if(grid[i][(j+1)%grid[0].length] == true){
                            uf.union(i, j, i, (j+1)%grid[0].length);
                        }
        
                        //Edges
                        //#5
                        if(grid[(i-1+grid.length)% grid.length][(j-1+grid[0].length)%grid[0].length] == true){
                            uf.union(i, j,(i-1+grid.length)% grid.length ,(j-1+grid[0].length)%grid[0].length );
                        }
                        //#6
                        if(grid[(i+1)% grid.length][(j-1+grid[0].length)%grid[0].length] == true){
                            uf.union(i, j, (i+1)% grid.length,(j-1+grid[0].length)%grid[0].length);
                        }
                        //#7
                        if(grid[(i-1+grid.length)% grid.length][(j+1)%grid[0].length] == true){
                            uf.union(i, j, (i-1+grid.length)% grid.length,(j+1)%grid[0].length);
                        }
                        //#8
                        if(grid[(i+1)% grid.length][(j+1)%grid[0].length] == true){
                            uf.union(i, j, (i+1)% grid.length, (j+1)%grid[0].length);
                        }
            
            
                    }else{
        
                        //Top Row
                        if(grid[i-1][j-1]==true){
                            uf.union(i, j, i-1, j-1);
                        }
                        if(grid[i-1][j]==true){
                            uf.union(i, j, i-1, j);
                        }
                        if(grid[i-1][j+1]==true){
                            uf.union(i, j, i-1, j+1);
                        }
            
                        //Middle Row
                        if(grid[i][j-1]==true){
                            uf.union(i, j, i, j-1);
                        }
                        if(grid[i][j+1]==true){
                            uf.union(i, j, i, j+1);
                        }
            
                        //Bottom Row
                        if(grid[i+1][j-1]==true){
                            uf.union(i, j, i+1, j-1);
                        }
                        if(grid[i+1][j]==true){
                            uf.union(i, j, i+1, j);
                        }
                        if(grid[i+1][j+1]==true){
                            uf.union(i, j, i+1, j+1);
                        }
                    }

                    //Maybe Delete
                    if(numOfAliveNeighbors(i, j)  == 0){
                        uf.union(i, j, i, j);
                    }
                }
            }
        }

        
        ArrayList<Integer> roots = new ArrayList<Integer>();
        for(int i = 0; i < grid.length;i++){
            for(int j = 0; j < grid[0].length;j++){
                if(grid[i][j] == true){
                    if(roots.contains(uf.find(i, j))){
                        //do nothing
                    }else{
                        roots.add(uf.find(i, j));
                    }
                }
            }
        }

        int count = 0;
        for(int i = 0; i < roots.size(); i++){
            count++;
        }

        return count; // update this line, provided so that code compiles
    }
}
