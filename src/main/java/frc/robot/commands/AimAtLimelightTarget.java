/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Turret;

public class AimAtLimelightTarget extends CommandBase {
  Turret m_turret;
  Lights m_lights;
  /**
   * Creates a new AimAtLimelightTarget.
   */
  public AimAtLimelightTarget(Turret turret, Lights lights) {
    m_turret = turret;
    m_lights = lights;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(turret, lights);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_turret.trackCameraTarget();
    if (m_turret.onTarget()){
      m_lights.setPatternValue(0.73);
    } else {
      m_lights.setPatternValue(0.61);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_turret.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
