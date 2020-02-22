package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {
    private static final int turID=3;
    
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    public static double p,i,d,iz,ff,max,min,rotations;

    private static CANSparkMax turMotor;
    private static CANPIDController turPID;
    private static CANEncoder turEnc;

    public static void setTurMotor(){
        turMotor.restoreFactoryDefaults();
        turMotor=new CANSparkMax(turID, MotorType.kBrushless);
        turPID=turMotor.getPIDController();
        turEnc=turMotor.getEncoder();
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
        turPID.setP(kP);
        turPID.setI(kI);
        turPID.setD(kD);
        turPID.setIZone(kIz);
        turPID.setFF(kFF);
        turPID.setOutputRange(kMinOutput, kMaxOutput);
    }

    public static void putPIDOnSmart(){
        SmartDashboard.putNumber("Tur P Gain", kP);
        SmartDashboard.putNumber("Tur I Gain", kI);
        SmartDashboard.putNumber("Tur D Gain", kD);
        SmartDashboard.putNumber("Tur I Zone", kIz);
        SmartDashboard.putNumber("Tur Feed Forward", kFF);
        SmartDashboard.putNumber("Tur Max Output", kMaxOutput);
        SmartDashboard.putNumber("Tur Min Output", kMinOutput);
        SmartDashboard.putNumber("Tur Set Rotations", 0);
    }

    public static void getFromDash(){
        p=SmartDashboard.getNumber("Tur P Gain", 0);
        i=SmartDashboard.getNumber("Tur I Gain", 0);
        d=SmartDashboard.getNumber("Tur D Gain", 0);
        iz=SmartDashboard.getNumber("Tur I Zone", 0);
        ff=SmartDashboard.getNumber("Tur Feed Forward", 0);
        max=SmartDashboard.getNumber("Tur Max Output", 0);
        min=SmartDashboard.getNumber("Tur Min Output", 0);
        rotations = SmartDashboard.getNumber("Tur Set Rotations", 0);
    }

    public static void setFromDash(){
        if(p != kP){
            turPID.setP(p);
            kP = p; 
        }
        if(i != kI){
            turPID.setI(i);
            kI = i;
        }
        if(d != kD){
            turPID.setD(d);
            kD = d;
        }
        if(iz != kIz){
            turPID.setIZone(iz);
            kIz = iz;
        }
        if(ff != kFF){
            turPID.setFF(ff);
            kFF = ff;
        }
        if((max != kMaxOutput)||(min != kMinOutput))
        { 
          turPID.setOutputRange(min, max); 
          kMinOutput = min; kMaxOutput = max; 
        }
    }

    public static void controlTur(double pos){
        turPID.setReference(pos, ControlType.kPosition);
        
        SmartDashboard.putNumber("Tur SetPoint", rotations);
        SmartDashboard.putNumber("Tur ProcessVariable", turEnc.getPosition());
    }
}