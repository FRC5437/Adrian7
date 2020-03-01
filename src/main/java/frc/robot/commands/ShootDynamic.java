/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.VerticalIndexer;

public class ShootDynamic extends CommandBase {
  Shooter m_shooter;
  VerticalIndexer m_indexer;
  Turret m_turret;

  /**
   * Creates a new ShootDynamic.
   */
  public ShootDynamic(Shooter shooter, VerticalIndexer indexer, Turret turret) {
    m_shooter = shooter;
    m_indexer = indexer;
    m_turret = turret;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, indexer, turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    int currentSpeed = m_shooter.getCurrentSpeed();
    int targetSpeed = getSpeedForArea(m_turret.getTargetArea());
    int tolerance = 500;

    m_shooter.setSpeed(targetSpeed);
    if (Math.abs(targetSpeed - currentSpeed) < tolerance){
      //we are close enough to target velocity so shoot the ball!
      m_indexer.activate();
    } else {
      //we are out of tolerance for the shooter velocity so wait for it
      m_indexer.stop();
    }
  }

  private int getSpeedForArea(double area){
    //the smaller the area the higher the speed should be
    double kP = 500.0;
    int maxSpeed = 20000;
    int calculatedSpeed = maxSpeed - (int)(kP * area);
    return calculatedSpeed;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
    m_indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
