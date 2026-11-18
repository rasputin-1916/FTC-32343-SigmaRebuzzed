package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Translation2d;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import java.util.function.BooleanSupplier;

public class CalculateShot {

    private Shooter s_Shooter;
    private Turret s_Turret;
    private Odometry s_Odom;
    private boolean isRed, isNectar;
    private BooleanSupplier compressionIn, compressionOut;
    private Translation2d robotPosition, targetPosition;
    private double turretAngle, yError, xError, distance;

    public CalculateShot(Shooter s_Shooter,
                         Turret s_Turret,
                         Odometry s_Odom,
                         boolean isRed,
                         BooleanSupplier compressionIn,
                         BooleanSupplier compressionOut) {

        this.s_Shooter = s_Shooter;
        this.s_Turret = s_Turret;
        this.s_Odom = s_Odom;
        this.isRed = isRed;
        this.compressionIn = compressionIn;
        this.compressionOut = compressionOut;
    }

    public void run() {
        robotPosition = new Translation2d(s_Odom.getX(), s_Odom.getY());

        if (compressionIn.getAsBoolean()) {
            isNectar = false;
        }

        if (compressionOut.getAsBoolean()) {
            isNectar = true;
        }


        if (robotPosition.getY() > 0) {
            if (isRed) {
                targetPosition = Constants.targetingConstants.positiveYRedGoal;
            } else {
                targetPosition = Constants.targetingConstants.positiveYBlueGoal;
            }
        } else {
            if (isRed) {
                targetPosition = Constants.targetingConstants.negativeYRedGoal;
            } else {
                targetPosition = Constants.targetingConstants.negativeYBlueGoal;
            }
        }

        yError = targetPosition.getY() - robotPosition.getY();
        xError = targetPosition.getX() - robotPosition.getX();
        distance = Math.hypot(yError, xError);
        turretAngle = (-s_Turret.wrapAngle(Math.toDegrees(Math.atan2(yError, xError))) + 90);

        //y=0.0693741x^{2}+5.98592x+1542.54257 pollen
        //0.0339231x^{2}+9.48098x+1763.02031 nectar

        if (isNectar) {
            s_Shooter.setSetpoint((0.0339231 * Math.pow(distance, 2)) + (9.48098 * distance) + 1763.02031);
        } else {
            s_Shooter.setSetpoint((0.0693741 * Math.pow(distance, 2)) + (5.98592 * distance) + 1542.54257);
        }

        s_Turret.setSetpoint(turretAngle);
    }

}
