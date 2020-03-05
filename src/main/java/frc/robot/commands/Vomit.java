/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VerticalIndexer;

public class Vomit extends CommandBase {
  Intake m_intake;
  HorizontalIndexer m_horizontalIndexer;
  VerticalIndexer m_verticalIndexer;

  /**
   * Creates a new Vomit.
   */
  public Vomit(Intake intake, HorizontalIndexer horizontalIndexer, VerticalIndexer verticalIndexer) {
    m_intake = intake;
    m_horizontalIndexer = horizontalIndexer;
    m_verticalIndexer = verticalIndexer;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake, horizontalIndexer, verticalIndexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.eject();
    m_horizontalIndexer.backup();
    m_verticalIndexer.backup();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.stop();
    m_horizontalIndexer.stop();
    m_verticalIndexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
