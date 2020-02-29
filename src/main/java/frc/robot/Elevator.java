package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Relay;

public class Elevator{

     VictorSP eleMotorL=new VictorSP(2);
     VictorSP eleMotorR=new VictorSP(3);

     Relay lock=new Relay(0);

    public void controlEle(boolean b1, double d1){
        if(b1){
            eleMotorL.set(d1);
            eleMotorR.set(d1);
        }else{
            eleMotorL.set(0);
            eleMotorR.set(0);
        }
    }
    
    public void controlLock(boolean b1){
        if(b1){
            lock.set(Relay.Value.kForward);
        }else{
            lock.set(Relay.Value.kOff);
        }
    }
}
