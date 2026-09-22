package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Util.PIDController;

public class Shooter {

    private DcMotorEx shooterMotor;
    private Servo compression;

//    private Servo hood;
    private PIDController shooterController;

    private double setpoint;

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

    public double getRPM() {
        return (shooterMotor.getVelocity() / 24) * 60;
    }

    public void runShooter() {
        shooterMotor.setPower(
                Math.max(Math.min(shooterController.calculate(getRPM(), setpoint) +
                        calculateFF(Constants.shooterConstants.shooterkS, Constants.shooterConstants.shooterkV),
                        Constants.shooterConstants.maxSpeed), 0));

    }

    public double calculateFF(double kS, double kV) {
        return (kS * Math.signum(setpoint) + kV * setpoint);
    }

    public void setSetpoint(double newSetpoint) {
        setpoint = newSetpoint;
    }

//    public double getHoodPosition() {
//        return hood.getPosition();
//    }
//
//    public void setHoodPositonUp() {
//        hood.setPosition(0.0);
//    }
//
//    public void setHoodPositonDown() {
//        hood.setPosition(0.4);
//    }

    public double getCompression() {
        return compression.getPosition();
    }

    public void setCompression(double newCompression) {
        compression.setPosition(newCompression);
    }

}
