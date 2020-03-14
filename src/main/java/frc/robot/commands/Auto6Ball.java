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

public class Auto6Ball extends SequentialCommandGroup {
  public Auto6Ball(Intake intake, HorizontalIndexer horizontalIndexer, VerticalIndexer verticalIndexer, Chassis chassis, Shooter shooter){
    super(new Auto3BallSequence(shooter, verticalIndexer, horizontalIndexer, intake, chassis, 0.0),
          new DriveDistanceAndIntakeABall(intake, chassis, 91), 
          new ParallelRaceGroup(new StackTheMagazine(horizontalIndexer, verticalIndexer), new WaitCommand(3.0)),
          new DriveDistanceAndIntakeABall(intake, chassis, 36),
          new ParallelRaceGroup(new StackTheMagazine(horizontalIndexer, verticalIndexer), new WaitCommand(3.0)),
          new DriveDistanceAndIntakeABall(intake, chassis, 36), 
          new DriveDistance(chassis, -1.0 * 164), 
          new ShootAtSpeed (Constants.SPEED_FOR_END_OF_TRENCH_RUN, shooter, verticalIndexer, horizontalIndexer));
  }

}