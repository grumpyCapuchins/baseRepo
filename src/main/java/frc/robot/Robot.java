// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
//import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  //Constructs and initializes spark max objects
  private final CANSparkBase m_frontLeftMotor = new CANSparkMax(2, MotorType.kBrushed);
  private final CANSparkMax m_backLeftMotor = new CANSparkMax(1, MotorType.kBrushed);
  private final CANSparkBase m_frontRightMotor = new CANSparkMax(3, MotorType.kBrushed);
  private final CANSparkMax m_backRightMotor = new CANSparkMax(4, MotorType.kBrushed);

  //Constructs and initializes a Timer Object
  private final Timer m_Timer = new Timer();
  
  //constructs and initializes a differential drive object
  private final DifferentialDrive m_robotDrive 
    = new DifferentialDrive(m_frontLeftMotor::set, m_frontRightMotor::set);
  
  //constructs an xbox controller object
  private XboxController m_Controller;

  /**
   * Robot class that stores all information that needs to be transfered to the super class
   */
  public Robot() {
    //adds child to sendable Registry (Whatever that means)
    SendableRegistry.addChild(m_robotDrive, m_frontLeftMotor);
    SendableRegistry.addChild(m_robotDrive, m_frontRightMotor);
  }

  /**
   * Initialization class that allows for set up for later code
   */
  @Override
  public void robotInit() {
    // Followers (Replacment for motor controller groups)
    m_backLeftMotor.follow(m_frontLeftMotor);
    m_backRightMotor.follow(m_frontRightMotor);

    // inverts Right side of robot
    m_frontRightMotor.setInverted(true);
    m_backRightMotor.setInverted(true);

    // starts live view from robot webcam
    CameraServer.startAutomaticCapture();
  }

  /**
   * Autonomous initialization class that creates objects or resets certain variables
   * for autonomous Periodic
   */
  @Override
  public void autonomousInit() {
    // resets timer for autonomous
    m_Timer.restart();
  }

  /**
   * Program that is ran when the robot is in "Autonomous Mode"
   */
  @Override
  public void autonomousPeriodic() {
    // if statment to show how long robot should run for 2 seconds (timer loop)
    if (m_Timer.get() < 2.0) {
      // tankDrive command that allows robot to run at 0.5 speed on each side
      m_robotDrive.tankDrive(-0.5, -0.555);
    } else {
      // stops robot
      m_robotDrive.stopMotor();
    }
  }
  
  /**
   * Program that is ran when robot is in "Teleoperated Mode"
   */
  @Override
  public void teleopPeriodic() {
    //initializes Controller Object
    m_Controller = new XboxController(0);

    /*
    Drive with arcade drive.
    That means that the Y axis drives forward
    and backward, and the X turns left and right.
    */
    m_robotDrive.tankDrive(m_Controller.getLeftY(), m_Controller.getRightX());
  }
}
