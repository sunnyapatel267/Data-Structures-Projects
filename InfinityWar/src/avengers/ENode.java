package avengers;

public class ENode {
    int event;
    double EUValue;

    public ENode(){

    }
    public ENode(int event,int EUValue){
        this.event = event;
        this.EUValue = EUValue;
    }

    public void setEvent(int event){
        this.event = event;
    }

    public void setEUValue(int EUValue){
        this.EUValue = EUValue;
    }

    public int getEvent(){
        return event;
    }

    public double getEUValue(){
        return EUValue;
    }
}
