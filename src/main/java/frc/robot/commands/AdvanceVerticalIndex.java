/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VerticalIndexer;

public class AdvanceVerticalIndex extends CommandBase {
  VerticalIndexer m_indexer;
  int m_targetPosition = 0;
  int m_counter = 0;
  /**
   * Creates a new AdvanceVerticalIndex.
   */
  public AdvanceVerticalIndex(VerticalIndexer indexer) {
    m_indexer = indexer;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_targetPosition = m_indexer.getCurrentPosition() + 10000;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_indexer.advanceBall(m_targetPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_indexer.onTarget() ){
      m_counter += 1;
    }
    if (m_counter > 10){
      return true;
    } else {
      return false;
    }  
  }
}
