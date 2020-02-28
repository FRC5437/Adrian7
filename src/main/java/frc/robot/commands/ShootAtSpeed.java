/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VerticalIndexer;

public class ShootAtSpeed extends CommandBase {
  final double indexerBeltSpeed = 0.5;
  final int tolerance = 500;

  int m_target_speed;
  Shooter m_shooter;
  VerticalIndexer m_indexer;

  /**
   * Creates a new ShootAtSpeed.
   */
  public ShootAtSpeed(int speed, Shooter shooter, VerticalIndexer indexer) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, indexer);
    m_target_speed = speed;
    m_shooter = shooter;
    m_indexer = indexer;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {  
      int currentSpeed = m_shooter.getCurrentSpeed();
      m_shooter.setSpeed(m_target_speed);
      if (Math.abs(m_target_speed - currentSpeed) < tolerance){
        //we are close enough to target velocity so shoot the ball!
        m_indexer.activate();
      } else {
        //we are out of tolerance for the shooter velocity so wait for it
        m_indexer.deactivate();
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setSpeed(0);
    m_indexer.deactivate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
