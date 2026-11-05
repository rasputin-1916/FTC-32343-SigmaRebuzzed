package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Util.MathUtil;
import org.firstinspires.ftc.teamcode.Util.PIDController;

public class Transfer {

    private DcMotorEx firstStage;
    private CRServo secondStage;
    private PIDController firstStageController, secondStageController;
    private AnalogInput servoPosition;
    private double motorSetpoint, servoSetpoint;
    private double lastServoPosition, storedRotations;

    public Transfer(HardwareMap hardwareMap) {
        firstStage = hardwareMap.get(DcMotorEx.class, Constants.transferConstants.transferMotorID);
        firstStage.setDirection(DcMotorSimple.Direction.REVERSE);
        firstStage.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        firstStage.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        secondStage = hardwareMap.get(CRServo.class, Constants.transferConstants.transferServoID);
        secondStage.setDirection(DcMotorSimple.Direction.REVERSE);

        servoPosition = hardwareMap.get(AnalogInput.class, Constants.transferConstants.servoAnalogID);

        firstStageController = new PIDController(
                Constants.transferConstants.motorkP,
                Constants.transferConstants.motorkI,
                Constants.transferConstants.motorkD
        );

        secondStageController = new PIDController(
                Constants.transferConstants.servokP,
                Constants.transferConstants.servokI,
                Constants.transferConstants.servokD
        );

        storedRotations = 0;
        lastServoPosition = servoPosition.getVoltage() / 3.3;
    }

    public double calculateFF(double kS, double kV, double kG, double error) {
        return (kS * Math.signum(error)) + (kV * error) + kG;
    }

    public void setMotorPostition(double newSetpoint) {
        double output = firstStageController.calculate(getMotorPosition(), newSetpoint)
                + calculateFF(Constants.transferConstants.motorkS,
                Constants.transferConstants.motorkV,
                Constants.transferConstants.motorkG,
                Math.abs(newSetpoint - getMotorPosition()));

        if (!(Math.abs(newSetpoint - getMotorPosition()) < Constants.transferConstants.motorTolerance)) {
            if (getMotorPosition() < Constants.transferConstants.minMotorPos) {
                firstStage.setPower(Math.max(output, 0));
            } else if (getMotorPosition() > Constants.transferConstants.maxMotorPos) {
                firstStage.setPower(Math.min(output, 0));
            } else {
                firstStage.setPower(output);
            }
        } else {
            firstStage.setPower(0);
        }
    }

    public double getMotorPosition() {
        return (firstStage.getCurrentPosition()/ (24.0 * 20.0));
    }

    public void setRawServoSpeed(double newSpeed) {
        secondStage.setPower(newSpeed);
    }

    public void setServoPosition(double newPosition) {
        double output = secondStageController.calculate(getServoPosition(), newPosition);

        if (!(Math.abs(newPosition - getServoPosition()) < Constants.transferConstants.servoTolerance)) {
            if (getServoPosition() < Constants.transferConstants.minServoPos) {
                secondStage.setPower(Math.max(output, 0));
            } else if (getServoPosition() > Constants.transferConstants.maxServoPos) {
                secondStage.setPower(Math.min(output, 0));
            } else {
                secondStage.setPower(output);
            }
        } else {
            secondStage.setPower(0);
        }
    }

    public double getRawServoPosition() {
        return servoPosition.getVoltage();
    }

    public double getServoPosition() {
        return lastServoPosition + storedRotations;
    }

    public double getServoSpeed() {
        return secondStage.getPower();
    }

    public void setMotorSetpoint(double newSetpoint) {
        motorSetpoint = newSetpoint;
    }

    public double getMotorSetpoint() {
        return motorSetpoint;
    }

    public boolean atMotorSetpoint() {
        return Math.abs(motorSetpoint - getMotorPosition()) < Constants.transferConstants.motorTolerance;
    }

    public void setServoSetpoint(double newSetpoint) {
        servoSetpoint = newSetpoint;
    }

    public double getServoSetpoint() {
        return servoSetpoint;
    }

    public boolean atServoSetpoint() {
        return Math.abs(servoSetpoint - getServoPosition()) < Constants.transferConstants.servoTolerance;
    }

    public void periodic() {

        double servoError = (servoPosition.getVoltage() / 3.3) - lastServoPosition;

        if (servoError > 0.5) {
            storedRotations--;
        } else if (servoError < -0.5){
            storedRotations++;
        }

        lastServoPosition = (servoPosition.getVoltage() / 3.3);

        setMotorPostition(motorSetpoint);
        setServoPosition(servoSetpoint);
    }
}
