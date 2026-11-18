package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptScanServo;
import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Util.LimitedQueue;
import org.firstinspires.ftc.teamcode.Util.MathUtil;

import java.util.ArrayList;
import java.util.Optional;

public class GamePieceQueue {

    private IncrementLift m_Lift;
    private Transfer s_Transfer;
    private Shooter s_Shooter;
    private GamepadEx operatorInput;
    private ArrayList<Boolean> booleanlist;
    private double pieceDistance, currentDistance, deadspace, totalDistance;
    private double timestamp;
    private boolean isFinished;
    private ArrayList<Double> distancelist;

    public GamePieceQueue(IncrementLift m_Lift, Transfer s_Transfer, Shooter s_Shooter, GamepadEx operatorInput) {
        this.m_Lift = m_Lift;
        this.s_Transfer = s_Transfer;
        this.s_Shooter = s_Shooter;
        this.operatorInput = operatorInput;

        booleanlist = new ArrayList<Boolean>();
        distancelist = new ArrayList<Double>();
    }

    public void addPollen() {
        booleanlist.add(false);
    }

    public void addNectar() {
        booleanlist.add(true);
    }

    public void clear() {
        booleanlist.clear();
        distancelist.clear();
        currentDistance = 0;
        isFinished = false;
    }



    public void calculateDistances() {
        if (booleanlist.size() >= 4 && !isFinished) {
            distancelist.add(getDeadSpace());
            for (int i = booleanlist.size() - 4; i < booleanlist.size(); i++) {
                if (booleanlist.get(i) == true) {
                    pieceDistance = Constants.transferConstants.nectarSize;
                } else {
                    pieceDistance = Constants.transferConstants.pollenSize;
                }
                currentDistance += pieceDistance;
                if (i == booleanlist.size() - 1 || booleanlist.get(i) != booleanlist.get(i + 1)) {
                    distancelist.add(currentDistance);
                    currentDistance = 0;
                    if (i == booleanlist.size() - 1) {
                        isFinished = true;
                    }
                }
            }
        }
    }

    public double getDeadSpace() {
        deadspace = 1;
        if (booleanlist.size() >= 4) {
            for (int i = booleanlist.size() - 4; i < booleanlist.size(); i++) {
                if (booleanlist.get(i)) {
                    deadspace -= Constants.transferConstants.nectarSize;
                } else {
                    deadspace -= Constants.transferConstants.pollenSize;
                }
            }
        }
        return deadspace;
    }


    // add distance up until the next item in the array list does not equal the current
    // then add the values up to the next slot the distance array list
    // the variable the was used to set the distance should reset to 0;
    // total distance calculations should be done first
    // order 1: Find total distance 2: remove dead space 3: move up in a loop

    public ArrayList<Double> getDistanceList() {
        return distancelist;
    }

    public ArrayList<Boolean> getBooleanlist() {
        return booleanlist;
    }
}
