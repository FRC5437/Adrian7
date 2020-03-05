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
  int m_counter;
  /**
   * Creates a new AdvanceVerticalIndex.
   */
  public AdvanceVerticalIndex(VerticalIndexer indexer) {
    m_indexer = indexer;
    m_counter = 0;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_counter = 0;
    m_targetPosition = m_indexer.getCurrentPosition() + 5950;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //TODO see if simple power based activation works better than motion magic
    //m_indexer.advanceBall(m_targetPosition);
    m_indexer.drive(0.35);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //TODO trying to use the ball sensors rather than the error
    //because there are 2 different distance moves depending on whether we are moving a ball from stage 3 or stage 4
      if(m_indexer.ballAtStage5()){
        return true;      
      }
      if(m_indexer.ballAtStage4() || m_indexer.ballAtStage5()){
        m_counter += 1;
      }
    return m_counter > 3;
  }
}
