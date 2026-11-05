package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Util.MathUtil;
import org.firstinspires.ftc.teamcode.Util.PIDController;

public class Turret {

    private DcMotorEx turretMotor;
    private PIDController turretController;
    private Drivetrain drive;
    private double setpoint;

    public Turret(HardwareMap hardwareMap, Drivetrain drive) {
        turretMotor = hardwareMap.get(DcMotorEx.class, Constants.turretConstants.turretID);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        turretController = new PIDController(
                Constants.turretConstants.turretkP,
                Constants.turretConstants.turretkI,
                Constants.turretConstants.turretkD
        );
        turretController.enableContinuousInput(-180, 180);

        this.drive = drive;
    }

    public double wrapAngle(double angle) {
        angle = angle % 360;

        if (angle > 180)
            angle -= 360;

        if (angle < -180)
            angle += 360;

        return angle;
    }

    public double getAngleRR() {
        double rotations = turretMotor.getCurrentPosition() / (24 * 3.7 * (126.0/16.0));
        //Declan was here
        double degrees = rotations * 360;
        return wrapAngle(degrees);
    }

    public double getAngleFR() {
        return wrapAngle(getAngleRR() - drive.getHeading());
    }

    public void setAngleFR(double newSetpoint) {
        if (!(Math.abs(newSetpoint - getAngleFR()) < Constants.turretConstants.turretTolerance)) {
            turretMotor.setPower(MathUtil.clamp(
                    turretController.calculate(getAngleFR(), newSetpoint),
                    -Constants.turretConstants.turretMaxSpeed,
                    Constants.turretConstants.turretMaxSpeed
            ));
        } else {
            turretMotor.setPower(0);
        }
    }

    public void setSetpoint(double newSetpoint) {
        setpoint = wrapAngle(newSetpoint);
    }

    public double getSetpointFR() {
        return setpoint;
    }

    public double getSetpointRR() {
        return setpoint + drive.getHeading();
    }

    public boolean atSetpoint() {
        return Math.abs(setpoint - getAngleFR()) < Constants.turretConstants.turretTolerance;
    }

    public void periodic() {
        setAngleFR(setpoint);
    }
}
