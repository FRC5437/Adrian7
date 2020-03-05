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

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX m_intakePositionMotor = new WPI_TalonSRX(Constants.INTAKE_POSITION_MOTOR_ID);
  private final WPI_TalonSRX m_intakeBeltMotor = new WPI_TalonSRX(Constants.INTAKE_BELT_MOTOR_ID);

  private final DigitalInput m_intakeIndexerSensor = new DigitalInput(Constants.DIO_PORT_INTAKE_BALL_SENSOR);

  
  /**
   * Creates a new Intake.
   */
  public Intake() {

  }

  public boolean isLowered(){
    return m_intakePositionMotor.getSelectedSensorPosition() > 600;
  }

  public boolean isRaised(){
    return m_intakePositionMotor.getSelectedSensorPosition() < 300;
  }

  public void raiseIntake(){
    if(!isRaised()){
      m_intakePositionMotor.set(ControlMode.PercentOutput, 0.9);
    } else {
      m_intakePositionMotor.set(ControlMode.PercentOutput, 0.0);
    }
  }

  public void lowerIntake(){
    if(!isLowered()){
      m_intakePositionMotor.set(ControlMode.PercentOutput, -0.9);
    } else {
      m_intakePositionMotor.set(ControlMode.PercentOutput, 0.0);
    }
  }

  public void ingest(){
    m_intakeBeltMotor.set(ControlMode.PercentOutput, -0.9);
  }

  public void eject(){
    m_intakeBeltMotor.set(ControlMode.PercentOutput, 0.9);
  }

  public void drive(double power){
    if (Math.abs(power) < 0.1){
      power = 0;
    }
    m_intakeBeltMotor.set(ControlMode.PercentOutput, power);
  }

  public void stop(){
    m_intakeBeltMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean isEmpty(){
    return m_intakeIndexerSensor.get(); 
  }

  public boolean hasABall(){
    return !m_intakeIndexerSensor.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
