/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int K_UNITS_PER_REVOLUTION = 2048; /* this is constant for Talon FX */
  public static final int LEFT_UPPER_DRIVE_ID = 4;
  public static final int LEFT_LOWER_DRIVE_ID = 1;
  public static final int RIGHT_UPPER_DRIVE_ID = 2;
  public static final int RIGHT_LOWER_DRIVE_ID = 3;

  public static final int DRIVER_CONTROLLER_PORT = 0;
  public static final int OPERATOR_CONTROLLER_PORT = 1;

  public static final int SHOOTER_MOTOR_ID = 42;
  public static final int TURRET_MOTOR_ID = 55;
  public static final int VERTICAL_FEEDER_MOTOR_ID = 54;
  public static final int INTAKE_POSITION_MOTOR_ID = 22;
  public static final int INTAKE_BELT_MOTOR_ID = 21;

  public static final int TALON_DEFAULT_PID_PORT = 0;
  public static final int TALON_TIMEOUT_MS = 10;

  public static final int DIO_PORT_TOP_VERTICAL_BALL_SENSOR = 0;
  public static final int DIO_PORT_BOTTOM_VERTICAL_BALL_SENSOR = 1;
  public static final int DIO_PORT_MIDDLE_BALL_SENSOR = 2;

  public static final int SPEED_FOR_INITIATION_LINE = 17000;
  public static final int SPEED_FOR_END_OF_TRENCH_RUN = 17500;
public static final double TELEOP_MAX_DRIVE_POWER = 0.6;

}
