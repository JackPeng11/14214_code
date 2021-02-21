package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline;

import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.OneRingBehavior.doOneRingBehavior;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline.position;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.activateOpenCvCamera;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;

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
        
        if (position == RingDeterminationPipeline.RingPosition.FOUR)
        {
            //doFourRingsBehavior(this);
        }
        else if (position == RingDeterminationPipeline.RingPosition.ONE)
        {
            doOneRingBehavior(this);
        }
        else
        {
            //doNoRingsBehavior(this);
        }
    
        telemetry.addData("rings found:", position);
        telemetry.update();
        while (opModeIsActive())
        {
        
        }
    }
}

