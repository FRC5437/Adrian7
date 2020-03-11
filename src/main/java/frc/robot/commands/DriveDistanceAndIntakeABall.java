/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VerticalIndexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveDistanceAndIntakeABall extends ParallelDeadlineGroup {
  /**
   * Creates a new DriveDistanceAndStackMagazine.
   */
  public DriveDistanceAndIntakeABall(Intake intake, Chassis chassis, double distance) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    //super(new StackTheMagazine(intake, horizontalIndexer, verticalIndexer), new DriveDistance(chassis, distance));
    super(new SequentialCommandGroup(new DriveDistance(chassis, distance), new WaitCommand(2.0)),
          new IntakeABall(intake));
  }
}
