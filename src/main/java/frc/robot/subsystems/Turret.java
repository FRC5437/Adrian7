/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  private final WPI_TalonSRX m_turretMotor = new WPI_TalonSRX(Constants.TURRET_MOTOR_ID);

  /**
   * Creates a new Turret.
   */
  public Turret() {

  }

  public void trackCameraTarget(){
    //TODO - use the limelight target info to adjust the turret toward the target
    //ensure limits to rotation are enforced!
    //use Network Tables to get the limelight info
  }

  //TODO - figure out if we even need 3d target info this year - the distance can be useful
  //in a few auto cases to determine field position
  public void limelight3dTest() {
    //final double[] camtran = limelight3dTransposeArray.getDoubleArray(new double[] {});
    //final double zValueFromTarget = camtran[2];

 //   if (zValueFromTarget < -60.0) {
      //m_robotDrive.arcadeDrive(0.6, 0.0);
//    } else {
      //System.out.println(zValueFromTarget);
      //m_robotDrive.arcadeDrive(0.0, 0.0);
 //   }
  }

  public void rotate(double rotation_rate){
    m_turretMotor.set(ControlMode.PercentOutput, rotation_rate);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
