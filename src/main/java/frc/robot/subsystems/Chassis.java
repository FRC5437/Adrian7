/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {

  private final AHRS m_gyro = new AHRS(Port.kMXP);

  private final WPI_TalonFX m_leftMaster = new WPI_TalonFX(Constants.LEFT_UPPER_DRIVE_ID);
  private final WPI_TalonFX m_leftSlave = new WPI_TalonFX(Constants.LEFT_LOWER_DRIVE_ID);
  private final WPI_TalonFX m_rightMaster = new WPI_TalonFX(Constants.RIGHT_UPPER_DRIVE_ID);
  private final WPI_TalonFX m_rightSlave = new WPI_TalonFX(Constants.RIGHT_LOWER_DRIVE_ID);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMaster, m_rightMaster);
  
  /**
   * Creates a new ExampleSubsystem.
   */
  public Chassis() {
    initializeDriveTrain();
    m_gyro.reset();
  }

  public void stop(){
    m_robotDrive.arcadeDrive(0.0, 0.0);
  }
    /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
      m_robotDrive.arcadeDrive(-1.0 * getEnhancedJoystickInput(fwd), getEnhancedJoystickInput(rot) * -1.0);
      SmartDashboard.putData("Gyro", m_gyro);
  }

  public void driveDistance(double distanceEncoderTicks){
    
  }

  public void driveAuto(double fwd, double rot){
    m_robotDrive.arcadeDrive(fwd, rot);
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    m_leftMaster.setSelectedSensorPosition(0);
    m_rightMaster.setSelectedSensorPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftMaster.getSelectedSensorPosition() + m_rightMaster.getSelectedSensorPosition()) / 2.0;
  }

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_robotDrive.setMaxOutput(maxOutput);
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    return Math.IEEEremainder(m_gyro.getAngle(), 360);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getRate();
  }

    /**
   * square the value while retaining sign and implement a deadband
   * 
   * @param rawValue - expected to represent a joystick axis value
   * @return modifiedValue which has retained the sign but squared the value and
   *         implemented a deadband for small rawValues
   */
  private double getEnhancedJoystickInput(final double rawValue) {
    final int sign = (int) Math.signum(rawValue);
    double modifiedValue = Math.pow(rawValue, 4.0);//rawValue * rawValue * rawValue * rawValue;
    //deadband
    if (modifiedValue < 0.05) {
        modifiedValue = 0.0;
    }

    return sign * modifiedValue;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void initializeDriveTrain() {
    m_leftMaster.configFactoryDefault();
    m_leftSlave.configFactoryDefault();
    m_rightMaster.configFactoryDefault();
    m_rightSlave.configFactoryDefault();

    m_leftSlave.follow(m_leftMaster);
    m_rightSlave.follow(m_rightMaster);

    m_rightSlave.setInverted(InvertType.FollowMaster);
    m_leftSlave.setInverted(InvertType.FollowMaster);

    // set the inversion for the drive motors at the TalonFX level
    //WARNING this means do not let the WPI Differential Drive invert the right side
    m_rightMaster.setInverted(false);
    m_leftMaster.setInverted(true);

    m_rightMaster.configOpenloopRamp(0.4);
    m_leftMaster.configOpenloopRamp(0.4);

    m_rightMaster.setSensorPhase(true);
    m_leftMaster.setSensorPhase(true);

    m_robotDrive.setRightSideInverted(false);

    m_robotDrive.setSafetyEnabled(false);

    //setup encoders
    m_leftMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.TALON_DEFAULT_PID_PORT, Constants.TALON_TIMEOUT_MS);
    m_rightMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.TALON_DEFAULT_PID_PORT, Constants.TALON_TIMEOUT_MS);

  }
}
