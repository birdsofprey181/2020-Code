package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {
    private static final int turID=2;
    
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double p,i,d,iz,ff,max,min,rotations;

    public CANSparkMax turMotor;
    private CANPIDController turPID;
    private CANEncoder turEnc;

    NetworkTable vision_table = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry tarValid = vision_table.getEntry("tv");
    NetworkTableEntry tarOff = vision_table.getEntry("tx");

    public void setTurMotor(){
        turMotor=new CANSparkMax(turID, MotorType.kBrushless);
        turPID=turMotor.getPIDController();
        turEnc=turMotor.getEncoder();
    }

    public void resetTurEnc(){
        turEnc.setPosition(0);
    }

    public void setPIDVariables(){
        kP=6e-5; 
        kI=0;
        kD=0; 
        kIz=0; 
        kFF=0.000015; 
        kMaxOutput=1; 
        kMinOutput=-1;
    }

public void setPIDController(){
        turPID.setP(kP);
        turPID.setI(kI);
        turPID.setD(kD);
        turPID.setIZone(kIz);
        turPID.setFF(kFF);
        turPID.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void putPIDOnSmart(){
        SmartDashboard.putNumber("Tur P Gain", kP);
        SmartDashboard.putNumber("Tur I Gain", kI);
        SmartDashboard.putNumber("Tur D Gain", kD);
        SmartDashboard.putNumber("Tur I Zone", kIz);
        SmartDashboard.putNumber("Tur Feed Forward", kFF);
        SmartDashboard.putNumber("Tur Max Output", kMaxOutput);
        SmartDashboard.putNumber("Tur Min Output", kMinOutput);
        SmartDashboard.putNumber("Tur Set Rotations", 0);
    }

    public void getFromDash(){
        p=SmartDashboard.getNumber("Tur P Gain", 0);
        i=SmartDashboard.getNumber("Tur I Gain", 0);
        d=SmartDashboard.getNumber("Tur D Gain", 0);
        iz=SmartDashboard.getNumber("Tur I Zone", 0);
        ff=SmartDashboard.getNumber("Tur Feed Forward", 0);
        max=SmartDashboard.getNumber("Tur Max Output", 0);
        min=SmartDashboard.getNumber("Tur Min Output", 0);
        rotations = SmartDashboard.getNumber("Tur Set Rotations", 0);
    }

    public void setFromDash(){
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

    public void controlTur(double pos){
        turPID.setReference(pos, ControlType.kPosition);
        
        SmartDashboard.putNumber("Tur SetPoint", rotations);
        SmartDashboard.putNumber("Tur ProcessVariable", turEnc.getPosition());
    }

    public void turAlign(){
        //converts tx (degrees) to error (rotations)
        double error=tarOff.getDouble(0)/360;
        controlTur(error);
    }

    public void vision_align() {
        double output = 0;
        output = tarOff.getDouble(0) * p;
        output *= max;
        controlTur(output);
    }

    public void dumbTurn(double d1){
        turMotor.set(d1);
    }
}