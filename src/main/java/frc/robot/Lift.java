package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Lift{
    private static VictorSP beltMotor=new VictorSP(2);

    public static void move(boolean b){
        if(b==true){
            beltMotor.set(1);
        }else{
            beltMotor.set(0);
        }
    }
}