package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {
    private DcMotorEx intakeMotor;
    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, Constants.intakeConstants.intakeMotorID);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void stop() {
        intakeMotor.setPower(0);
    }

    public void setPower(double desiredPower) {
        intakeMotor.setPower(desiredPower);
    }

    public double getPower() {
        return intakeMotor.getPower();
    }

    public double getVelocity(){
        return intakeMotor.getVelocity();
    }
}
