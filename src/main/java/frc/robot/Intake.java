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
    //private static final int leftID=3;
    //private static final int rightID=4;

    private static CANSparkMax leftIn=new CANSparkMax(3, MotorType.kBrushless);
    private static CANSparkMax rightIn=new CANSparkMax(4, MotorType.kBrushless);
    /*
    public static void leftBin(boolean b1){if(b1){leftIn.set(0.5);}else{leftIn.set(0);}}
    public static void rightBin(boolean b1){if(b1){rightIn.set(0.5);}else{rightIn.set(0);}}
    */
    //Actual code for Intake below

    private static VictorSP intakeWheels=new VictorSP(1);
    private static Spark intakeAim=new Spark(1);

    static Counter intakeCounter = new Counter(new DigitalInput(2));
    private static int position = 0;

    public static void intake(boolean b1){
        if(b1){
            intakeWheels.set(1);
        }else{
            intakeWheels.set(0);
        }
    }   

    public static void intakeRead(Joystick opStick){
        int povValue = opStick.getPOV(0);
        if(povValue == 0){
            position += intakeCounter.get();
        } else {
            position -= intakeCounter.get();
        }
        System.out.println(position);
        intakeCounter.reset();
    }

    public static void intakeReset(){
        intakeCounter.reset();
    }

    public static void intakeMove(double up){
        intakeAim.set(up);
    }

    public static void controlIntake(Joystick opStick) {
        int povValue = opStick.getPOV(0);
        if (povValue == 0) {
            intakeMove(1.0);
        } else if (povValue == 180) {
            intakeMove(-1.0); 
        } else if (povValue == -1) {
            intakeMove(0.0);
        }
    }
}