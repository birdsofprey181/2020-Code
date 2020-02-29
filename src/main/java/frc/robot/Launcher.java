package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Launcher {
    private final int launID=1;
    
    //public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput,maxRPM;
    public double p,i,d,iz,ff,max,min;

    public CANSparkMax launMotor;
    private CANPIDController launPID;
    public CANEncoder launEnc;
    private double power;

    public void setLaunMotor(){
        launMotor=new CANSparkMax(1, MotorType.kBrushless);
        launMotor.setIdleMode(IdleMode.kCoast);
        launMotor.setSmartCurrentLimit(80);
        launPID=launMotor.getPIDController();
        //launPID.setP(0.000135);
        //launPID.setD(0.0005);
        launPID.setP(0);
        launPID.setD(0);
        launPID.setFF(0.000158);
        launPID.setDFilter(1);
        launEnc=launMotor.getEncoder();
    }

    /*
    public void resetLaunEnc(){
        launEnc.setPosition(0);
    }

    public void setPIDVariables(){
        kP=6e-5; 
        kI=0;
        kD=0; 
        kIz=0; 
        kFF=0.000015; 
        kMaxOutput=1; 
        kMinOutput=-1;
        maxRPM=5800;
    }

    public void setPIDController(){
        launPID.setP(kP);
        launPID.setI(kI);
        launPID.setD(kD);
        launPID.setIZone(kIz);
        launPID.setFF(kFF);
        launPID.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void putPIDOnSmart(){
        SmartDashboard.putNumber("Launcher P Gain", kP);
        SmartDashboard.putNumber("Launcher I Gain", kI);
        SmartDashboard.putNumber("Launcher D Gain", kD);
        SmartDashboard.putNumber("Launcher I Zone", kIz);
        SmartDashboard.putNumber("Launcher Feed Forward", kFF);
        SmartDashboard.putNumber("Launcher Max Output", kMaxOutput);
        SmartDashboard.putNumber("Launcher Min Output", kMinOutput);
    }

    public void getFromDash(){
        p=SmartDashboard.getNumber("Launcher P Gain", 0);
        i=SmartDashboard.getNumber("Launcher I Gain", 0);
        d=SmartDashboard.getNumber("Launcher D Gain", 0);
        iz=SmartDashboard.getNumber("Launcher I Zone", 0);
        ff=SmartDashboard.getNumber("Launcher Feed Forward", 0);
        max=SmartDashboard.getNumber("Launcher Max Output", 0);
        min=SmartDashboard.getNumber("Launcher Min Output", 0);
    }

    public void setFromDash(){
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

    public void controlLaun(double rpmPer){
        double setPoint=rpmPer*maxRPM;
        launPID.setReference(setPoint, ControlType.kVelocity);
        
        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("ProcessVariable", launEnc.getPosition());
    }
    */

    public void dumbLaunch(boolean b1, double d1){
        double launPow=3000+(d1*2000);
        if(b1){
            launPID.setReference(-launPow, ControlType.kVelocity);
            System.out.println("Launcher Power: "+launPow);
            //launMotor.
        }else{
            launPID.setReference(0, ControlType.kDutyCycle);
        }
    }

}