// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final CANSparkMax m_frontLeftMotor = new CANSparkMax(1, MotorType.kBrushed);
  private final CANSparkMax m_backLeftMotor = new CANSparkMax(2, MotorType.kBrushed);
  private final CANSparkMax m_frontRightMotor = new CANSparkMax(4, MotorType.kBrushed);
  private final CANSparkMax m_backRightMotor = new CANSparkMax(3, MotorType.kBrushed);

  //m_frontLeftMotor.addFollower(m_backLeftMotor);
  
  private final DifferentialDrive m_robotDriveLeft =
      new DifferentialDrive(m_frontLeftMotor::set, m_backLeftMotor::set);

  private final DifferentialDrive m_robotDriveRight = 
      new DifferentialDrive(m_frontRightMotor::set, m_backRightMotor::set);
  
  //make an xbox controller
  private XboxController m_Controller;
  //private final Joystick m_stick = new Joystick(0);

  public Robot() {
    SendableRegistry.addChild(m_robotDriveLeft, m_frontLeftMotor);
    SendableRegistry.addChild(m_robotDriveRight, m_frontRightMotor);

    SendableRegistry.addChild(m_robotDriveLeft, m_backLeftMotor);
    SendableRegistry.addChild(m_robotDriveRight, m_backRightMotor);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    //m_frontRightMotor.setInverted(true);
    //m_frontLeftMotor.setInverted(true);
    //m_backRightMotor.setInverted(true);
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_Controller = new XboxController(0);
    //m_Controller = new XboxController(0);
    //nullpointerexception
    m_robotDriveLeft.arcadeDrive(m_Controller.getLeftY()/1.5, m_Controller.getLeftY()/1.5);
    m_robotDriveRight.arcadeDrive(m_Controller.getRightY()/1.5, -m_Controller.getRightY()/1.5);
    //m_robotDrive2.arcadeDrive(, kDefaultPeriod);

    //Hello World
  }
}
