/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.I2C;

public class ControlPanelManipulator extends SubsystemBase {

  private final NetworkTableInstance m_tableInstance;
  private NetworkTable m_fmsTable;

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
    m_tableInstance = NetworkTableInstance.getDefault();
    m_fmsTable = m_tableInstance.getTable("FMSInfo");  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public String getFMSColor(){
    return m_fmsTable.getEntry("GameSpecificMessage").getString("");
  }

  public boolean doesColorMatchFMS(){
      Color detectedColor = colorSensor.getColor();
      String colorString;
      ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
      if (match.confidence < 0.91) {
        colorString = "Other Color - Low Confidence";
      } else if (match.color == kBlueTarget) {
        colorString = "B";
      } else if (match.color == kRedTarget) {
        colorString = "R";
      } else if (match.color == kGreenTarget) {
        colorString = "G";
      } else if (match.color == kYellowTarget) {
        colorString = "Y";
      } else {
        colorString = "Unknown";
      }
    
      return colorString == getFMSColor();
  }

  private void initializeColorSensor() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }
}
