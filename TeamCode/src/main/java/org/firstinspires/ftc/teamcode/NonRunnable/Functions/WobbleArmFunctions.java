package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_ARM_COUNTS_PER_DEG;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;

public final class WobbleArmFunctions
{
    private WobbleArmFunctions()
    {
    }
    
    public static final boolean SECTOR_0_DOWN = wobblePositionLesserOrEqual(45);
    public static final boolean SECTOR_1_DOWN = wobblePositionLesserOrEqual(90);
    public static final boolean SECTOR_2_DOWN = wobblePositionLesserOrEqual(180);
    
    public static final boolean SECTOR_0_UP = wobblePositionGreaterOrEqual(180);
    public static final boolean SECTOR_1_UP = wobblePositionGreaterOrEqual(180);
    public static final boolean SECTOR_2_UP = wobblePositionGreaterOrEqual(180);
    
    
    public static boolean wobbleArmReachedTarget(double wobbleArmPosition)
    {
        return Math.abs(wobbleArm.getCurrentPosition() - wobbleArmDegreesToCounts(wobbleArmPosition)) <= wobbleArmDegreesToCounts(
                3);
    }
    
    public static double wobbleArmDegreesToCounts(double degrees)
    {
        return degrees * WOBBLE_ARM_COUNTS_PER_DEG;
    }
    
    public static boolean wobblePositionGreaterOrEqual(double wobbleArmPosition)
    {
        return wobbleArm.getCurrentPosition() >= wobbleArmDegreesToCounts(wobbleArmPosition - 4);
    }
    
    public static boolean wobblePositionLesserOrEqual(double wobbleArmPosition)
    {
        return wobbleArm.getCurrentPosition() <= wobbleArmDegreesToCounts(wobbleArmPosition + 4);
    }
    
    public static void moveWobbleArmDown(LinearOpMode opMode)
    {
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        while ((wobbleArm.getCurrentPosition() <= wobbleArmDegreesToCounts(183)) && opMode.opModeIsActive())
        {
            setVelocity(wobbleArm, 0.4);
        }
        setVelocity(wobbleArm, 0);
    }
    
    public static void moveWobbleArmUp(LinearOpMode opMode)
    {
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    
        while ((wobbleArm.getCurrentPosition() >= wobbleArmDegreesToCounts(-183)) && opMode.opModeIsActive())
        {
            setVelocity(wobbleArm, -0.4);
        }
        setVelocity(wobbleArm, 0);
    }
    
    public static void releaseWobbleGoal(LinearOpMode opMode)
    {
        wobble.setPosition(WOBBLE_OPEN_POSITION);
        opMode.sleep(400);
        opMode.idle();
    }
    
    public static void gripWobbleGoal(LinearOpMode opMode)
    {
        wobble.setPosition(WOBBLE_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
}
