/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lights extends SubsystemBase {
  Spark m_blinkin;
  /**
   * Creates a new Lights.
   */
  public Lights() {
    m_blinkin = new Spark(Constants.LIGHTS_PWM_PORT_ID);
  }

  public void setPatternValue( double value ){
    m_blinkin.setSpeed(value);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
