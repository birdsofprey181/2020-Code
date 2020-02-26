package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Relay;

public class Elevator{

    static VictorSP eleMotorL=new VictorSP(2);
    static VictorSP eleMotorR=new VictorSP(3);

    static Relay lock=new Relay(0);

    public static void controlEle(boolean b1, double d1){
        if(b1){
            eleMotorL.set(d1);
            eleMotorR.set(d1);
        }else{
            eleMotorL.set(0);
            eleMotorR.set(0);
        }
    }
    
    public static void controlLock(boolean b1){
        if(b1){
            lock.set(Relay.Value.kForward);
        }else{
            lock.set(Relay.Value.kOff);
        }
    }
}
