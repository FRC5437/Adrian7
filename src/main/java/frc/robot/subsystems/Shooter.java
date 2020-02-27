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
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(Constants.SHOOTER_MOTOR_ID);

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    initializeShooter();
  }

  public int getCurrentSpeed(){
    return m_shooterMotor.getSelectedSensorVelocity();
  }

  public void setSpeed(int targetSpeed){
    m_shooterMotor.set(ControlMode.Velocity, targetSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void initializeShooter() {
    //shooter init
    TalonFXConfiguration configs = new TalonFXConfiguration();
    /* select integ-sensor for PID0 (it doesn't matter if PID is actually used) */
    //public final static Gains kGains_Velocit  = new Gains( 0.1, 0.001, 5, 1023.0/20660.0,  300,  1.00);
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
    configs.slot0.kP = 0.1;
    configs.slot0.kI = 0.001;
    configs.slot0.kD = 5.0;
    configs.slot0.kF = 1023.0/20660.0;
    configs.slot0.integralZone = 300;
    configs.slot0.closedLoopPeakOutput = 1.0;

    m_shooterMotor.configAllSettings(configs);
		m_shooterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
		m_shooterMotor.setInverted(TalonFXInvertType.CounterClockwise);
    m_shooterMotor.setNeutralMode(NeutralMode.Coast);
  }
}
