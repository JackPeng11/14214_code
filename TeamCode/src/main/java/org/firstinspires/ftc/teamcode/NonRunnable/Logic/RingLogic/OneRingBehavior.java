package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.phoneCam;

public final class OneRingBehavior
{
    private OneRingBehavior()
    {
    }
    
    public static void doOneRingBehavior(LinearOpMode opMode)
    {
        phoneCam.closeCameraDevice();
        DrivePath advanceToShootingLine  = new DrivePath(0.4, 69, DriveMode.FORWARD, opMode);
        DrivePath strafeRightAtBeginning = new DrivePath(0.4, 16, DriveMode.STRAFE_RIGHT, opMode);
        strafeRightAtBeginning.go();
        correctToHeading(0);
        advanceToShootingLine.go();
        
    }
}
