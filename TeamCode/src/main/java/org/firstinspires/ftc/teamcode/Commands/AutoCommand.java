package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Util.MathUtil;
import org.firstinspires.ftc.teamcode.Util.PIDController;

public class AutoCommand extends CommandBase {
    Drivetrain s_Drivetrain;
    Odometry s_Odometry;


    PIDController yController;
    PIDController xController;
    PIDController hController;
    double xError;
    double yError;
    double hError;
    double xOut;
    double yOut;
    double hOut;
    boolean isFinished;
    double timestamp;

    public AutoCommand(Drivetrain drivetrain, Odometry odometry) {
        this.s_Drivetrain = drivetrain;
        this.s_Odometry = odometry;

        yController = new PIDController(Constants.driveConstants.drivekP,Constants.driveConstants.drivekI,Constants.driveConstants.drivekD);
        xController = new PIDController(Constants.driveConstants.drivekP,Constants.driveConstants.drivekI,Constants.driveConstants.drivekD);
        hController = new PIDController(Constants.driveConstants.drivekP,Constants.driveConstants.drivekI,Constants.driveConstants.drivekD);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        isFinished = false;
    }

    public void run(SparkFunOTOS.Pose2D targetPose, double maxLinearSpeed, double maxRotationalSpeed) {
        isFinished = false;

        double YPos = s_Odometry.getY();
        double XPos = s_Odometry.getX();
        double hPos = s_Odometry.getRotation();

        yError = targetPose.y - YPos;
        xError = targetPose.x - XPos;
        hError = targetPose.h - hPos;

        yOut = MathUtil.clamp(yController.calculate(YPos, targetPose.y), -maxLinearSpeed, maxLinearSpeed);
        xOut = MathUtil.clamp(xController.calculate(XPos, targetPose.x), -maxLinearSpeed, maxLinearSpeed);
        hOut = MathUtil.clamp(hController.calculate(hPos, targetPose.h), -maxRotationalSpeed, maxRotationalSpeed);

        s_Drivetrain.drive(yOut,xOut,hOut);

        if (Math.abs(xError) < Constants.driveConstants.driveTolerance && Math.abs(yError) < Constants.driveConstants.driveTolerance && Math.abs(hError) < Constants.driveConstants.driveTolerance) {
            s_Drivetrain.stop();
            isFinished = true;
        }

    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    public boolean isFinished() {
        return isFinished;
    }

}
