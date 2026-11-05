package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ServoTest;


@TeleOp(name = "Servo Test", group="Teleop")
public class ServoTeleop extends LinearOpMode {

    ServoTest test;

    @Override
    public void runOpMode() throws InterruptedException {

        test = new ServoTest(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}



