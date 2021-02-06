package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.DrivePath;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.phoneCam;

public final class FourRingsBehavior
{
    private FourRingsBehavior()
    {
    }
    
    public static void doFourRingsBehavior(LinearOpMode opMode)
    {
        phoneCam.closeCameraDevice();
        DrivePath moveForward = new DrivePath(0.3, 4, NvyusRobotConstants.DriveMode.FORWARD, opMode);
        moveForward.go();
    }
}
