/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  private final WPI_TalonSRX m_turretMotor = new WPI_TalonSRX(Constants.TURRET_MOTOR_ID);

  /**
   * Creates a new Turret.
   */
  public Turret() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
