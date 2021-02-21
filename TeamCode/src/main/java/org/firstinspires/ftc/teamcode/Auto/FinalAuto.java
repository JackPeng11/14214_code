package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline.position;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.activateOpenCvCamera;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;

@Autonomous
public class FinalAuto extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        activateOpenCvCamera(FinalAuto.this);
        initHardware(FinalAuto.this);
        
        telemetry.addData("Mode", "Ready");
        telemetry.update();
        
        waitForStart();
        
        telemetry.addData("rings found:", position);
        telemetry.update();
        while (opModeIsActive())
        {
        
        }
//        if (position == RingPosition.FOUR)
//        {
//            doFourRingsBehavior(FinalAuto.this);
//        }
//        else if (position == RingPosition.ONE)
//        {
//            doOneRingBehavior(FinalAuto.this);
//        }
//        else
//        {
//            doNoRingsBehavior(FinalAuto.this);
//        }
    }
}

