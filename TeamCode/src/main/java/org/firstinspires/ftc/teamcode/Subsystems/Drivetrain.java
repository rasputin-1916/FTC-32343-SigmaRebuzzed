package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;

public class Drivetrain extends SubsystemBase {
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    private final IMU IMU;


    private double yawOffset;

    //    private Constants.DrivetrainConstants.rotatingDirections rotationDirection;
    public Drivetrain(HardwareMap hardwaremap) {
        frontLeft = hardwaremap.get(DcMotorEx.class, Constants.driveConstants.frontLeftMotorID);
        frontRight = hardwaremap.get(DcMotorEx.class, Constants.driveConstants.frontRightMotorID);
        backLeft = hardwaremap.get(DcMotorEx.class, Constants.driveConstants.backLeftMotorID);
        backRight = hardwaremap.get(DcMotorEx.class, Constants.driveConstants.backRightMotorID);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IMU = hardwaremap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        IMU.initialize(parameters);
    }

    public void drive(double driveY, double driveX, double rotation) {

        double botHeading = getHeading();
        double headingRadians = Math.toRadians(botHeading);

        double sin =  Math.sin(-headingRadians);
        double cos =  Math.cos(-headingRadians);

        double fieldOrientedX = driveX * cos - driveY * sin;
        double fieldOrientedY = driveX * sin + driveY * cos;

        fieldOrientedX *= 1.0;

        double denominator = Math.max(Math.abs(fieldOrientedY) + Math.abs(fieldOrientedX) + Math.abs(rotation), 1);

        double frontLeftPower = (fieldOrientedY + fieldOrientedX + rotation) / denominator;
        double frontRightPower = (fieldOrientedY - fieldOrientedX - rotation) / denominator;
        double backLeftPower = (fieldOrientedY - fieldOrientedX + rotation) / denominator;
        double backRightPower = (fieldOrientedY + fieldOrientedX - rotation) / denominator;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);


//        if (rotation < -0.01) {
//            rotationDirection = Constants.DrivetrainConstants.rotatingDirections.CLOCKWISE;
//        }
    }

//    public rotationDirections getRotatingDirection() {
//        return rotatingClockwise;
//    }

    //    public double getRawHeading() {
//        Orientation angles = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        return angles.firstAngle;
//    }
    public double getHeading() {
        double heading = IMU.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);;

        if(heading > 180) {
            heading -= 360;
        } else if (heading < -180) {
            heading += 360;
        }

        return heading;
    }

    public void resetYaw() {
        IMU.resetYaw();
    }

    public void stop() {
//        double yPow = drivePID.calculate(otos.getY(), otos.getY());
//        double xPow = strafePID.calculate(otos.getX(), otos.getX());
//        double hPow = turnPID.calculate(otos.getH(), otos.getH());

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

//        drive(yPow, xPow, hPow);
    }

    public void periodic() {
//        telemetry.addData("DRIVE: Clockwise?", getRotatingDirection());
//        m_telemetry.addData("DRIVE: Front Left Power: ", frontLeft.getPower());
//        m_telemetry.addData("DRIVE: Front Right Power: ", frontRight.getPower());
//        m_telemetry.addData("DRIVE: Back Left Power: ", backLeft.getPower());
//        m_telemetry.addData("DRIVE: Back Right Power: ", backRight.getPower());
    }


}
