package org.firstinspires.ftc.teamcode.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;

@Autonomous
public class MoveForward extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        initHardware(MoveForward.this);
        DrivePath moveForward = new DrivePath(0.9, 24, DriveMode.FORWARD, MoveForward.this);
        telemetry.addData("Mode", "Ready");
        telemetry.update();
        waitForStart();
        
        moveForward.go();
    }
}
