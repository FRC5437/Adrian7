/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveDistance extends CommandBase {
  Chassis m_chassis;
  int m_leftTargetPosition;
  int m_rightTargetPosition;
  double m_distanceInches;
  int m_counter = 0;

  /**
   * Creates a new DriveDistance.
   */
  public DriveDistance(Chassis chassis, double distanceInches) {
    m_chassis = chassis;
    m_distanceInches = distanceInches;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_counter = 0;
    m_leftTargetPosition = m_chassis.convertLeftInchesToTarget(m_distanceInches);
    m_rightTargetPosition = m_chassis.convertRightInchesToTarget(m_distanceInches);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_chassis.driveAuto(m_leftTargetPosition, m_rightTargetPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_chassis.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_chassis.onTarget() ){
      m_counter += 1;
    }
    if (m_counter > 10){
      return true;
    } else {
      return false;
    }
  }
}
