package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.MAX_COUNTS_PER_SECOND;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.PAUSE_BETWEEN_MOVEMENTS;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.BL;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.BR;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.FL;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.FR;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.driveMotorsArray;

public final class GeneralDriveMotorFunctions
{
    private GeneralDriveMotorFunctions()
    {
    }
    
    /*
    - When looking at the front of the goBILDA drive shaft, clockwise is
      FORWARD, counter-clockwise is REVERSE
    - These directions only work assuming a bevel gear configuration on the drivetrain
    */
    public static void setDriveDirection(NvyusRobotConstants.DriveMode driveMode)
    {
        if (driveMode == NvyusRobotConstants.DriveMode.STRAFE_LEFT)
        {
            setMotorDirections(REVERSE, REVERSE, FORWARD, FORWARD);
        }
        else if (driveMode == NvyusRobotConstants.DriveMode.STRAFE_RIGHT)
        {
            setMotorDirections(FORWARD, FORWARD, REVERSE, REVERSE);
        }
        else if (driveMode == NvyusRobotConstants.DriveMode.FORWARD)
        {
            setMotorDirections(FORWARD, REVERSE, FORWARD, REVERSE);
        }
        else if (driveMode == NvyusRobotConstants.DriveMode.BACKWARD)
        {
            setMotorDirections(REVERSE, FORWARD, REVERSE, FORWARD);
        }
        else if (driveMode == NvyusRobotConstants.DriveMode.ROTATE_CW)
        {
            setMotorDirections(FORWARD, FORWARD, FORWARD, FORWARD);
        }
        else if (driveMode == NvyusRobotConstants.DriveMode.ROTATE_CCW)
        {
            setMotorDirections(REVERSE, REVERSE, REVERSE, REVERSE);
        }
    }
    
    private static void setMotorDirections(DcMotorSimple.Direction FLDirection, DcMotorSimple.Direction FRDirection,
                                           DcMotorSimple.Direction BLDirection, DcMotorSimple.Direction BRDirection)
    {
        FL.setDirection(FLDirection);
        FR.setDirection(FRDirection);
        BL.setDirection(BLDirection);
        BR.setDirection(BRDirection);
    }
    
    public static void stopDrivingRobot(LinearOpMode opMode)
    {
        setDriveMotorsVelocity(0);
        opMode.sleep(PAUSE_BETWEEN_MOVEMENTS);
    }
    
    public static void setDriveMotorsVelocity(double velocity)
    {
        for (DcMotorEx dcMotorEx : driveMotorsArray)
        {
            setVelocity(dcMotorEx, velocity);
        }
    }
    
    public static void setVelocity(DcMotorEx motor, double velocity)
    {
        motor.setVelocity(velocity * MAX_COUNTS_PER_SECOND);
    }
    
    public static void setDriveMotorsVelocity(double[] velocityArray)
    {
        for (int i = 0; i < velocityArray.length; i++)
        {
            setVelocity(driveMotorsArray[i], velocityArray[i]);
        }
    }
    
    public static void resetDriveEncoders()
    {
        for (DcMotorEx dcMotorEx : driveMotorsArray)
        {
            dcMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}