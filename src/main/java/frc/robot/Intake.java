package frc.robot;

// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake{
    private static final int leftID=3;
    private static final int rightID=4;

    private static CANSparkMax leftIn=new CANSparkMax(leftID, MotorType.kBrushless);
    private static CANSparkMax rightIn=new CANSparkMax(rightID, MotorType.kBrushless);

    public static void leftBin(boolean b1){if(b1){leftIn.set(0.5);}}
    public static void rightBin(boolean b1){if(b1){rightIn.set(0.5);}}
}