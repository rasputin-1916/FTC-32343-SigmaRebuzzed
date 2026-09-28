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

      double testRPM = 500;
      double servopos = 0;

      waitForStart();

      while (opModeIsActive()) {

          driver.readButtons();

          s_Shooter.setSetpoint(testRPM);
          s_Shooter.runShooter();
          s_Shooter.periodic();


          if (driver.wasJustPressed(GamepadKeys.Button.A)) {
//              s_Shooter.setCompression(s_Shooter.getCompression() - 0.05);
              s_Shooter.setCompression(Constants.shooterConstants.compressionMin);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.B)) {
//              s_Shooter.setCompression(s_Shooter.getCompression() + 0.05);
              s_Shooter.setCompression(Constants.shooterConstants.compressionMax);
          }



          if (driver.wasJustPressed(GamepadKeys.Button.X)) {
              testRPM += 50;
          }

          if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
              testRPM -= 25;
          }

          telemetry.addData("Shooter RPM", s_Shooter.getRPM());
          telemetry.addData("Shooter Setpoint", testRPM);
          telemetry.addData("Compression Setpoint", s_Shooter.getCompression());

          telemetry.update();
      }
    }
}
