package avengers;
    
public class Node {
    int num;
    double func;

    public Node(){

    }
    public Node(int num,double func){
        this.num = num;
        this.func = func;
    }

    public void setNum(int num){
        this.num = num;
    }

    public void setFunc(double func){
        this.func = func;
    }

    public int getNum(){
        return num;
    }

    public double getFunc(){
        return func;
    }
}
