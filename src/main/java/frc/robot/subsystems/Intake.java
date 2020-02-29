/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX m_intakePositionMotor = new WPI_TalonSRX(Constants.INTAKE_POSITION_MOTOR_ID);
  private final WPI_TalonSRX m_intakeBeltMotor = new WPI_TalonSRX(Constants.INTAKE_BELT_MOTOR_ID);
  
  /**
   * Creates a new Intake.
   */
  public Intake() {

  }

  public void raiseIntake(){
    //TODO use position encoders
    
  }

  public void lowerIntake(){
    //TODO use position encoders

  }

  public void ingest(){
    m_intakeBeltMotor.set(ControlMode.PercentOutput, -0.9);
  }

  public void eject(){
    m_intakeBeltMotor.set(ControlMode.PercentOutput, 0.9);
  }

  public void stop(){
    m_intakeBeltMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
