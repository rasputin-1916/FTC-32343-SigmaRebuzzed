package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Util.PIDController;

//import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
//TODO: Potential shooting
@Autonomous(name="Auto Test", group="Auto")
@Disabled // TODO: get rid of or keep later tho
public class AutoTest extends LinearOpMode {
    Shooter s_Shooter;
    Drivetrain s_Drive;
    Odometry s_Odometry;
    PIDController s_PID;

    @Override
    public void runOpMode() {
        s_Shooter = new Shooter(hardwareMap);

        s_Drive = new Drivetrain(hardwareMap);

        s_Odometry = new Odometry(hardwareMap);

        double kp = 0;
        double ki = 0;
        double kd = 0;

        s_PID = new PIDController(kp,ki,kd);
        waitForStart();


        while (opModeIsActive()) {

            s_Drive.drive(0,0,0);
            telemetry.addData("X Position: ", s_Odometry.getPosition().getX(DistanceUnit.INCH));
            telemetry.addData("Y Position: ", s_Odometry.getPosition().getY(DistanceUnit.INCH));
            telemetry.addData("Drive Rotation", s_Odometry.getPosition().getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }

    }

}
