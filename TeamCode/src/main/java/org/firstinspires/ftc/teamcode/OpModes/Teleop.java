package org.firstinspires.ftc.teamcode.OpModes;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

@TeleOp(name="Drive", group="Teleop")
public class Teleop extends LinearOpMode {

    Drivetrain s_Drive;
//    Shooter s_Shooter;

    GamepadEx driver;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Drive = new Drivetrain(hardwareMap);

//        s_Shooter = new Shooter(hardwareMap);

      driver = new GamepadEx(gamepad1);

      double testRPM = 500;
      double servopos = 0;

      waitForStart();

      while (opModeIsActive()) {
//          s_Shooter.setSetpoint(testRPM);
//          s_Shooter.runShooter();

          s_Drive.drive(
                  driver.getLeftY(),
                  driver.getLeftX(),
                  driver.getRightX()
          );



//          if (driver.wasJustPressed(GamepadKeys.Button.A)) {
////              s_Shooter.setCompression(s_Shooter.getCompression() - 0.05);
//              s_Shooter.setCompression(Constants.shooterConstants.compressionMin);
//          }

//          if (driver.wasJustPressed(GamepadKeys.Button.B)) {
////              s_Shooter.setCompression(s_Shooter.getCompression() + 0.05);
//              s_Shooter.setCompression(Constants.shooterConstants.compressionMax);
//          }



//          if (driver.wasJustPressed(GamepadKeys.Button.X)) {
//              testRPM += 50;
//          }

//          if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
//              testRPM -= 50;
//          }

//          telemetry.addData("Left Compression Servo", s_Shooter.getCompressionLeftPosition());
//          telemetry.addData("Right Compression Servo", s_Shooter.getCompressionRightPosition());
//          telemetry.addData("Hood Servo", s_Shooter.getHoodPosition());
//          telemetry.addData("Shooter RPM", s_Shooter.getRPM());
//          telemetry.addData("Shooter Setpoint", testRPM);
//          telemetry.addData("Compression Setpoint", s_Shooter.getCompression());
          telemetry.addData("Drive Rotation", s_Drive.getHeading());

          telemetry.update();
      }
    }
}
