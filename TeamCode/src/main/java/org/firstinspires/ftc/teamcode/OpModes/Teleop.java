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
import org.firstinspires.ftc.teamcode.Commands.CalculateShot;
import org.firstinspires.ftc.teamcode.Commands.GamePieceQueue;
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

import java.util.ArrayList;

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
    CalculateShot m_Aim;
    GamePieceQueue m_Queue;

    @Override
    public void runOpMode() throws InterruptedException {

        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        double shooterRPM = 500;
        double transferPosition = 0.0;
        double queueTimestamp = getRuntime();
        double transferDistance = 0.0;
//        double turretPosition = 0.0;
//        boolean isNectar = false;

        s_Drive = new Drivetrain(hardwareMap);
        s_Odometry = new Odometry(hardwareMap);
        s_Shooter = new Shooter(hardwareMap);
        s_Transfer = new Transfer(hardwareMap);
        s_Turret = new Turret(hardwareMap, s_Drive);
        s_Intake = new Intake(hardwareMap);
        s_Clamp = new ClampServos(hardwareMap);

        m_Telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        m_Lift = new IncrementLift(s_Transfer);
        m_Aim = new CalculateShot(
                s_Shooter,
                s_Turret,
                s_Odometry,
                false,
                () -> s_Shooter.getCompression() < 0.5,
                () -> s_Shooter.getCompression() > 0.5);
        m_Queue = new GamePieceQueue(m_Lift, s_Transfer, s_Shooter, operator);

      s_Clamp.setFrontCornerServo(Constants.clampConstants.closedFrontPosition);
      s_Clamp.setBackCornerServo(Constants.clampConstants.closedBackPosition);


      waitForStart();

      s_Odometry.setNewPosition(new Pose2D(DistanceUnit.INCH, 62.0, -60.0, AngleUnit.DEGREES, 0));

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

          if (operator.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
              m_Queue.addPollen();
          }

          if (operator.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
              m_Queue.addNectar();
          }

          if (operator.wasJustPressed(GamepadKeys.Button.A)) {
              m_Queue.clear();
          }

            m_Queue.calculateDistances();

          if (operator.wasJustPressed(GamepadKeys.Button.Y)) {
//              if (m_Queue.calculateDistances().isPresent()) {
                  ArrayList<Double> givenList = m_Queue.getDistanceList();
                  for (int j = -1; j < givenList.size();) {
                      m_Telemetry.addData("timestamp", queueTimestamp);
                      m_Telemetry.addData("given list", givenList);
                      m_Telemetry.addData("transfer distance", transferDistance);
                      if (j == -1) {
                          queueTimestamp = getRuntime();
                          j++;
                      }
                      if (givenList.get(j) % Constants.transferConstants.nectarSize == 0) {
                          s_Shooter.setCompression(1);
                      } else {
                          s_Shooter.setCompression(0);
                      }
                      transferDistance += givenList.get(j);
                      transferDistance = MathUtil.clamp(transferDistance, 0, 1);
                      m_Lift.run(transferDistance);
                      if (getRuntime() > queueTimestamp + 0.5) {
                          if (j == givenList.size() - 1) {
                              m_Queue.clear();
                              m_Lift.run(0);
                          }
                          queueTimestamp = getRuntime();
                          j++;
                      }
                  }
//              }
          }

          m_Aim.run();

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

//          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
//              transferPosition = 0;
//          }
//
//          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
//              transferPosition += 0.125;
//          }

          // pollen 1st ball 0.41, 0.59, 0.76, 0.95,
            // in 0.25 intervals
//
//          transferPosition = MathUtil.clamp(transferPosition, 0 , 1);
//          m_Lift.run(transferPosition);

//          if (driver.wasJustPressed(GamepadKeys.Button.A)) {
//              s_Shooter.setCompression(0);
//          }
//
//          if (driver.wasJustPressed(GamepadKeys.Button.B)) {
//              s_Shooter.setCompression(1);
//          }

          if (driver.wasJustPressed(GamepadKeys.Button.BACK)) {
              s_Odometry.setNewPosition(new Pose2D(DistanceUnit.INCH, 62.0, -60.0, AngleUnit.DEGREES, 0));
          }

//          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
//              turretPosition += 5.0;
//          }
//
//          if (driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
//              turretPosition -= 5.0;
//          }
//
//          s_Turret.setSetpoint(turretPosition);


          s_Shooter.periodic();
          s_Turret.periodic();
          s_Transfer.periodic();
          s_Odometry.periodic();
          s_Drive.periodic();

          m_Telemetry.addData("Boolean List", m_Queue.getBooleanlist());
          m_Telemetry.addData("Distance List", m_Queue.getDistanceList());

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
          m_Telemetry.addData("Odometry X:", s_Odometry.getX());
          m_Telemetry.addData("Odometry Y:", s_Odometry.getY());
          m_Telemetry.addData("Odometry Angle:", s_Odometry.getPosition().getHeading(AngleUnit.DEGREES));

          m_Telemetry.update();

      }
    }
}
