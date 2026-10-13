package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Constants;

public class Odometry {

    GoBildaPinpointDriver pinpoint;
    public Odometry(HardwareMap hardwareMap) {

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        pinpoint.setOffsets(Constants.odometryConstants.xOffset, Constants.odometryConstants.yOffset, DistanceUnit.INCH); //these are tuned for 3110-0002-0001 Product Insight #1
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
    }

    public Pose2D getPosition() {
        return pinpoint.getPosition();
    }

    public double getX() {
        return -(getPosition().getX(DistanceUnit.INCH));
    }

    public double getY() {
        return -(getPosition().getY(DistanceUnit.INCH));
    }

    public double getRotation() {
        return -(getPosition().getHeading(AngleUnit.DEGREES));
    }

    public void resetPosition() {
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,0,0, AngleUnit.DEGREES, 0));
    }

    public void periodic() {
        pinpoint.update();
    }


}
