package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Commands.AutoCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

//import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
//TODO: Potential shooting
@Autonomous(name="Auto Test", group="Auto")
@Disabled // TODO: get rid of or keep later tho
public class AutoTest extends LinearOpMode {
    Shooter s_Shooter;
    Drivetrain s_Drive;
    Odometry s_Odometry;

    AutoCommand autoCommand;

    @Override
    public void runOpMode() {
        s_Shooter = new Shooter(hardwareMap);

        s_Drive = new Drivetrain(hardwareMap);

        s_Odometry = new Odometry(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            autoCommand = new AutoCommand(s_Drive, s_Odometry);

            SparkFunOTOS.Pose2D target = new SparkFunOTOS.Pose2D(s_Odometry.getX(),s_Odometry.getY() + 40,90);

            autoCommand.run(target, 0.01,0.01);

            telemetry.addData("X Position: ", s_Odometry.getPosition().getX(DistanceUnit.INCH));
            telemetry.addData("Y Position: ", s_Odometry.getPosition().getY(DistanceUnit.INCH));
            telemetry.addData("Drive Rotation", s_Odometry.getPosition().getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }

    }

}
