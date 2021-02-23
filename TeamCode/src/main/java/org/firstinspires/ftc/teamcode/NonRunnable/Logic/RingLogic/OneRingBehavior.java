package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.turn;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.moveWobbleArmDown;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.moveWobbleArmUp;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.releaseWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;

public final class OneRingBehavior
{
    private OneRingBehavior()
    {
    }
    
    public static void doOneRingBehavior(LinearOpMode opMode)
    {
        DrivePath strafeToWobbleZone = new DrivePath(0.4, 16, DriveMode.STRAFE_LEFT, opMode);
    
        turn(-90);
        strafeToWobbleZone.go();
        moveWobbleArmDown(opMode);
        releaseWobbleGoal(opMode);
        moveWobbleArmUp(opMode);
    
    }
}
