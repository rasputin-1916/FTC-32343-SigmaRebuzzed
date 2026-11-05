package org.firstinspires.ftc.teamcode;

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

    public static final class intakeConstants {
        public static final String intakeID = "intake";
    }

    public static final class transferConstants {
        public static final String transferMotorID = "transferFirst";
        public static final String transferServoID = "transferSecond";
        public static final String servoAnalogID = "transferAnalog";

        //in motor rotations
        public static final double minMotorPos = 0.00;
        public static final double maxMotorPos = 2.15;
        public static final double minServoPos = 0.40;
        public static final double maxServoPos = 1.80;

        public static final double motorkP = 1.5;
        public static final double motorkI = 0.0;
        public static final double motorkD = 0.02;
        public static final double motorTolerance = 0.05;

        public static final double motorkS = 0.0;
        public static final double motorkV = 0.0;
        public static final double motorkG = 0.0;

        public static final double servokP = 1.0;
        public static final double servokI = 0.0;
        public static final double servokD = 0.0;
        public static final double servoTolerance = 0.05;
    }

    public static final class shooterConstants {
        public static final String shooterMotorID = "shooter";
        public static final String compressionServoID = "compression";
        public static final String hoodServoID = "hood";

        public static final double compressionMax = 0.26;
        public static final double compressionMin = 0.46;

        public static double maxSpeed = 1.0;
        public static double shooterkP = 0.0001;
        public static double shooterkI = 0.0;
        public static double shooterkD = 0.0;
        public static double shooterkS = 0.002;
        public static double shooterkV = 0.00019;
    }

    public static final class turretConstants {
        public static final String turretID = "turret";
        public static final double turretkP = 0.015;
        public static final double turretkI = 0.015;
        public static final double turretkD = 0.0004;
        public static final double turretTolerance = 0.5;
        public static final double turretMaxSpeed = 1.0;
    }

    public static final class clampConstants {
        public static final String frontClampID = "frontCornerServo";
        public static final String backClampID = "backCornerServo";
        public static final double closedFrontPosition = 0.4;
        public static final double openFrontPosition = 0.75;
        public static final double closedBackPosition = 0.65;
        public static final double openBackPosition = 1.0;
    }

}
