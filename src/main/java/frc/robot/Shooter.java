package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
//import edu.wpi.first.wpilibj.controller.PIDController;

public class Shooter {
    private static VictorSP testMotor = new VictorSP(7);
    
    private static double kP=0.5;
    private static double kI=0.5;
    private static double iLimit=1;
    private static double kD=0.1;
    public static double errorSum, lastTimeStamp, lastError=0;
    private static double rpm;

    //private PIDController pidTest=new PIDController(0.5,0.5,0.1);

    public static void setErrorSum(double d1){
        errorSum=d1;
    }

    public static void setLastTimeStamp(){
        lastTimeStamp=Timer.getFPGATimestamp();
    }

    public static void shootAtRPM(double wantRPM, Encoder enc){
        double dt=Timer.getFPGATimestamp() - Shooter.lastTimeStamp; //sets the difference between the current time and last recorded time
        rpm=(enc.getRate()/6); //sets current rpm: getRate() is degrees per second
        double error=wantRPM-rpm; //the difference between wanted and current rpm
        if(Math.abs(errorSum)<iLimit)
        errorSum+=error*dt*50;
        double errorRate=(error-lastError)/dt;
        double speed=(kP*error)+(kI*errorSum)+(kD*errorRate);
        testMotor.setSpeed(speed);
        lastTimeStamp=Timer.getFPGATimestamp();
        lastError=error;
    }
}