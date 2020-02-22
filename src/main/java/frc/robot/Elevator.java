package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Elevator{
    static int b1=-1;

    static VictorSP eleMotor=new VictorSP(1);

    public static void resetB1(){
        b1=-1;
    }
    public static void invertB1(boolean b2){
        if(b2){
            b1*=-1;
        }
    }

    public static void controlEle(double d1){
        if(b1>0){
            eleMotor.set(d1);
        }
    }
}
