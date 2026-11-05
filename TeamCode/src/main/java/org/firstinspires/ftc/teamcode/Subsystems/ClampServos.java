package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class ClampServos {

    private Servo frontCornerServo, backCornerServo;
    public ClampServos(HardwareMap hardwareMap)  {
        frontCornerServo = hardwareMap.get(Servo.class, Constants.clampConstants.frontClampID);
        backCornerServo = hardwareMap.get(Servo.class, Constants.clampConstants.backClampID);
    }

    public void setFrontCornerServo(double newPosition) {
        frontCornerServo.setPosition(newPosition);
    }

    public void setBackCornerServo(double newPosition) {
        backCornerServo.setPosition(newPosition);
    }

    public double getFrontCornerServo() {
        return frontCornerServo.getPosition();
    }

    public double getBackCornerServo() {
        return backCornerServo.getPosition();
    }
}
