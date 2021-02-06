package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.guide;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.MAX_COUNTS_PER_SECOND;

@Autonomous
public class ShooterAuto extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        initHardware(ShooterAuto.this);
        telemetry.addData("Mode", "ready");
        telemetry.update();
        waitForStart();
        
        telemetry.addData("Mode", "running");
        telemetry.update();
        
        //First set guide into position while spinning up flywheel
        guide.setPosition(0.9);
        sleep(200);
        idle();
        flyWheel.setVelocity(0.74 * MAX_COUNTS_PER_SECOND);
        sleep(2000);
        
        //start up spinner
        spinner.setVelocity(0.6 * MAX_COUNTS_PER_SECOND);
        sleep(5000);
    }
}