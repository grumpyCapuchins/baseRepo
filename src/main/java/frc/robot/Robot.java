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

  //launcher spark max objects
  private final CANSparkMax m_topLaunchMotor = new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax m_bottomLaunchMotor = new CANSparkMax(5, MotorType.kBrushless);

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
  public Robot() 
  {
    //adds child to sendable Registry (Whatever that means)
    SendableRegistry.addChild(m_robotDrive, m_frontLeftMotor);
    SendableRegistry.addChild(m_robotDrive, m_frontRightMotor);
  }

  /**
   * Initialization class that allows for set up for later code
   */
  @Override
  public void robotInit() 
  {
    // Followers (Replacment for motor controller groups)
    m_backLeftMotor.follow(m_frontLeftMotor);
    m_backRightMotor.follow(m_frontRightMotor);

    // inverts Right side of robot
    m_frontRightMotor.setInverted(true);
    m_backRightMotor.setInverted(true);

    //initializes Controller Object
    m_Controller = new XboxController(0);

    // starts live view from robot webcam
    CameraServer.startAutomaticCapture();
  }

  /**
   * Autonomous initialization class that creates objects or resets certain variables
   * for autonomous Periodic
   */
  @Override
  public void autonomousInit() 
  {
    // resets timer for autonomous
    m_Timer.restart();
  }

  /**
   * Program that is ran when the robot is in "Autonomous Mode"
   */
  @Override
  public void autonomousPeriodic() 
  {
    //if statment for auto top motor warm-up
    if (m_Timer.get() <= 5.0) 
    {
      //sets top motor speed
      m_topLaunchMotor.set(-1.0);
    // Elif statment for auot bottom motor starting
    } else if (m_Timer.get() > 5.0 && m_Timer.get() < 10.0) 
    {
      //sets bottom motor speed
      m_bottomLaunchMotor.set(-1.0);
    // ELif for auto back out
    } else if (m_Timer.get() > 10.0 && m_Timer.get() < 14.0) 
    {
      //both motors reverse
      m_robotDrive.tankDrive(0.5, 0.5);
    } else {
      // stops all autonomous operations
      m_robotDrive.stopMotor();
      m_topLaunchMotor.stopMotor();
      m_bottomLaunchMotor.stopMotor();
    }
  }
  
  /**
   * Program that is ran when robot is in "Teleoperated Mode"
   */
  @Override
  public void teleopPeriodic() 
  {
    /*
    Drive with arcade drive.
    That means that the Y axis drives forward
    and backward, and the X turns left and right.
    */
    if (m_Controller.getLeftY() > 0.0 || m_Controller.getRightX() > 0.0 || m_Controller.getLeftY() < 0.0 || m_Controller.getRightX() < 0.0)
    {
      m_robotDrive.arcadeDrive(m_Controller.getLeftY(), m_Controller.getRightX());
    }
    /**
     * B button for top motor warm-up
     * right bumper for firing the note
     * left bumper for intake
     */
    
    //Prepares top motor for firing
    if (m_Controller.getBButtonPressed() == true)
    {
      m_topLaunchMotor.set(1.0);
    } else if (m_Controller.getBButtonReleased() == true) 
    {
      m_topLaunchMotor.stopMotor();
    }

    //activate bottom motor for active firing
    if (m_Controller.getRightBumperPressed() == true)
    {
      m_bottomLaunchMotor.set(1.0);
    } else if (m_Controller.getRightBumperReleased() == true) 
    {
      m_bottomLaunchMotor.stopMotor();
    }

    //if statement for Reciever
    if (m_Controller.getLeftBumperPressed() == true)
    {
      m_topLaunchMotor.set(-0.5);
      m_bottomLaunchMotor.set(-0.2);
    } else if (m_Controller.getLeftBumperReleased() == true) 
    {
      m_topLaunchMotor.stopMotor();
      m_bottomLaunchMotor.stopMotor();
    }

  }
}
