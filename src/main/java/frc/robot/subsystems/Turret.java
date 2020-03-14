/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  private final double m_kP = 0.07;
  private final double m_tolerance = 0.5;
  private final WPI_TalonSRX m_turretMotor = new WPI_TalonSRX(Constants.TURRET_MOTOR_ID);
  private final NetworkTableInstance m_tableInstance;
  private NetworkTable m_limelight;

  /**
   * Creates a new Turret.
   */
  public Turret() {
    m_tableInstance = NetworkTableInstance.getDefault();
    m_limelight = m_tableInstance.getTable("limelight-shooter");
    m_turretMotor.configFactoryDefault();
    m_turretMotor.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.Analog, Constants.TALON_PID_LOOP_INDEX, Constants.TALON_TIMEOUT_MS);
    m_turretMotor.setSelectedSensorPosition(0);
  }

  public double getTargetArea(){
    return m_limelight.getEntry("ta").getDouble(0.0);
  }

  public boolean onTarget(){
    double degreesFromTarget = m_limelight.getEntry("tx").getDouble(0.0);
    boolean withinTolerance = Math.abs(degreesFromTarget) < m_tolerance;
    return (withinTolerance);
  }

  public void trackCameraTarget(){
    //TODO ensure limits to rotation are enforced! Ideally limit switches - fallback plan is to zero encoder facing front and limit the range
    //boolean foundTarget = m_limelight.getEntry("tv").getBoolean(false);
    double degreesFromTarget = m_limelight.getEntry("tx").getDouble(0.0);
    //if (foundTarget){
      if (Math.abs(degreesFromTarget) > m_tolerance ){
        m_turretMotor.set(ControlMode.PercentOutput, degreesFromTarget * m_kP);
      } else {
        m_turretMotor.set(ControlMode.PercentOutput, 0.0);
      }
   // } else {
   //   m_turretMotor.set(ControlMode.PercentOutput, 0.0);
   // }
  }

  public void stop(){
    m_turretMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void rotate(double rotation_rate){
    m_turretMotor.set(ControlMode.PercentOutput, getEnhancedJoystickInput(rotation_rate));
  }

  /**
   * square the value while retaining sign and implement a deadband
   * 
   * @param rawValue - expected to represent a joystick axis value
   * @return modifiedValue which has retained the sign but squared the value and
   *         implemented a deadband for small rawValues
   */
  private double getEnhancedJoystickInput(double rawValue) {
    int sign = (int) Math.signum(rawValue);
    double modifiedValue = Math.pow(rawValue, 2.0);//rawValue * rawValue * rawValue * rawValue;
    //deadband
    if (modifiedValue < 0.04) {
        modifiedValue = 0.0;
    }
    double enhancedValue = sign * modifiedValue;
    return enhancedValue;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
