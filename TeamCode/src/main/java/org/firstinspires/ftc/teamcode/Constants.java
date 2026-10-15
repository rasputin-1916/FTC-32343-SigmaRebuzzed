package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

public class Constants {

    public static final class driveConstants {

        public static final String frontLeftMotorID = "fl";
        public static final String frontRightMotorID = "fr";
        public static final String backLeftMotorID = "bl";
        public static final String backRightMotorID = "br";

        public static final double drivekP = 0.00;
        public static final double drivekI = 0.00;
        public static final double drivekD = 0.00;
        public static final double driveTolerance = 1;

        public static final double rotationkP = 0.00;
        public static final double rotationkI = 0.00;
        public static final double rotationkD = 0.00;
        public static final double rotationTolerance = 1;
    }

    public static final class odometryConstants {
        public static final String pinpointID = "pinpoint";
        public static final double trackwidth = 11 + (3.0/8.0);
        public static final double xOffset = 5.618110;
        //5.618110
        public static final double yOffset = -5.779771;
        //-5.779771
        public static final double ticksPerRev = 2000.0;
        public static final double wheelDiameter = 1.88976;
        public static final double distancePerPulse = (Math.PI * wheelDiameter * ticksPerRev);

    }

    public static final class shooterConstants {
        public static final String shooterMotorID = "shooter";
        public static final String compressionServoID = "compression";
        public static final String hoodServoID = "hood";

        public static final double compressionMax = 0.10;
        public static final double compressionMin = 0.50;

        public static double maxSpeed = 1.0;
        public static double shooterkP = 0.0001;
        public static double shooterkI = 0.0;
        public static double shooterkD = 0.0;
        public static double shooterkS = 0.002;
        public static double shooterkV = 0.00019;
    }

}
