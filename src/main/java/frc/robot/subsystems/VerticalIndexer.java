/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class VerticalIndexer extends SubsystemBase {
  private final WPI_TalonSRX m_feederMotor = new WPI_TalonSRX(Constants.VERTICAL_FEEDER_MOTOR_ID);

  // NOTE these sensors read true when empty and false when detecting a ball
  private final DigitalInput m_verticalIndexerTopSensor = new DigitalInput(Constants.DIO_PORT_TOP_VERTICAL_BALL_SENSOR);
  private final DigitalInput m_verticalIndexerMidSensor = new DigitalInput(Constants.DIO_PORT_MID_VERTICAL_BALL_SENSOR);
  private final DigitalInput m_verticalIndexerBottomSensor = new DigitalInput(Constants.DIO_PORT_BOTTOM_VERTICAL_BALL_SENSOR);
  private final DigitalInput m_verticalIndexerRearHorizontalSensor = new DigitalInput(Constants.DIO_PORT_REAR_HORIZONTAL_BALL_SENSOR);

  /**
   * Creates a new VerticalIndexer.
   */
  public VerticalIndexer() {
    initializeMotorControl();
  }

  public void drive(double power){
    m_feederMotor.set(ControlMode.PercentOutput, power);
  }

  public void activate(){
    m_feederMotor.set(ControlMode.PercentOutput, 0.50);
  }

  public void backup(){
    m_feederMotor.set(ControlMode.PercentOutput, -0.50);
  }

  public void stop(){
    m_feederMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean isEmpty(){
    return m_verticalIndexerMidSensor.get() && 
        m_verticalIndexerBottomSensor.get() && 
        m_verticalIndexerTopSensor.get() && 
        m_verticalIndexerRearHorizontalSensor.get(); 
  }

  public boolean hasABall(){
    return !isEmpty();  
  }

  public boolean ballAtBottomSensor(){
    return !m_verticalIndexerBottomSensor.get();
  }

  public boolean ballAtMidSensor(){
    return !m_verticalIndexerMidSensor.get() || !m_verticalIndexerTopSensor.get();
  }

  public int getCurrentPosition(){
    return m_feederMotor.getSelectedSensorPosition(Constants.TALON_PID_LOOP_INDEX);
  }

  public boolean onTarget(){
    return Math.abs(m_feederMotor.getClosedLoopError()) < Constants.TALON_INDEXER_TOLERANCE;
  }

  public void advanceBall(int targetPosition){
    m_feederMotor.set(ControlMode.MotionMagic, targetPosition);
  }

  private void initializeMotorControl() {
    m_feederMotor.configFactoryDefault();
    m_feederMotor.setSensorPhase(true);
    m_feederMotor.setInverted(false);
    m_feederMotor.configOpenloopRamp(0.4);
    //setup encoders
    m_feederMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.TALON_DEFAULT_PID_PORT, Constants.TALON_TIMEOUT_MS);
    m_feederMotor.configNeutralDeadband(0.001, Constants.TALON_TIMEOUT_MS);
    setupTalonForMotionMagic(m_feederMotor);
  }

  private void setupTalonForMotionMagic(WPI_TalonSRX talon){
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
		talon.config_kP(Constants.TALON_SLOT_INDEX, Constants.TALON_INDEXER_P, Constants.TALON_TIMEOUT_MS);
		talon.config_kI(Constants.TALON_SLOT_INDEX, Constants.TALON_DRIVE_I, Constants.TALON_TIMEOUT_MS);
		talon.config_kD(Constants.TALON_SLOT_INDEX, Constants.TALON_DRIVE_D, Constants.TALON_TIMEOUT_MS);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(15000, Constants.TALON_TIMEOUT_MS);
    talon.configMotionAcceleration(6000, Constants.TALON_TIMEOUT_MS);
    
    //try middle of the road smoothing TODO add constant
    talon.configMotionSCurveStrength(1);

		/* Zero the sensor once on robot boot up */
    talon.setSelectedSensorPosition(0, Constants.TALON_PID_LOOP_INDEX, Constants.TALON_TIMEOUT_MS);
    talon.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
