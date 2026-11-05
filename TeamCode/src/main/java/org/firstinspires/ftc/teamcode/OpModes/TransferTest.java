package org.firstinspires.ftc.teamcode.OpModes;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@TeleOp(name="TransferTest", group="Teleop")
public class TransferTest extends LinearOpMode {

    Transfer s_Transfer;
    Drivetrain s_Drive;
    GamepadEx driver, operator;

    @Override
    public void runOpMode() throws InterruptedException {
        s_Transfer = new Transfer(hardwareMap);

        driver = new GamepadEx(gamepad1);

        s_Transfer.setMotorSetpoint(0.0);
//        s_Transfer.setServoSetpoint(0.5);

        double servoPos = 0.0;

        waitForStart();

        while (opModeIsActive()) {

            driver.readButtons();

            if (driver.wasJustPressed(GamepadKeys.Button.A)) {
                servoPos += 0.1;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.B)) {
                servoPos -= 0.1;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
                servoPos = Constants.transferConstants.minServoPos;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.X)) {
                servoPos = Constants.transferConstants.maxServoPos;
            }

            s_Transfer.setServoSetpoint(servoPos);

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                s_Transfer.setMotorSetpoint(2.15);
            }

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
                s_Transfer.setMotorSetpoint(1.0);
            }

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
                s_Transfer.setMotorSetpoint(2.0);
            }

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                s_Transfer.setMotorSetpoint(0.0);
            }

            s_Transfer.periodic();



            telemetry.addData("Transfer Motor Position", s_Transfer.getMotorPosition());
            telemetry.addData("Motor Setpoint", s_Transfer.getMotorSetpoint());

            telemetry.addData("Servo Position", s_Transfer.getServoPosition());
            telemetry.addData("Servo Raw Position", s_Transfer.getRawServoPosition());
            telemetry.addData("Servo Speed", s_Transfer.getServoSpeed());

            telemetry.addData("Servo Setpoint", s_Transfer.getServoSetpoint());

            telemetry.update();
        }
    }
}
