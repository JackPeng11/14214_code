package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.IntakeFunctions.shoot;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.gripWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline.position;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.highGoalSpeed;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.activateOpenCvCamera;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.phoneCam;

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
    
        DrivePath strafeRightAtBeginning = new DrivePath(0.4, 19, Constants.DriveMode.STRAFE_RIGHT, this);
        DrivePath advanceToShootingLine  = new DrivePath(0.4, 62, Constants.DriveMode.FORWARD, this);
        DrivePath strafeToAim            = new DrivePath(0.4, 25, Constants.DriveMode.STRAFE_LEFT, this);
    
        phoneCam.closeCameraDevice();
        gripWobbleGoal(this);
        strafeRightAtBeginning.go();
        correctToHeading(0);
        advanceToShootingLine.go();
        correctToHeading(0);
    
        setVelocity(flyWheel, highGoalSpeed); // >=12V: 0.17 <=11V: 0.19
        strafeToAim.go();
        correctToHeading(0);
        shoot(this, 1800);
    
        //        if (position == RingDeterminationPipeline.RingPosition.FOUR)
        //        {
        //            doFourRingsBehavior(this);
        //        }
        //        else if (position == RingDeterminationPipeline.RingPosition.ONE)
        //        {
        //            doOneRingBehavior(this);
        //        }
        //        else
        //        {
        //            doNoRingsBehavior(this);
        //        }
    
        telemetry.addData("rings found:", position);
        telemetry.update();
        while (opModeIsActive())
        {
        
        }
    }
}

