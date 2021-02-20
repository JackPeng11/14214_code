package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonRunnable.Button;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.FLAP_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.FLAP_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.GUIDE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.GUIDE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.WOBBLE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.WOBBLE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.flap;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.guide;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.tubeIntake;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.wobbleArm;

@TeleOp
public final class FinalTeleop extends LinearOpMode
{
    double forwardComponent;
    double strafeComponent;
    double rotationComponent;
    
    double currentTime;
    
    double[] teleopVelocityArray = new double[4];
    
    Button toggleSlowMode     = new Button();
    Button toggleRingFlow     = new Button();
    Button toggleWobbleServo  = new Button();
    Button zeroWobblePosition = new Button();
    
    boolean wobbleArmCalibrated = false;
    boolean slowMode            = false;
    
    boolean firstPartDone;
    boolean secondPartDone;
    boolean thirdPartDone;
    
    int toggleRingFlowCount = 0;
    
    @Override
    public void runOpMode()
    {
        initHardware(this);
        showReady(this);
        
        waitForStart();
        
        while (opModeIsActive())
        {
            drive();
            controlRingFlow();
            controlIntake();
            controlShooter();
            controlWobbleArm();
            
            telemetry.addData("Wobble Arm Calibrated:", wobbleArmCalibrated);
            telemetry.update();
        }
    }
    
    private void drive()
    {
        forwardComponent = -gamepad1.left_stick_y;
        strafeComponent = gamepad1.left_stick_x;
        rotationComponent = gamepad1.right_stick_x;
        
        teleopVelocityArray[0] = forwardComponent + strafeComponent + rotationComponent;
        teleopVelocityArray[1] = forwardComponent - strafeComponent - rotationComponent;
        teleopVelocityArray[2] = forwardComponent - strafeComponent + rotationComponent;
        teleopVelocityArray[3] = forwardComponent + strafeComponent - rotationComponent;
        
        normalizeVelocities();
        
        if (toggleSlowMode.isPressed(gamepad1.y))
        {
            slowMode = !slowMode;
        }
        setDriveMotorsVelocity(teleopVelocityArray);
    }
    
    private void controlRingFlow()
    {
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
    
    private void controlShooter()
    {
        if (gamepad2.right_trigger > 0.5)
        {
            if (gamepad2.left_trigger > 0.5)
            {
               setVelocity(flyWheel, 0.1469);
            }
            else
            {
                setVelocity(flyWheel, 0.4);
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
                setVelocity(spinner, 0.64);
            }
        }
        else
        {
            setVelocity(flyWheel, 0.05);
            guide.setPosition(GUIDE_OPEN_POSITION);
            sleep(0);
            idle();
            toggleRingFlow.isFinished();
            toggleRingFlowCount = 0;
        }
        
    }
    
    public void controlWobbleArm()
    {
        if (toggleWobbleServo.isPressed(gamepad2.left_stick_button))
        {
            if (wobble.getPosition() == WOBBLE_CLOSED_POSITION)
            {
                wobble.setPosition(WOBBLE_OPEN_POSITION);
            }
            else
            {
                wobble.setPosition(WOBBLE_CLOSED_POSITION);
            }
            sleep(0);
            idle();
        }
        
        if (zeroWobblePosition.isPressed(gamepad2.right_stick_button))
        {
            if (!wobbleArmCalibrated)
            {
                wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                zeroWobblePosition.isFinished();
                wobbleArmCalibrated = true;
            }
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
            
            if (slowMode)
            {
                teleopVelocityArray[i] /= Math.abs(teleopVelocityArray[i]);
                teleopVelocityArray[i] *= 0.3;
            }
        }
    }
}