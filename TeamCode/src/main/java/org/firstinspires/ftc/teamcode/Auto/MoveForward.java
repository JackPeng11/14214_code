package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;

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
