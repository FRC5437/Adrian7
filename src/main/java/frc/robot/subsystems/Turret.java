/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  private final double m_kP = 2.0;
  private final WPI_TalonSRX m_turretMotor = new WPI_TalonSRX(Constants.TURRET_MOTOR_ID);
  private final NetworkTableInstance m_tableInstance;
  private NetworkTable m_limelight;


  /**
   * Creates a new Turret.
   */
  public Turret() {
    m_tableInstance = NetworkTableInstance.getDefault();
     m_limelight = m_tableInstance.getTable("limelight");
     
  }

  public double getTargetArea(){
    return m_limelight.getEntry("ta").getDouble(0.0);
  }

  public void trackCameraTarget(){
    //TODO ensure limits to rotation are enforced!
    boolean foundTarget = m_limelight.getEntry("tv").getBoolean(false);
    double degreesFromTarget = m_limelight.getEntry("tx").getDouble(0.0);
    if (foundTarget){
      if (Math.abs(degreesFromTarget) > 1.0 ){
        m_turretMotor.set(ControlMode.PercentOutput, degreesFromTarget * m_kP);
      } else {
        m_turretMotor.set(ControlMode.PercentOutput, 0.0);
      }
    } else {
      m_turretMotor.set(ControlMode.PercentOutput, 0.0);
    }
  }

  public void stop(){
    m_turretMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void rotate(double rotation_rate){
    m_turretMotor.set(ControlMode.PercentOutput, rotation_rate * 0.5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
