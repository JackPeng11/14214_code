package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.phoneCam;

public final class NoRingsBehavior
{
    private NoRingsBehavior()
    {
    }
    
    public static void doNoRingsBehavior(LinearOpMode opMode)
    {
        phoneCam.closeCameraDevice();
        DrivePath moveForward = new DrivePath(0.3, 6, DriveMode.FORWARD, opMode);
        moveForward.go();
    }
}
