package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Twist2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class ThreeWheelOdometry extends SubsystemBase {

    //Ticks to inches per each pod
    //Tracks field relative x and y distance
    //Find rotation in radians, convert to degrees

    MotorEx rightPod;
    MotorEx leftPod;
    MotorEx horizontalPod;

    double prevLeftEncoder, prevRightEncoder, prevHorizontalEncoder;
    Rotation2d previousAngle;
    Pose2d robotPose;


    public ThreeWheelOdometry(HardwareMap hardwaremap) {
        rightPod = new MotorEx(hardwaremap, "fl");
        leftPod = new MotorEx(hardwaremap, "fr");
        horizontalPod = new MotorEx(hardwaremap, "bl");

        previousAngle = new Rotation2d();
        robotPose = new Pose2d();
    }

    public double ticksToInches(double ticks) {
        return (ticks/Constants.odometryConstants.ticksPerRev) * (Math.PI * Constants.odometryConstants.wheelDiameter);
    }

    public double getLeftPodInches() {
        return ticksToInches(leftPod.getCurrentPosition());
    }

    public double getRightPodInches() {
        return ticksToInches(rightPod.getCurrentPosition());
    }

    public double getHorizontalPodInches() {
        return ticksToInches(horizontalPod.getCurrentPosition());
    }

    public void update(double leftEncoderPos, double rightEncoderPos, double horizontalEncoderPos) {
        double deltaLeftEncoder = leftEncoderPos - prevLeftEncoder;
        double deltaRightEncoder = rightEncoderPos- prevRightEncoder;
        double deltaHorizontalEncoder = horizontalEncoderPos - prevHorizontalEncoder;

        Rotation2d angle = previousAngle.plus(
                new Rotation2d(deltaLeftEncoder - deltaRightEncoder / Constants.odometryConstants.trackwidth)
        );

        prevLeftEncoder = leftEncoderPos;
        prevRightEncoder = rightEncoderPos;
        prevHorizontalEncoder = horizontalEncoderPos;

        double dw = (angle.minus(previousAngle).getRadians());
        double dx = (deltaLeftEncoder + deltaRightEncoder) / 2;
        double dy = deltaHorizontalEncoder - (Constants.odometryConstants.yOffset * dw);


        Twist2d twist2d = new Twist2d(dx, dy, dw);

        Pose2d newPose = robotPose.exp(twist2d);

        previousAngle = angle;

        robotPose = new Pose2d(newPose.getTranslation(), angle);
    }

    public Pose2d getPose() {
        return robotPose;
    }

    public void periodic() {
        update(getLeftPodInches(), getRightPodInches(), getHorizontalPodInches());
    }
}
