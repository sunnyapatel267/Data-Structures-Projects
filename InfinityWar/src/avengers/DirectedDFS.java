package avengers;
import java.util.*;

public class DirectedDFS {
    private boolean[] visted;
    int count = 0;
    Stack<Integer> s = new Stack<>();
    

    private boolean checkVertices(int[][] arr, int z){
        int count = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[z][i]==0){ 
                count++;
            }
        }

        if(count == arr.length){
            return true;
        }
        return false;
    }

    public DirectedDFS(){

    }

    public DirectedDFS(int[][] arr, int v){
        visted = new boolean[arr.length];
        count++;
        s.push(v);
        dfs(arr,v);
    }


    public void dfs(int[][] arr, int z){

        visted[z] = true;
        if(checkVertices(arr, z) == true){
            s.pop();
            return;
        }
        
        for(int i = 0; i < arr.length; i++){
            if(arr[z][i] == 1){
                if(visted[i] == false){
                    s.push(i);
                    dfs(arr,i);
                }
            }
        }
    }
   

    public boolean visted(int v){
        return visted[v];
    }

    public int rCount(){
        return count;
    }

    public Stack<Integer> print(){
        return s;
    }

    
}
