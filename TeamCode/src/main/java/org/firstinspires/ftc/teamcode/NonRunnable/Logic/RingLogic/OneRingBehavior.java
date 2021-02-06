package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.phoneCam;

public final class OneRingBehavior
{
    private OneRingBehavior()
    {
    }
    
    public static void doOneRingBehavior(LinearOpMode opMode)
    {
        phoneCam.closeCameraDevice();
        DrivePath moveForward = new DrivePath(0.4, 6, DriveMode.FORWARD, opMode);
        moveForward.go();
    }
}
