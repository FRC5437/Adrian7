/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Lights;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ActivateLights extends InstantCommand {
  private Lights m_lights;
  private double m_value = -1.0;

  public ActivateLights(Lights lights) {
    m_lights = lights;
    addRequirements(lights);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // TODO: set a wrapper to change m_value wrap to -1.0 when value hits 1.0
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_value += 0.02;
    if (m_value >= 1.0){
      m_value = -0.98;
    }
    m_lights.setPatternValue(m_value);
  }
}
