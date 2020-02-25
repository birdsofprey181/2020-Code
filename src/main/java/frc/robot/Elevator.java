package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Elevator{

    static VictorSP eleMotorL=new VictorSP(2);
    static VictorSP eleMotorR=new VictorSP(3);

    public static void controlEle(boolean b1, double d1){
        if(b1){
            eleMotorL.set(d1);
            eleMotorR.set(d1);
        }else{
            eleMotorL.set(0);
            eleMotorR.set(0);
        }
    }
}
