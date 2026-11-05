package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Util.MathUtil;

public class IncrementLift {

    private Transfer s_Transfer;

    private double totalPercentage, motorPercentage, servoPercentage, newTotalPercentage;
    private double percentageError, motorError, servoError;
    private double motorSetpoint, servoSetpoint;


    public IncrementLift(Transfer s_Transfer) {
        this.s_Transfer = s_Transfer;
    }

    // Motor 9 + 7/16 in range of motion
    // Servo 4.9 in range of motion
    // 14.3375 in total range
    // 0.65823888404 % of rotation done by motor
    // 0.34176111595 % of rotation done by servo

    public void run(double inputPercentage) {

        newTotalPercentage = MathUtil.clamp(inputPercentage, 0, 1);

        motorSetpoint = newTotalPercentage * Constants.transferConstants.maxMotorPos;
        servoSetpoint = newTotalPercentage *
                (Constants.transferConstants.maxServoPos - Constants.transferConstants.minServoPos);

        s_Transfer.setMotorSetpoint(motorSetpoint);
        s_Transfer.setServoSetpoint(servoSetpoint + Constants.transferConstants.minServoPos);
    }

}
