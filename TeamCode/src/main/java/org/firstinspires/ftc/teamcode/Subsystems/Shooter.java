package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Util.PIDController;

import java.util.Timer;

public class Shooter {

    private DcMotorEx shooterMotor;
    private Servo compression;

//    private Servo hood;
    private PIDController shooterController;

    private double setpoint;
    private double compressionSetpoint;
    private double timestamp;

    public Shooter (HardwareMap hardwaremap) {
        shooterMotor = hardwaremap.get(DcMotorEx.class, Constants.shooterConstants.shooterMotorID);
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        hood = hardwaremap.get(Servo.class, Constants.shooterConstants.hoodServoID);
        compression = hardwaremap.get(Servo.class, Constants.shooterConstants.compressionServoID);

        shooterController = new PIDController(
                Constants.shooterConstants.shooterkP,
                Constants.shooterConstants.shooterkI,
                Constants.shooterConstants.shooterkD);
    }

    public void setPower(double desiredPower) {
        shooterMotor.setPower(desiredPower);
    }
    public double getPower() {
        return shooterMotor.getPower();
    }
    public double getVelocity()  {
        return shooterMotor.getVelocity();
    }


    public double getRPM() {
        return (shooterMotor.getVelocity() / 24) * 60;
    }

    public void runShooter() {
        shooterMotor.setPower(
                Math.max(Math.min(shooterController.calculate(getVelocity(), (setpoint / 60) * 24) +
                        calculateFF(Constants.shooterConstants.shooterkS, Constants.shooterConstants.shooterkV),
                        Constants.shooterConstants.maxSpeed), 0));

    }

    public double calculateFF(double kS, double kV) {
        return (kS * Math.signum(setpoint) + kV * setpoint);
    }

    public void setSetpoint(double newSetpoint) {
        setpoint = newSetpoint;
    }

    public double getCompression() {
        return compression.getPosition();
    }

    public void setCompression(double newCompression) {
        compressionSetpoint = newCompression;
        timestamp = System.currentTimeMillis();
    }

    public void periodic() {
        compression.setPosition(compressionSetpoint);
        if (Math.abs(compressionSetpoint - getCompression()) < 0.05) {
            timestamp = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() > timestamp + 1000) {
            setCompression(getCompression());
        }
    }
}
