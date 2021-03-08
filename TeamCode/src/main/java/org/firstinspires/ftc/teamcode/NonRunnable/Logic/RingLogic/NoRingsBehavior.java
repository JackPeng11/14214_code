package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.moveWobbleArmDown;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.moveWobbleArmUp;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.releaseWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;

public final class NoRingsBehavior
{
    private NoRingsBehavior()
    {
    }
    
    public static void doNoRingsBehavior(LinearOpMode opMode)
    {
        DrivePath moveForwardOntoLaunchLine = new DrivePath(0.3, 26, DriveMode.FORWARD, opMode);
        DrivePath strafeToZone              = new DrivePath(0.3, 8, DriveMode.STRAFE_LEFT, opMode);
        DrivePath releaseByMovingBack       = new DrivePath(0.3, 4, DriveMode.BACKWARD, opMode);
    
        moveForwardOntoLaunchLine.go();
        strafeToZone.go();
        moveWobbleArmDown(opMode);
        releaseWobbleGoal(opMode);
        releaseByMovingBack.go();
        moveWobbleArmUp(opMode);
    }
}
