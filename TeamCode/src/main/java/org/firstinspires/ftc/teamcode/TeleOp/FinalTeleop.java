package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FLAP_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FLAP_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.highGoalSpeed;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.powerShotSpeed;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flap;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.guide;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.tubeIntake;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;

@TeleOp
public final class FinalTeleop extends LinearOpMode
{
    double forwardComponent;
    double strafeComponent;
    double rotationComponent;
    
    double currentTime;
    
    double[] teleopVelocityArray = new double[4];
    
    Button toggleSlowMode    = new Button();
    Button toggleRingFlow    = new Button();
    Button toggleWobbleServo = new Button();
    Button zeroAngle         = new Button();
    Button powerShotToggle   = new Button();
    
    boolean slowMode = false;
    
    boolean firstPartDone;
    boolean secondPartDone;
    boolean thirdPartDone;
    
    int toggleRingFlowCount = 0;
    private boolean currentWobblePos = false;
    private boolean leftPosReached   = true;
    private boolean centerPosReached = true;
    private boolean rightPosReached  = true;
    private boolean powershot        = false;
    
    @Override
    public void runOpMode()
    {
        initHardware(this);
        showReady(this);
        
        waitForStart();
        
        while (opModeIsActive())
        {
            telemetry.addData("slowmode", slowMode);
            telemetry.addData("powershot speed", powershot);
            telemetry.addData("angle", getAngle());
            telemetry.update();
            //-8 right, 0 middle, 5 left
            drive();
            controlIntake();
            controlShooter();
            controlWobbleArm();
            powerShot();
            setZeroAngle();
        }
    }
    
    private void drive()
    {
        forwardComponent = -gamepad1.left_stick_y;
        strafeComponent = gamepad1.left_stick_x;
        rotationComponent = 0.7 * gamepad1.right_stick_x;
        
        teleopVelocityArray[0] = forwardComponent + strafeComponent + rotationComponent;
        teleopVelocityArray[1] = forwardComponent - strafeComponent - rotationComponent;
        teleopVelocityArray[2] = forwardComponent - strafeComponent + rotationComponent;
        teleopVelocityArray[3] = forwardComponent + strafeComponent - rotationComponent;
    
        normalizeVelocities();
    
        if (toggleSlowMode.isPressed(gamepad1.y))
        {
            slowMode = !slowMode;
        }
        if (forwardComponent == 0 && strafeComponent == 0 && rotationComponent == 0)
        {
            if (!(gamepad1.dpad_left || gamepad1.dpad_up || gamepad1.dpad_right))
            {
                setDriveMotorsVelocity(0);
            }
        }
        else
        {
            setDriveMotorsVelocity(teleopVelocityArray);
        }
    }
    
    private void controlShooter()
    {
        if (powerShotToggle.isPressed(gamepad2.x))
        {
            powershot = !powershot;
        }
        
        if (gamepad2.right_trigger > 0.5)
        {
            if (powershot)
            {
                setVelocity(flyWheel, powerShotSpeed);
            }
            else
            {
                setVelocity(flyWheel, highGoalSpeed);
            }
            ++toggleRingFlowCount;
            
            if (toggleRingFlowCount == 1)
            {
                firstPartDone = false;
                secondPartDone = false;
                thirdPartDone = false;
            }
            
            if (!firstPartDone)
            {
                flap.setPosition(FLAP_CLOSED_POSITION);
                sleep(0);
                idle();
                firstPartDone = true;
                currentTime = getRuntime();
            }
            else if (!secondPartDone && (getRuntime() > currentTime + 0.3))
            {
                flap.setPosition(FLAP_OPEN_POSITION);
                sleep(0);
                idle();
                secondPartDone = true;
                currentTime = getRuntime();
            }
            else if (!thirdPartDone && (getRuntime() > currentTime + 0.3))
            {
                guide.setPosition(GUIDE_CLOSED_POSITION);
                sleep(0);
                idle();
            }
            
            if (gamepad2.left_bumper)
            {
                setVelocity(spinner, 0.4);
            }
        }
        else
        {
            setVelocity(flyWheel, 0);
            guide.setPosition(GUIDE_OPEN_POSITION);
            sleep(0);
            idle();
            toggleRingFlow.isFinished();
            toggleRingFlowCount = 0;
        }
    }
    
