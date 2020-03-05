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

public class Climber extends SubsystemBase {
  private final WPI_TalonSRX m_climberMotor = new WPI_TalonSRX(Constants.CLIMBER_MOTOR_ID);

  /**
   * Creates a new Climber.
   */
  public Climber() {

  }

  public void driveClimber(double power){
    m_climberMotor.set(ControlMode.PercentOutput, getEnhancedJoystickInput(power));
  }

  //TODO confirm direction of motor
  public void raiseTheHook(){
    m_climberMotor.set(ControlMode.PercentOutput, -0.7);
  }

  public void lowerTheHook(){
    m_climberMotor.set(ControlMode.PercentOutput, 0.7);
  }

  public void stop(){
    m_climberMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
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
}
