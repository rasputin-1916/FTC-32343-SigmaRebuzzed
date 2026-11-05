package org.firstinspires.ftc.teamcode.OpModes;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Commands.IncrementLift;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ClampServos;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Util.MathUtil;

@TeleOp(name="Teleop", group="Teleop")
public class Teleop extends LinearOpMode {

    Drivetrain s_Drive;
    Odometry s_Odometry;
    Shooter s_Shooter;
    Transfer s_Transfer;
    Turret s_Turret;
    Intake s_Intake;
    ClampServos s_Clamp;

    GamepadEx driver;
    GamepadEx operator;

    MultipleTelemetry m_Telemetry;
    IncrementLift m_Lift;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Drive = new Drivetrain(hardwareMap);
        s_Odometry = new Odometry(hardwareMap);
        s_Shooter = new Shooter(hardwareMap);
        s_Transfer = new Transfer(hardwareMap);
        s_Turret = new Turret(hardwareMap, s_Drive);
        s_Intake = new Intake(hardwareMap);
        s_Clamp = new ClampServos(hardwareMap);

        m_Telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        m_Lift = new IncrementLift(s_Transfer);

        double shooterRPM = 500;
        double transferPosition = 0.0;
        double turretPosition = 0.0;

      driver = new GamepadEx(gamepad1);
      operator = new GamepadEx(gamepad2);

      s_Clamp.setFrontCornerServo(Constants.clampConstants.closedFrontPosition);
      s_Clamp.setBackCornerServo(Constants.clampConstants.closedBackPosition);

      s_Odometry.setPosition(new Pose2D(DistanceUnit.INCH, 8.0, 8.0, AngleUnit.DEGREES, 0));

      waitForStart();

      while (opModeIsActive()) {

          driver.readButtons();
          operator.readButtons();

          s_Clamp.setFrontCornerServo(Constants.clampConstants.openFrontPosition);
          s_Clamp.setBackCornerServo(Constants.clampConstants.openBackPosition);


          s_Drive.drive(
                  driver.getLeftY(),
                  driver.getLeftX(),
                  driver.getRightX()
          );

          if (driver.wasJustPressed(GamepadKeys.Button.A)) {
              s_Shooter.setCompression(0);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.B)) {
              s_Shooter.setCompression(1);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.X)) {
              shooterRPM += 50;
          }

          if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
              shooterRPM -= 25;
          }

          s_Shooter.setSetpoint(shooterRPM);
          s_Shooter.runShooter();

          if (driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
              s_Intake.intake();
              s_Intake.setIntakeMotor(driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
          } else if (driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1) {
              s_Intake.outtake();
              s_Intake.setIntakeMotor(-driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
          } else {
              s_Intake.stop();
              s_Intake.setIntakeMotor(0);
          }

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
              transferPosition -= 0.125;
          }

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
              transferPosition += 0.125;
          }

          transferPosition = MathUtil.clamp(transferPosition, 0 , 1);
          m_Lift.run(transferPosition);

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
              turretPosition += 5.0;
          }

          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
              turretPosition -= 5.0;
          }

          s_Turret.setSetpoint(turretPosition);


          s_Shooter.periodic();
          s_Turret.periodic();
          s_Transfer.periodic();
          s_Odometry.periodic();
          s_Drive.periodic();

          m_Telemetry.addData("Shooter Setpoint", shooterRPM);
          m_Telemetry.addData("Shooter RPM", s_Shooter.getRPM());

          m_Telemetry.addData("Turret Angle", s_Turret.getAngleFR());
          m_Telemetry.addData("Turret Setpoint", s_Turret.getSetpointFR());

          m_Telemetry.addData("Transfer Motor Pos", s_Transfer.getMotorPosition());
          m_Telemetry.addData("Transfer Servo Pos", s_Transfer.getServoPosition());
          m_Telemetry.addData("Transfer Given Setpoint", transferPosition);
          m_Telemetry.addData("Transfer: Motor Setpoint", s_Transfer.getMotorSetpoint());
          m_Telemetry.addData("Transfer: Servo Setpoint", s_Transfer.getServoSetpoint());


          m_Telemetry.addData("Drive Rotation", s_Drive.getHeading());
          m_Telemetry.addData("Odometry X:", s_Odometry.getPosition().getX(DistanceUnit.INCH));
          m_Telemetry.addData("Odometry Y:", s_Odometry.getPosition().getY(DistanceUnit.INCH));
          m_Telemetry.addData("Odometry Angle:", s_Odometry.getPosition().getHeading(AngleUnit.DEGREES));

          m_Telemetry.update();
      }
    }
}
