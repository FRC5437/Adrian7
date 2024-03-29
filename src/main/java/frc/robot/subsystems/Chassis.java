/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {

  private final AHRS m_gyro = new AHRS(Port.kMXP);

  private final WPI_TalonFX m_rightMasterMotor = new WPI_TalonFX(Constants.LEFT_UPPER_DRIVE_ID);
  private final WPI_TalonFX m_rightSlaveMotor = new WPI_TalonFX(Constants.LEFT_LOWER_DRIVE_ID);
  private final WPI_TalonFX m_leftMasterMotor = new WPI_TalonFX(Constants.RIGHT_UPPER_DRIVE_ID);
  private final WPI_TalonFX m_leftSlaveMotor = new WPI_TalonFX(Constants.RIGHT_LOWER_DRIVE_ID);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_rightMasterMotor, m_leftMasterMotor);
  
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

  public boolean onTarget(){
    return m_rightMasterMotor.getClosedLoopError() < Constants.TALON_DRIVE_TOLERANCE;
  }

  public int convertLeftInchesToTarget(double distanceInches){
    return convertInchesToTarget(distanceInches, m_leftMasterMotor);
  }
  
  public int convertRightInchesToTarget(double distanceInches){
    return convertInchesToTarget(distanceInches, m_rightMasterMotor);
  }

  private int convertInchesToTarget(double distanceInches, WPI_TalonFX motor){
    int distanceInTicks = (int)(Constants.WHEEL_GEAR_RATIO * Constants.WHEEL_ROTATIONS_PER_INCH * distanceInches * Constants.K_UNITS_PER_REVOLUTION);
    return motor.getSelectedSensorPosition() + distanceInTicks;
  }

  public void driveAuto(int leftTargetPos, int rightTargetPos){
    m_rightMasterMotor.set(TalonFXControlMode.MotionMagic, leftTargetPos);
    m_leftMasterMotor.set(TalonFXControlMode.MotionMagic, rightTargetPos);
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    m_rightMasterMotor.setSelectedSensorPosition(0);
    m_leftMasterMotor.setSelectedSensorPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_rightMasterMotor.getSelectedSensorPosition() + m_leftMasterMotor.getSelectedSensorPosition()) / 2.0;
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
    if (modifiedValue < 0.04) {
        modifiedValue = 0.0;
    }

    return sign * modifiedValue;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void initializeDriveTrain() {
    m_rightMasterMotor.configFactoryDefault();
    m_rightSlaveMotor.configFactoryDefault();
    m_leftMasterMotor.configFactoryDefault();
    m_leftSlaveMotor.configFactoryDefault();

    m_rightSlaveMotor.follow(m_rightMasterMotor);
    m_leftSlaveMotor.follow(m_leftMasterMotor);

    m_leftSlaveMotor.setInverted(InvertType.FollowMaster);
    m_rightSlaveMotor.setInverted(InvertType.FollowMaster);

    // set the inversion for the drive motors at the TalonFX level
    //WARNING this means do not let the WPI Differential Drive invert the right side
    m_leftMasterMotor.setInverted(false);
    m_rightMasterMotor.setInverted(true);

    m_leftMasterMotor.configOpenloopRamp(0.4);
    m_rightMasterMotor.configOpenloopRamp(0.4);

    m_leftMasterMotor.setSensorPhase(true);
    m_rightMasterMotor.setSensorPhase(true);

    m_robotDrive.setRightSideInverted(false);
    m_robotDrive.setSafetyEnabled(false);

    //setup encoders
    m_rightMasterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.TALON_DEFAULT_PID_PORT, Constants.TALON_TIMEOUT_MS);
    m_leftMasterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.TALON_DEFAULT_PID_PORT, Constants.TALON_TIMEOUT_MS);

    m_rightMasterMotor.configNeutralDeadband(0.001, Constants.TALON_TIMEOUT_MS);
    m_leftMasterMotor.configNeutralDeadband(0.001, Constants.TALON_TIMEOUT_MS);

    setupTalonForMotionMagic(m_rightMasterMotor);
    setupTalonForMotionMagic(m_leftMasterMotor);
  }

  private void setupTalonForMotionMagic(WPI_TalonFX talon){
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.TALON_TIMEOUT_MS);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.TALON_TIMEOUT_MS);

		/* Set the peak and nominal outputs */
		talon.configNominalOutputForward(0, Constants.TALON_TIMEOUT_MS);
		talon.configNominalOutputReverse(0, Constants.TALON_TIMEOUT_MS);
		talon.configPeakOutputForward(1, Constants.TALON_TIMEOUT_MS);
		talon.configPeakOutputReverse(-1, Constants.TALON_TIMEOUT_MS);

		/* Set Motion Magic gains in slot0 - see documentation */
		talon.selectProfileSlot(Constants.TALON_SLOT_INDEX, Constants.TALON_PID_LOOP_INDEX);
		talon.config_kF(Constants.TALON_SLOT_INDEX, Constants.TALON_DRIVE_F, Constants.TALON_TIMEOUT_MS);
		talon.config_kP(Constants.TALON_SLOT_INDEX, Constants.TALON_DRIVE_P, Constants.TALON_TIMEOUT_MS);
		talon.config_kI(Constants.TALON_SLOT_INDEX, Constants.TALON_DRIVE_I, Constants.TALON_TIMEOUT_MS);
		talon.config_kD(Constants.TALON_SLOT_INDEX, Constants.TALON_DRIVE_D, Constants.TALON_TIMEOUT_MS);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(15000, Constants.TALON_TIMEOUT_MS);
    talon.configMotionAcceleration(6000, Constants.TALON_TIMEOUT_MS);
    
    //try middle of the road smoothing TODO add constant
    talon.configMotionSCurveStrength(4);

		/* Zero the sensor once on robot boot up */
		talon.setSelectedSensorPosition(0, Constants.TALON_PID_LOOP_INDEX, Constants.TALON_TIMEOUT_MS);
  }
}
