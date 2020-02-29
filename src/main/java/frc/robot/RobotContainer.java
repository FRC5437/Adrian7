/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto10Ball;
import frc.robot.commands.Auto3Ball;
import frc.robot.commands.Auto5Ball;
import frc.robot.commands.Auto6Ball;
import frc.robot.commands.Auto8Ball;
import frc.robot.commands.AutoChaosMonkey;
import frc.robot.commands.DriveRobot;
import frc.robot.commands.IntakeABall;
import frc.robot.commands.RotateTurret;
import frc.robot.commands.ShootAtSpeed;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.VerticalIndexer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis m_chassis = new Chassis();
  private final Turret m_turret = new Turret();
  private final Intake m_intake = new Intake();
  private final Shooter m_shooter = new Shooter();
  private final VerticalIndexer m_verticalIndexer = new VerticalIndexer();
  private final HorizontalIndexer m_horizontalIndexer = new HorizontalIndexer();

  private SendableChooser<Command> m_autoChooser;
  private Command m_autoCommand;

  // The driver's controller
  XboxController m_driverController = new XboxController(Constants.DRIVER_CONTROLLER_PORT);
  XboxController m_operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_PORT);

  // Network Tables
  private final NetworkTableInstance m_tableInstance;

  private NetworkTableEntry m_fieldSpecifiedColorName = null;
  private NetworkTableEntry m_limelight3dTransposeArray = null;
  private NetworkTableEntry m_colorSensorValue = null;



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_tableInstance = NetworkTableInstance.getDefault();
    initializeNetworkTables();
    configureButtonBindings();

    m_chassis.setDefaultCommand(new DriveRobot(m_chassis, m_driverController));
    m_turret.setDefaultCommand(new RotateTurret(m_turret, m_operatorController));
    initializeAutoChooser();
  }

  private void initializeAutoChooser() {
    m_autoChooser = new SendableChooser<>();
    m_autoChooser.setDefaultOption("3 Ball Auto", new Auto3Ball());
    m_autoChooser.addOption("6 Ball Auto", new Auto6Ball());
    m_autoChooser.addOption("5 Ball Sneak Auto", new Auto5Ball());
    m_autoChooser.addOption("8 Ball Auto", new Auto8Ball());
    m_autoChooser.addOption("10 Ball Auto", new Auto10Ball());
    m_autoChooser.addOption("Chaos Monkey Never Use!", new AutoChaosMonkey());
    SmartDashboard.putData("Auto Mode", m_autoChooser);
  }

  private void initializeNetworkTables() {
    NetworkTable fmsTable = m_tableInstance.getTable("FMSInfo");
    m_fieldSpecifiedColorName = fmsTable.getEntry("GameSpecificMessage");
    NetworkTable limelightTable = m_tableInstance.getTable("limelight");
    m_limelight3dTransposeArray = limelightTable.getEntry("camtran");
    NetworkTable colorSensorTable = m_tableInstance.getTable("ColorSensor");
    m_colorSensorValue = colorSensorTable.getEntry("sensorData");
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //******* Driver Controls ************/
    // Drive at half speed when the right bumper is held
    new JoystickButton(m_driverController, Button.kBumperRight.value)
        .whenPressed(() -> m_chassis.setMaxOutput(0.5))
        .whenReleased(() -> m_chassis.setMaxOutput(1));

    new JoystickButton(m_driverController, Button.kX.value)
        .whileHeld(new ShootAtSpeed(17000, m_shooter, m_verticalIndexer));

    new JoystickButton(m_driverController, Button.kY.value)
        .whileHeld(new ShootAtSpeed(17500, m_shooter, m_verticalIndexer));

    new JoystickButton(m_driverController, Button.kA.value)
        .whileHeld(new ShootAtSpeed(14000, m_shooter, m_verticalIndexer));

    new JoystickButton(m_driverController, Button.kB.value)
        .whileHeld(new ShootAtSpeed(20000, m_shooter, m_verticalIndexer));



    //***** Operator Controls ***********/
    new JoystickButton(m_operatorController, Button.kX.value)
        .whileHeld(new IntakeABall(m_intake));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    m_autoCommand = m_autoChooser.getSelected();
    return m_autoCommand;
  }
}
