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

public class VerticalIndexer extends SubsystemBase {
  private final WPI_TalonSRX m_feederMotor = new WPI_TalonSRX(Constants.VERTICAL_FEEDER_MOTOR_ID);

  // NOTE these sensors read true when empty and false when detecting a ball
  private final DigitalInput m_verticalIndexerTopSensor = new DigitalInput(Constants.DIO_PORT_TOP_VERTICAL_BALL_SENSOR);
  private final DigitalInput m_verticalIndexerBottomSensor = new DigitalInput(Constants.DIO_PORT_BOTTOM_VERTICAL_BALL_SENSOR);

  Intake m_intake;
  HorizontalIndexer m_horizontalIndexer;

  /**
   * Creates a new VerticalIndexer.
   */
  public VerticalIndexer(HorizontalIndexer horizontalIndexer, Intake intake) {
    m_intake = intake;
    m_horizontalIndexer = horizontalIndexer;
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
    return m_verticalIndexerTopSensor.get() && m_verticalIndexerBottomSensor.get() && m_horizontalIndexer.isEmpty() && m_intake.isEmpty(); 
  }

  public boolean hasABall(){
    return !m_verticalIndexerTopSensor.get() || !m_verticalIndexerBottomSensor.get() ||  m_horizontalIndexer.hasABall() || m_intake.hasABall();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
