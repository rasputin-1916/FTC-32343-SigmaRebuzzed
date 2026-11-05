package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoTest {

    private Servo test;

    public ServoTest(HardwareMap hardwareMap) {
        test = hardwareMap.get(Servo.class, "testservo");
        holdServo();
    }

    public void holdServo() {
        test.setPosition(0.5);
    }
}
