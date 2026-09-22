package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

@TeleOp(name="Teleop", group="Teleop")
public class Teleop extends LinearOpMode {

    Shooter s_Shooter;

    Gamepad driver;

    @Override
    public void runOpMode() throws InterruptedException {

      s_Shooter = new Shooter(hardwareMap);

      driver = gamepad1;

      double testRPM = 3000;

      waitForStart();

      while (opModeIsActive()) {
//          s_Shooter.setSetpoint(testRPM);
//          s_Shooter.runShooter();

          if (driver.aWasPressed()) {
//            s_Shooter.setHoodPositonUp();
              s_Shooter.setCompression(0);
          }

          if (driver.bWasPressed()) {
              s_Shooter.setCompression(0.6);
//              s_Shooter.setHoodPositonDown();
          }

          if (driver.xWasPressed()) {
//              s_Shooter.setCompressionHigh();
              testRPM += 25;
          }

          if (driver.yWasPressed()) {
//              s_Shooter.setCompressionLow();
              testRPM -=25;
          }

//          telemetry.addData("Left Compression Servo", s_Shooter.getCompressionLeftPosition());
//          telemetry.addData("Right Compression Servo", s_Shooter.getCompressionRightPosition());
//          telemetry.addData("Hood Servo", s_Shooter.getHoodPosition());
          telemetry.addData("Shooter RPM", s_Shooter.getRPM());
          telemetry.addData("Shooter Setpoint", testRPM);

          telemetry.update();
      }
    }
}
