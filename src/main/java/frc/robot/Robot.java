/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//Gwen Miller 2020
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Joystick driveStick=new Joystick(0);
  private Joystick opStick=new Joystick(1);

  private final I2C.Port i2cPort=I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor=new ColorSensorV3(i2cPort);
  private final ColorMatch colorMatcher=new ColorMatch();
  private final Color kBlueTarget=ColorMatch.makeColor(0.143,0.427,0.429);
  private final Color kGreenTarget=ColorMatch.makeColor(0.197,0.561,0.240);
  private final Color kRedTarget=ColorMatch.makeColor(0.561,0.232,0.114);
  private final Color kYellowTarget=ColorMatch.makeColor(0.361,0.524,0.113);

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Do Nothing", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    colorMatcher.addColorMatch(kBlueTarget);
    colorMatcher.addColorMatch(kGreenTarget);
    colorMatcher.addColorMatch(kRedTarget);
    colorMatcher.addColorMatch(kYellowTarget);
    
    Turret.setTurMotor();
    Turret.turMotor.restoreFactoryDefaults();
    Turret.setPIDVariables();
    Turret.setPIDController();
    Turret.putPIDOnSmart();
    
    Launcher.setLaunMotor();
    Launcher.launMotor.restoreFactoryDefaults();
    Launcher.setPIDVariables();
    Launcher.setPIDController();
    Launcher.putPIDOnSmart();
  }

  @Override
  public void robotPeriodic() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);

    //SmartDashboard.putNumber("Launcher RPM%", rpm);
  }

  @Override
  public void autonomousInit() {
    Launcher.resetLaunEnc();
    Turret.resetTurEnc();
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    if(Turret.lockedOn()==false){
      Turret.turnTur(0.0, 0.0);
    }else{
      //double rpm=retireving distance from limelight and comparing to rpm needed for that speed;
      //TODO: measure rpm vs distance launched and create equation
      Shooter.shootAtRPM(1200, enc);
    }
  }

  @Override
  public void teleopInit(){
  }
  @Override
  public void teleopPeriodic() {
    Turret.getFromDash();
    Launcher.getFromDash();

    Elevator.controlEle(opStick.getRawButton(1), opStick.getY());
    Intake.intakeWrist(opStick.getRawButton(1), opStick.getY());
    Intake.intakeWheels(opStick.getRawButton(7), 1);

    if(opStick.getRawButton(2)){
      Launcher.controlLaun((opStick.getThrottle()/2)+0.5);
    }

    Turret.controlTur(opStick.getZ()*135);

    Intake.moveConvey(opStick.getRawButton(9));
    Intake.moveHopper(opStick.getRawButton(11));

    Drivetrain.move(driveStick.getY(), driveStick.getZ());
  }

  @Override
  public void testPeriodic() {    
    //Turret.getFromDash();
    //Turret.controlTur(pos);
    Launcher.getFromDash();
    Launcher.controlLaun(0.5);
  }
}
