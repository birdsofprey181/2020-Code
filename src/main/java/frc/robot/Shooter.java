package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;

public class Shooter {
    private static VictorSP testMotor = new VictorSP(7);
    
    private static double kP=0.0001;
    private static double rpm;

    public static void shootAtRPM(double wantRPM, Encoder enc){
        rpm=(enc.getRate()/6);
        System.out.println(rpm);
        double speed=kP*(wantRPM-rpm);
        testMotor.setSpeed(speed); 
    }
}