    private void powerShot()
    {
        double angleError;
        int    direction;
        if (gamepad1.dpad_left || !leftPosReached)
        {
            setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            setDriveMotorsVelocity(0.4);
            sleep(158);
            setDriveMotorsVelocity(0);
            
            //            angleError = 5 - getAngle();
            //            direction = (int) (angleError / Math.abs(angleError));
            //            if (angleError > 2)
            //            {
            //                setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            //                setDriveMotorsVelocity(0.14 * direction);
            //            }
            //            else
            //            {
            //                leftPosReached = true;
            //            }
        }
        else if (gamepad1.dpad_up || !centerPosReached)
        {
            angleError = 0 - getAngle();
            direction = (int) (angleError / Math.abs(angleError));
            if (angleError > 2)
            {
                setDriveDirection(Constants.DriveMode.ROTATE_CCW);
                setDriveMotorsVelocity(0.14 * direction);
            }
            else
            {
                centerPosReached = true;
            }
        }
        else if (gamepad1.dpad_right || !rightPosReached)
        {
            setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            setDriveMotorsVelocity(-0.4);
            sleep(158);
            setDriveMotorsVelocity(0);
            
            //            angleError = -5 - getAngle();
            //            direction = (int) (angleError / Math.abs(angleError));
            //            if (angleError < -2)
            //            {
            //                setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            //                setDriveMotorsVelocity(0.14 * direction);
            //            }
            //            else
            //            {
            //                rightPosReached = true;
            //            }
        }
        setDriveDirection(Constants.DriveMode.FORWARD);
    }
    
    private void controlIntake()
    {
        if (gamepad2.left_bumper)
        {
            if (gamepad2.dpad_down)
            {
                tubeIntake.setPower(-1);
            }
            else
            {
                tubeIntake.setPower(1);
            }
        }
        else
        {
            tubeIntake.setPower(0);
        }
        
        if (gamepad2.dpad_up)
        {
            setVelocity(spinner, 0.2);
        }
        else if (gamepad2.dpad_down)
        {
            setVelocity(spinner, -0.4);
        }
        else if (!((gamepad2.right_trigger > 0.5) && gamepad2.left_bumper))
        {
            setVelocity(spinner, 0);
        }
    }
    
    private void setZeroAngle()
    {
        if (zeroAngle.isPressed(gamepad1.a))
        {
            resetAngle();
        }
    }
    
    public void controlWobbleArm()
    {
        if (toggleWobbleServo.isPressed(gamepad2.left_stick_button))
        {
            if (currentWobblePos)
            {
                wobble.setPosition(WOBBLE_OPEN_POSITION);
            }
            else
            {
                wobble.setPosition(WOBBLE_CLOSED_POSITION);
            }
            currentWobblePos = !currentWobblePos;
            sleep(0);
            idle();
        }
        
        if (gamepad2.right_stick_y < -0.3)
        {
            setVelocity(wobbleArm, -0.25);
        }
        else if (gamepad2.right_stick_y > 0.3)
        {
            setVelocity(wobbleArm, 0.25);
        }
        else if (gamepad2.left_stick_y < -0.3)
        {
            setVelocity(wobbleArm, -0.6);
        }
        else if (gamepad2.left_stick_y > 0.3)
        {
            setVelocity(wobbleArm, 0.6);
        }
        else
        {
            setVelocity(wobbleArm, 0);
        }
    }
    
    private void normalizeVelocities()
    {
        double maxSpeed = Math.abs(Arrays.stream(teleopVelocityArray).max().getAsDouble());
        
        for (int i = 0; i < 4; i++)
        {
            if (maxSpeed > 1)
            {
                teleopVelocityArray[i] /= maxSpeed;
            }
    
            teleopVelocityArray[i] /= Math.abs(teleopVelocityArray[i]);
            teleopVelocityArray[i] *= 0.9;
    
            if (slowMode)
            {
                teleopVelocityArray[i] /= Math.abs(teleopVelocityArray[i]);
                teleopVelocityArray[i] *= 0.3;
            }
        }
    }
}