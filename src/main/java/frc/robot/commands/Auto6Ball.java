/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VerticalIndexer;

public class Auto6Ball extends SequentialCommandGroup {
  public Auto6Ball(Intake intake, HorizontalIndexer horizontalIndexer, VerticalIndexer verticalIndexer, Chassis chassis, double distanceInches, Shooter shooter){
    super(new Auto3BallSequence(shooter, verticalIndexer, horizontalIndexer, intake, chassis),
          new DriveDistanceAndStackMagazine(intake, horizontalIndexer, verticalIndexer, chassis, distanceInches), 
          new DriveDistance(chassis, distanceInches), 
          new ShootTheMoon(shooter, verticalIndexer, horizontalIndexer, intake));
  }

}