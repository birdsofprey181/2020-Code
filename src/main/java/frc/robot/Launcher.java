package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Launcher {
    private static final int launID=3;
    
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    public static double p,i,d,iz,ff,max,min;

    private static CANSparkMax launMotor;
    private static CANPIDController launPID;
    private static CANEncoder launEnc;

    public static void setLaunMotor(){
        launMotor.restoreFactoryDefaults();
        launMotor=new CANSparkMax(launID, MotorType.kBrushless);
        launPID=launMotor.getPIDController();
        launEnc=launMotor.getEncoder();
    }

    public static void setPIDVariables(){
        kP=6e-5; 
        kI=0;
        kD=0; 
        kIz=0; 
        kFF=0.000015; 
        kMaxOutput=1; 
        kMinOutput=-1;
        maxRPM=5700;
    }

    public static void setPIDController(){
        launPID.setP(kP);
        launPID.setI(kI);
        launPID.setD(kD);
        launPID.setIZone(kIz);
        launPID.setFF(kFF);
        launPID.setOutputRange(kMinOutput, kMaxOutput);
    }

    public static void putPIDOnSmart(){
        SmartDashboard.putNumber("Launcher P Gain", kP);
        SmartDashboard.putNumber("Launcher I Gain", kI);
        SmartDashboard.putNumber("Launcher D Gain", kD);
        SmartDashboard.putNumber("Launcher I Zone", kIz);
        SmartDashboard.putNumber("Launcher Feed Forward", kFF);
        SmartDashboard.putNumber("Launcher Max Output", kMaxOutput);
        SmartDashboard.putNumber("Launcher Min Output", kMinOutput);
    }

    public static void getFromDash(){
        p=SmartDashboard.getNumber("Launcher P Gain", 0);
        i=SmartDashboard.getNumber("Launcher I Gain", 0);
        d=SmartDashboard.getNumber("Launcher D Gain", 0);
        iz=SmartDashboard.getNumber("Launcher I Zone", 0);
        ff=SmartDashboard.getNumber("Launcher Feed Forward", 0);
        max=SmartDashboard.getNumber("Launcher Max Output", 0);
        min=SmartDashboard.getNumber("Launcher Min Output", 0);
    }

    public static void setFromDash(){
        if(p != kP){
            launPID.setP(p);
            kP = p; 
        }
        if(i != kI){
            launPID.setI(i);
            kI = i;
        }
        if(d != kD){
            launPID.setD(d);
            kD = d;
        }
        if(iz != kIz){
            launPID.setIZone(iz);
            kIz = iz;
        }
        if(ff != kFF){
            launPID.setFF(ff);
            kFF = ff;
        }
        if((max != kMaxOutput)||(min != kMinOutput))
        { 
          launPID.setOutputRange(min, max); 
          kMinOutput = min; kMaxOutput = max; 
        }
    }

    public static void controlLaun(double rpmPer){
        double setPoint=rpmPer*maxRPM;
        launPID.setReference(setPoint, ControlType.kVelocity);
        
        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("ProcessVariable", launEnc.getPosition());
    }
}