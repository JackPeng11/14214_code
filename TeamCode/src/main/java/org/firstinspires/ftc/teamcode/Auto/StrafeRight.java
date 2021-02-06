package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.DriveMode.STRAFE_RIGHT;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;

@Autonomous
public class StrafeRight extends LinearOpMode
{
    
    @Override
    public void runOpMode()
    {
        initHardware(this);
        
        DrivePath path = new DrivePath(0.9, 72, STRAFE_RIGHT, StrafeRight.this);
        showReady(StrafeRight.this);
        waitForStart();
        
        telemetry.clearAll();
        
        path.go();
        path.showCycleStats();
        //correctToHeading(0);
        
        while (opModeIsActive())
        {
        }
        
    }
}
