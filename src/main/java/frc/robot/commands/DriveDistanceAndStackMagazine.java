/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VerticalIndexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveDistanceAndStackMagazine extends ParallelCommandGroup {
  /**
   * Creates a new DriveDistanceAndStackMagazine.
   */
  public DriveDistanceAndStackMagazine(Intake intake, HorizontalIndexer horizontalIndexer, VerticalIndexer verticalIndexer, Chassis chassis, double distance) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new Stack3(intake, horizontalIndexer, verticalIndexer), new DriveDistance(chassis, distance));
  }
}
