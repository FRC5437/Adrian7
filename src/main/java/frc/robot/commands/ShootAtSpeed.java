/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VerticalIndexer;

public class ShootAtSpeed extends CommandBase {
  final double indexerBeltSpeed = 0.5;
  final int tolerance = 500;

  int m_target_speed;
  Shooter m_shooter;
  VerticalIndexer m_indexer;
  HorizontalIndexer m_horiz;

  int m_empty_counter = 0;

  /**
   * Creates a new ShootAtSpeed.
   */
  public ShootAtSpeed(int speed, Shooter shooter, VerticalIndexer indexer, HorizontalIndexer horiz) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, indexer, horiz);
    m_target_speed = speed;
    m_shooter = shooter;
    m_indexer = indexer;
    m_horiz = horiz;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_empty_counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {  
      int currentSpeed = m_shooter.getCurrentSpeed();
      m_shooter.setSpeed(m_target_speed);
      if (Math.abs(m_target_speed - currentSpeed) < tolerance){
        //we are close enough to target velocity so shoot the ball!
        m_indexer.activate();
        m_horiz.activate();
      } else {
        //we are out of tolerance for the shooter velocity so wait for it
        m_indexer.stop();
        m_horiz.stop();
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
    m_indexer.stop();
    m_horiz.stop();
    m_empty_counter = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean all_done = false;
    if (m_indexer.isEmpty()){
      m_empty_counter += 1;
    } else {
      m_empty_counter = 0;
    }
    if (m_empty_counter > 30){
      all_done = true;
    }
    return all_done;
  }
}
