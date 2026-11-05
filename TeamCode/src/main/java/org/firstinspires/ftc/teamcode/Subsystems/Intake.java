package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {

    private CRServo leftServo, rightServo;
    private DcMotorEx intake;
    public Intake(HardwareMap hardwaremap) {
        intake = hardwaremap.get(DcMotorEx.class, Constants.intakeConstants.intakeID);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        leftServo = hardwaremap.get(CRServo.class, "leftServo");
        rightServo = hardwaremap.get(CRServo.class, "RightServo");

        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        rightServo.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setIntakeMotor(double speed) {
        intake.setPower(speed);
    }

    public void intake() {
        leftServo.setPower(1.0);
        rightServo.setPower(1.0);
    }

    public void outtake() {
        leftServo.setPower(-1.0);
        rightServo.setPower(-1.0);
    }

    public void stop() {
        leftServo.setPower(0.0);
        rightServo.setPower(0.0);
    }
}
