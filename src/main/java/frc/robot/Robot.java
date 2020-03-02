/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//FRC Birds Of Prey 181
//Gwen Miller 2020
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.util.Color;
//import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;

//import com.revrobotics.ColorMatch;
//import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Do Nothing";
  private static final String scoShoAuto ="Scoot and Shoot";
  private static final String moveAuto="Just Move";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  double delay=0.0;
  double driveTime=2.0;

  private Joystick driveStick=new Joystick(0);
  private Joystick opStick=new Joystick(1);

  Turret turret=new Turret();
  Launcher launcher=new Launcher();
  Intake intake=new Intake();
  Elevator elevator=new Elevator();
  Vision vision=new Vision();
  ElapsedTimer timer=new ElapsedTimer();
  ElapsedTimer hopTime=new ElapsedTimer();

  @SuppressWarnings("unused")
  private Camera cam = new Camera("stream");

  int i1=0;
  /*
  private final I2C.Port i2cPort=I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor=new ColorSensorV3(i2cPort);
  private final ColorMatch colorMatcher=new ColorMatch();
  private final Color kBlueTarget=ColorMatch.makeColor(0.143,0.427,0.429);
  private final Color kGreenTarget=ColorMatch.makeColor(0.197,0.561,0.240);
  private final Color kRedTarget=ColorMatch.makeColor(0.561,0.232,0.114);
  private final Color kYellowTarget=ColorMatch.makeColor(0.361,0.524,0.113);
  */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Do Nothing", kDefaultAuto);
    m_chooser.addOption("Scoot and Shoot", scoShoAuto);
    m_chooser.addOption("Just Move",moveAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    SmartDashboard.putNumber("Delay", delay);

    //colorMatcher.addColorMatch(kBlueTarget);
    //colorMatcher.addColorMatch(kGreenTarget);
    //colorMatcher.addColorMatch(kRedTarget);
    //colorMatcher.addColorMatch(kYellowTarget);
    
    turret.setTurMotor();
    /*
    turret.turMotor.restoreFactoryDefaults();
    turret.setPIDVariables();
    turret.setPIDController();
    turret.putPIDOnSmart();
    */
    launcher.setLaunMotor();
    /*
    launcher.launMotor.restoreFactoryDefaults();
    launcher.setPIDVariables();
    launcher.setPIDController();
    launcher.putPIDOnSmart();
    */
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

    //System.out.println(turret.turEnc.getPosition());
    //System.out.println("Z: "+opStick.getZ());
    //System.out.println("T: "+opStick.getThrottle());
    SmartDashboard.putNumber("Launcher RPM%", launcher.launEnc.getVelocity());
  }

  @Override
  public void autonomousInit() {
    //launcher.resetLaunEnc();
    //turret.resetTurEnc();
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    double kDelay=SmartDashboard.getNumber("Delay", 0.0);
    if(kDelay!=delay){
      delay=kDelay;
    }
    int hop=-1;
    timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    System.out.println("Auto selected: " + m_autoSelected);
    /*
    if(timer.hasElapsed()<4.0){
      Drivetrain.move(0.5,0);
    }
    */
    if(timer.hasElapsed()>delay&&timer.hasElapsed()<4.0+delay){
      intake.intakeWrist(true, -1);
      Drivetrain.move(0.5,0);
    }else if(timer.hasElapsed()<15.0){
      launcher.dumbLaunch2(true);
      intake.moveConvey(true);
      if(Math.floor(timer.hasElapsed())%2==0){
        intake.moveHopper(true);
      }
    }
    /*
    
    \
    switch (m_autoSelected) {
      case scoShoAuto:
        // TimeInterval intakeT=new TimeInterval(4.0, timer.hasElapsed());
        // TimeInterval ballT=new TimeInterval(15.0, timer.hasElapsed());
        // TimeInterval hop1=new TimeInterval(14.0, 15.0, timer.hasElapsed());
        System.out.println(timer.hasElapsed());
        if(timer.hasElapsed()<4.0){
          intake.intakeWrist(true, -1);
          Drivetrain.move(0.5,0);
        }else if(timer.hasElapsed()<15.0){
          launcher.dumbLaunch2(true);
          intake.moveConvey(true);
          if(timer.hasElapsed()>14.0&&timer.hasElapsed()<15.0){
          intake.moveHopper(true);
          }
        }
        break;
      case moveAuto:
        //TimeInterval moveT=new TimeInterval(2.0, timer.hasElapsed());
        if(timer.hasElapsed()<4.0){
          Drivetrain.move(0.5,0);
        }
        break;
      case kDefaultAuto:
      default:
        //do nothing :|
        break;
    }
    */
  }

  @Override
  public void teleopInit(){
    //launcher.resetLaunEnc();
    //turret.resetTurEnc();
  }
  @Override
  public void teleopPeriodic() {
    //turret.getFromDash();
    //launcher.getFromDash();

    elevator.controlEle(opStick.getRawButton(1), opStick.getY());
    intake.intakeRead(opStick);
    intake.controlIntake(opStick);
    //intake.intakeWrist(opStick.getRawButton(1), opStick.getY());
    intake.intakeWheels(opStick.getRawButton(7));

    /*
    if(opStick.getRawButton(2)){
      launcher.controlLaun((-opStick.getThrottle()/2)+0.5);
    }
    */

    launcher.dumbLaunch(opStick.getRawButton(4), (-opStick.getThrottle()/2)+0.5);

    if(opStick.getRawButton(5)){
      vision.ledOn();
      turret.autoTur();  
    }else{
      vision.ledOff();
      if(Math.abs(opStick.getZ())>0.5){
        turret.dumbTurn(-opStick.getZ());
      }else{
        turret.dumbTurn(0);
      }
    }
    

    /*
    if(opStick.getRawButton(10)){
      turret.turAlign();
    }
  
    turret.controlTur(opStick.getZ()*135);
    */

    intake.moveConvey(opStick.getRawButton(9));
    intake.moveHopper(opStick.getRawButton(11));

    Drivetrain.move(-driveStick.getY(), driveStick.getZ());

    elevator.controlLock(opStick.getRawButton(12));

    vision.lightToggle(opStick.getRawButton(10));

    launcher.dumbLaunch2(opStick.getRawButton(2));
  }

  @Override
  public void testPeriodic() {    
    System.out.println(opStick.getZ());
  }
}
