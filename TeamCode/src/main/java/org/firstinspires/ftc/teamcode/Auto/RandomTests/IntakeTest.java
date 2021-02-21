package org.firstinspires.ftc.teamcode.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.IntakeFunctions.preventRingsFromPassing;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.IntakeFunctions.shoot;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.MAX_COUNTS_PER_SECOND;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.tubeIntake;

@Autonomous
public class IntakeTest extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        initHardware(IntakeTest.this);
        showReady(IntakeTest.this);
        waitForStart();
        
        preventRingsFromPassing(IntakeTest.this);
        
        //start intake and flywheel
        tubeIntake.setPower(0.95);
        spinner.setVelocity(0.2 * MAX_COUNTS_PER_SECOND);
        flyWheel.setVelocity(0.79 * MAX_COUNTS_PER_SECOND);
        sleep(4000);
        
        //stop intake and spinner
        tubeIntake.setPower(0);
        spinner.setPower(0);
        
        shoot(IntakeTest.this,3000);
        
        //reset guide and flap to open position
        preventRingsFromPassing(IntakeTest.this);
    }
}
