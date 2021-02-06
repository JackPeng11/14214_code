package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.moveArm;

@Autonomous

public class RobotArm extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        initHardware(RobotArm.this);
        telemetry.addData("Mode", "ready");
        telemetry.update();
        
        waitForStart();
        
        moveArm(RobotArm.this);
    }
}
