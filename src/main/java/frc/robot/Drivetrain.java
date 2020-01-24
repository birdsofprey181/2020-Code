package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain{
    
    private static final int leftID = 1;
    private static final int rightID=2;

    private static CANSparkMax motorL=new CANSparkMax(leftID, MotorType.kBrushless);
    private static CANSparkMax motorR=new CANSparkMax(rightID, MotorType.kBrushless);

    private static DifferentialDrive drive=new DifferentialDrive(motorL, motorR);
    
    public static void move(double pwr, double tur){
        drive.arcadeDrive(pwr, tur);
    }

    public void resetEnc(int port){
        //reset encoder in x port
    }
    public double calcDist(){
        double distTrav=0;
        double encRot=0;
        double wheelCon=0;
        //set wheel constant to distance traveled per 1 encoder rotation
        //encRot = read encoder rotations;
        distTrav=encRot*wheelCon;
        return(distTrav);
    }
    public void pidExample(double totDist){
        //kP (%speed per error)
        //totDist (how far we want to travel)
        //motorOut(kP*(totDist-calcDist()));

    }
    public void pidExample(double totDist, double kP){
        //kP (%speed per error)
        //totDist (how far we want to travel)
        //motorOut(kP*(totDist-calcDist()));

    }
}