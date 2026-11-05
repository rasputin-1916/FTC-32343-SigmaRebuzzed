package org.firstinspires.ftc.teamcode.OpModes;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ClampServos;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@TeleOp(name="Servos 4 to 6", group="Teleop")
public class TestServo4to6 extends LinearOpMode {

    ClampServos s_Clamp;
    Intake s_Intake;
    GamepadEx driver, operator;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Clamp = new ClampServos(hardwareMap);
        s_Intake = new Intake(hardwareMap);

        driver = new GamepadEx(gamepad1);

        double clampPosition = 0.45;
        double backClamp = 0.65;
        boolean intake = false;

        waitForStart();

        while (opModeIsActive()) {

            driver.readButtons();

            if (driver.wasJustPressed(GamepadKeys.Button.A)) {
                clampPosition += 0.05;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.B)) {
                clampPosition -= 0.05;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
                backClamp += 0.05;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.X)) {
                backClamp -= 0.05;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
                intake = true;
            }

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
                intake = false;
            }

            s_Clamp.setFrontCornerServo(clampPosition);
            s_Clamp.setBackCornerServo(backClamp);

            if (intake) {
                s_Intake.intake();
            } else {
                s_Intake.stop();
            }

            telemetry.addData("Front Clamp", s_Clamp.getFrontCornerServo());
            telemetry.addData("Back Clamp", s_Clamp.getBackCornerServo());

            telemetry.update();
        }
    }
}
