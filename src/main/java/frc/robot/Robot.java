// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final PWMSparkMax m_frontLeftMotor = new PWMSparkMax(0);
  private final PWMSparkMax m_backLeftMotor = new PWMSparkMax(1);
  private final PWMSparkMax m_frontRightMotor = new PWMSparkMax(2);
  private final PWMSparkMax m_backRightMotor = new PWMSparkMax(3);

  //m_frontLeftMotor.addFollower(m_backLeftMotor); 
  //m_frontRightMotor.addFollower(m_backRigthMotor);
  
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_frontLeftMotor::set, m_frontRightMotor::set);

  private final DifferentialDrive m_robotDrive2 = 
      new DifferentialDrive(m_backLeftMotor::set, m_backRightMotor::set);
  
  //make an xbox controller
  private final Joystick m_stick = new Joystick(0);

  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_frontLeftMotor);
    SendableRegistry.addChild(m_robotDrive, m_frontRightMotor);

    SendableRegistry.addChild(m_robotDrive2, m_backLeftMotor);
    SendableRegistry.addChild(m_robotDrive2, m_backRightMotor);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_frontRightMotor.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    
    //allow to use xbox controller
    m_robotDrive.arcadeDrive(-m_stick.getY(), -m_stick.getX());
    //m_robotDrive2.arcadeDrive(, kDefaultPeriod);
  }
}
