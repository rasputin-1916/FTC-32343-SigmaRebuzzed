package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Constants;

public class Odometry extends SubsystemBase {
    GoBildaPinpointDriver pinpoint;

    public Odometry(HardwareMap hardwaremap) {
        pinpoint = hardwaremap.get(GoBildaPinpointDriver.class, Constants.odometryConstants.pinpointID);

        pinpoint.setOffsets(Constants.odometryConstants.xOffset, Constants.odometryConstants.yOffset, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
    }

    public Pose2D getPose() {
        return pinpoint.getPosition();
//        return new Pose2d(
//                pinpoint.getPosX(DistanceUnit.INCH),
//                pinpoint.getPosY(DistanceUnit.INCH),
//                Rotation2d.fromDegrees(pinpoint.getHeading(AngleUnit.DEGREES)));
    }

    public void periodic() {
        pinpoint.update();
    }




}
