/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalIndexer;

public class AdvanceHorizontalIndexer extends CommandBase {
  private final HorizontalIndexer m_indexer;
  private int m_counter;
  /**
   * Creates a new AdvanceHorizontalIndexer.
   */
  public AdvanceHorizontalIndexer(HorizontalIndexer indexer) {
    m_indexer = indexer;
    m_counter = 0;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_indexer.activate();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_indexer.ballAtMidHorizontal() || !m_indexer.ballAtFrontHorizontal()){
      m_counter += 1;
    } else {
      m_counter = 0;
    }
    return m_counter > 10;
  }
}
