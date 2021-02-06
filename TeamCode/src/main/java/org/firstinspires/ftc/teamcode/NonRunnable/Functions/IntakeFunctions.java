package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.FLAP_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.FLAP_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.GUIDE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.GUIDE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.flap;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.guide;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.spinner;

public final class IntakeFunctions
{
    private IntakeFunctions()
    {
    }
    
    public static void preventRingsFromPassing(LinearOpMode opMode)
    {
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
    
    public static void shoot(LinearOpMode opMode, long duration)
    {
        letRingsPass(opMode);
        setVelocity(spinner, 0.6);
        opMode.sleep(duration);
    }
    
    public static void letRingsPass(LinearOpMode opMode)
    {
        flap.setPosition(FLAP_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
        opMode.sleep(400);
        
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
        opMode.sleep(400);
        
        guide.setPosition(GUIDE_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
    }
}
