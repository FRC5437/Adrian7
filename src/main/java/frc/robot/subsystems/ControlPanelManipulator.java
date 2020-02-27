/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C;

public class ControlPanelManipulator extends SubsystemBase {

  private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.1213, 0.4277, 0.4514);
  private final Color kGreenTarget = ColorMatch.makeColor(0.1633, 0.5845, 0.2524);
  private final Color kRedTarget = ColorMatch.makeColor(0.5239, 0.3428, 0.1333);
  private final Color kYellowTarget = ColorMatch.makeColor(0.3115, 0.5681, 0.1206);

  /**
   * Creates a new ControlPanelManipulator.
   */
  public ControlPanelManipulator() {
    initializeColorSensor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void initializeColorSensor() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }
}
