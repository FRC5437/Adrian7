/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VerticalIndexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Auto3BallSequence extends SequentialCommandGroup {
  /**
   * Creates a new Auto3BallSequence.
   */
  public Auto3BallSequence(Shooter shooter, VerticalIndexer indexer, HorizontalIndexer horizontalIndexer, Intake intake, Chassis chassis, double distance) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super( new ParallelRaceGroup(new ShootAtSpeed(Constants.SPEED_FOR_INITIATION_LINE, shooter, indexer, horizontalIndexer), new WaitCommand(4.0)),
           new ParallelRaceGroup(new DriveDistance(chassis, -1.0 * distance), new WaitCommand(1.5)),
           new ParallelRaceGroup(new LowerIntake(intake), new WaitCommand(1.0))
         );
  }
}
