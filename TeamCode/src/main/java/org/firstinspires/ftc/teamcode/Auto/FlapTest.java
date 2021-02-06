package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.*;

@Autonomous
public class FlapTest extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        initHardware(FlapTest.this);
        telemetry.addData("Mode", "Ready");
        telemetry.update();

        waitForStart();

        //set guide and flap to open position
        guide.setPosition(0.99);
        sleep(400);
        idle();
        flap.setPosition(1.0);
        sleep(700);
        idle();

        //set flap to closed position
        flap.setPosition(0.65);
        sleep(500);
        idle();

        //set guide to closed position
        guide.setPosition(0.88);
        sleep(400);
        idle();
    }
}