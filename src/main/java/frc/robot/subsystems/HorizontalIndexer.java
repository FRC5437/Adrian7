/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HorizontalIndexer extends SubsystemBase {
  private final WPI_TalonSRX m_feederMotor = new WPI_TalonSRX(Constants.HORIZONTAL_FEEDER_MOTOR_ID);

  private final DigitalInput m_rearHorizontalIndexerSensor = new DigitalInput(Constants.DIO_PORT_REAR_HORIZONTAL_BALL_SENSOR);
  private final DigitalInput m_frontHorizontalIndexerSensor = new DigitalInput(Constants.DIO_PORT_FRONT_HORIZONTAL_BALL_SENSOR);


  /**
   * Creates a new HorizontalIndexer.
   */
  public HorizontalIndexer() {

  }

  public void activate(){
    m_feederMotor.set(ControlMode.PercentOutput, 0.5);
  }

  public void backup(){
    m_feederMotor.set(ControlMode.PercentOutput, -0.5);
  }

  public void stop(){
    m_feederMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean isEmpty(){
    return m_rearHorizontalIndexerSensor.get() && m_frontHorizontalIndexerSensor.get(); 
  }

  public boolean hasABall(){
    return !m_rearHorizontalIndexerSensor.get() || !m_frontHorizontalIndexerSensor.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
