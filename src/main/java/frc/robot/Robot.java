/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
//import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Encoder enc = new Encoder(9, 8, true, Encoder.EncodingType.k4X);

  private Joystick driveStick=new Joystick(0);
  private Joystick opStick=new Joystick(1);

  private final I2C.Port i2cPort=I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor=new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher=new ColorMatch();
  private final Color kBlueTarget=ColorMatch.makeColor(0.143,0.427,0.429);
  private final Color kGreenTarget=ColorMatch.makeColor(0.197,0.561,0.240);
  private final Color kRedTarget=ColorMatch.makeColor(0.561,0.232,0.114);
  private final Color kYellowTarget=ColorMatch.makeColor(0.361,0.524,0.113);

  
  //private static VictorSP testMotor = new VictorSP(7);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    enc.reset();

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
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
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    Shooter.lastError=0.0;
  }

  /**
   * This function is called periodically during autonomous.
   */
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

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopInit(){
    //double teleStart=Timer.getFPGATimestamp();
    Shooter.setLastTimeStamp();
    Shooter.lastError=0.0;
  }
  @Override
  public void teleopPeriodic() {
    Drivetrain.move(driveStick.getX(), driveStick.getY());
    Intake.leftBin(opStick.getRawButton(7));
    Intake.rightBin(opStick.getRawButton(8));
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    System.out.println(enc.getRate()/6);
    //Shooter.shootAtRPM(1200, enc);  
    //testMotor.setSpeed(0.5);
  }
}
