package org.firstinspires.ftc.teamcode.OpModes;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

@TeleOp(name="ShooterTest", group="Teleop")
public class ShooterTest extends LinearOpMode {
    Shooter s_Shooter;

    GamepadEx driver;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Shooter = new Shooter(hardwareMap);

      driver = new GamepadEx(gamepad1);

      double testRPM = 0;
      s_Shooter.setCompression(s_Shooter.getCompression());

      waitForStart();

      while (opModeIsActive()) {

          driver.readButtons();

          s_Shooter.setSetpoint(testRPM);
          s_Shooter.runShooter();
          s_Shooter.periodic();


          if (driver.wasJustPressed(GamepadKeys.Button.A)) {
              s_Shooter.setCompression(s_Shooter.getCompression() - 0.01);
//              s_Shooter.setCompression(0);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.B)) {
              s_Shooter.setCompression(s_Shooter.getCompression() + 0.01);
//              s_Shooter.setCompression(1);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
              testRPM = 1750;
          }

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
              testRPM = 2000;
          }

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
              testRPM = 2500;
          }


          if (driver.wasJustPressed(GamepadKeys.Button.X)) {
              s_Shooter.setCompression(1);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
              s_Shooter.setCompression(0);
          }

          telemetry.addData("Shooter RPM", s_Shooter.getRPM());
          telemetry.addData("Shooter Setpoint", testRPM);
          telemetry.addData("Compression Position", s_Shooter.getCompression());

          telemetry.update();
      }
    }
}
