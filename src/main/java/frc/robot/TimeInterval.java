package frc.robot;

public class TimeInterval{
    double startTime;
    double endTime;
    double curTime;
    boolean b1;

    public TimeInterval(double d2, double d3){
        endTime=d2;
        curTime=d3;
        b1=(curTime<endTime);
    }

    public TimeInterval(double d1, double d2, double d3){
        startTime=d1;
        endTime=d2;
        curTime=d3;
        b1=((curTime>startTime)&&(curTime<endTime));
    }
    public boolean isInterval(){
        return(b1);
    }
}