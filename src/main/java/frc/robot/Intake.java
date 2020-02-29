package frc.robot;

// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class Intake{
    //I don't know what this is below but its kinda cool and a template for later?
    //private  final int leftID=3;
    //private  final int rightID=4;
    /*
    private  CANSparkMax leftIn=new CANSparkMax(2, MotorType.kBrushless);
    private  CANSparkMax rightIn=new CANSparkMax(3, MotorType.kBrushless);
    
    public  void leftBin(boolean b1){if(b1){leftIn.set(0.5);}else{leftIn.set(0);}}
    public  void rightBin(boolean b1){if(b1){rightIn.set(0.5);}else{rightIn.set(0);}}
    */
    //Actual code for Intake below

    private VictorSP intakeWheels=new VictorSP(4);
    private Spark intakeWrist=new Spark(5);

    private VictorSP conveyMotor=new VictorSP(6);
    private VictorSP hopperMotor=new VictorSP(7);

     Counter intakeCounter = new Counter(new DigitalInput(2));
    private int position = 0;

    public void moveConvey(boolean b){
        if(b){
            conveyMotor.set(-0.75);
        }else{
            conveyMotor.set(0);
        }
    }

    public void moveHopper(boolean b){
        if(b){
            hopperMotor.set(-1);
        }else{
            hopperMotor.set(0);
        }
    }

    public void intakeWrist(boolean b1, double d1){
        if(b1){
            intakeWrist.set(d1);
        }else{
            intakeWrist.set(0);
        }
    }

    public void intakeWheels(boolean b1){
        if(b1){
            intakeWheels.set(-1);
        }else{
            intakeWheels.set(0);
        }
    }   

    public void intakeRead(Joystick opStick){
        int povValue = opStick.getPOV(0);
        if(povValue == 0){
            position += intakeCounter.get();
        } else {
            position -= intakeCounter.get();
        }
        System.out.println(position);
        intakeCounter.reset();
    }

    public void intakeReset(){
        intakeCounter.reset();
    }

    public void controlIntake(Joystick opStick) {
        int povValue = opStick.getPOV(0);
        if (povValue == 0) {
            intakeWrist(true, -1);
        } else if (povValue == 180) {
            intakeWrist(true, 1); 
        } else if (povValue == -1) {
            intakeWrist(false, 0.0);
        }
    }
}