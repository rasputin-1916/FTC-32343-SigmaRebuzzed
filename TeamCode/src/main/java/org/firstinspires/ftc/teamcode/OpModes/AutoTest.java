package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;

@Autonomous(name="Auto Test", group="Auto")
@Disabled
public class AutoTest extends LinearOpMode {

    Drivetrain s_Drive;
    Odometry s_Odometry;
    @Override
    public void runOpMode() {
        s_Drive = new Drivetrain(hardwareMap);

        s_Odometry = new Odometry(hardwareMap);

        telemetry.addData("Position: ", s_Odometry.getPosition());
        telemetry.update();

        
    }

}
