package org.firstinspires.ftc.teamcode.OpModes;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@TeleOp(name="TurretTest", group="Teleop")
public class TurretTest extends LinearOpMode {

    Turret s_Turret;
    Drivetrain s_Drive;
    GamepadEx driver, operator;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Drive = new Drivetrain(hardwareMap);
        s_Turret = new Turret(hardwareMap, s_Drive);

        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        waitForStart();

        while (opModeIsActive()) {

            s_Drive.drive(
                    driver.getLeftY(),
                    driver.getLeftX(),
                    driver.getRightX()
            );

            s_Turret.setSetpoint(-Math.toDegrees(Math.atan2(operator.getLeftY(), operator.getLeftX())) + 90);

            s_Turret.periodic();

            telemetry.addData("Robot Relative Angle", s_Turret.getAngleRR());
            telemetry.addData("Field Relative Angle", s_Turret.getAngleFR());

            telemetry.addData("Field Relative Setpoint", s_Turret.getSetpointFR());
            telemetry.addData("Robot Relative PENIS", s_Turret.getSetpointRR());


            telemetry.update();
        }
    }
}
