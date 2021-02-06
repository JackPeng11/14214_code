package org.firstinspires.ftc.teamcode.NonRunnable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonRunnable.Logic.AngleCorrections;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.resetDriveEncoders;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.stopDrivingRobot;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.COUNTS_PER_INCH;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.FINAL_SLOW;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.FINAL_SLOW_VELOCITY;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.INITIAL_SLOW;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotConstants.INITIAL_SLOW_VELOCITY;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobotHardware.BL;

public class DrivePath
{
    private static double       currentVelocity;
    private final  ElapsedTime  runTime = new ElapsedTime();
    private final  double       velocity;
    private final  double       targetDistance;
    private final  DriveMode    driveMode;
    private final  LinearOpMode opMode;
    private        double       distanceTraveled;
    private        double       inchesError;
    private        double       cycles;
    
    public DrivePath(double velocity, double targetDistance, DriveMode driveMode, LinearOpMode opMode)
    {
        this.velocity = velocity;
        this.targetDistance = targetDistance;
        this.driveMode = driveMode;
        this.opMode = opMode;
        this.inchesError = targetDistance;
        this.distanceTraveled = 0;
        cycles = 0;
        
        setDriveDirection(this.driveMode);
    }
    
    public static double getCurrentVelocity()
    {
        return currentVelocity;
    }
    
    public void showCycleStats()
    {
        double finishTime = this.runTime.milliseconds();
        double cycleTime  = finishTime / this.cycles;
        
        this.opMode.telemetry.addData("Runtime", finishTime);
        this.opMode.telemetry.addData("Cycles", this.cycles);
        this.opMode.telemetry.addData("Latency(ms)", cycleTime);
        this.opMode.telemetry.update();
    }
    
    public void go()
    {
        prepareForStart();
        
        double[]         finalDriveVelocities;
        AngleCorrections correctionArray = new AngleCorrections(this.driveMode);
        
        while ((this.inchesError > 0.5) && this.opMode.opModeIsActive())
        {
            correctionArray.update(getAngle());
            finalDriveVelocities = getCorrectedVelocities(getInitialDriveVelocities(),
                                                          correctionArray.getCorrectionArray());
            this.opMode.telemetry.addData("distance traveled", this.distanceTraveled);
            this.opMode.telemetry.addData("OG", Arrays.toString(getInitialDriveVelocities()));
            this.opMode.telemetry.addData("Corrections", Arrays.toString(correctionArray.getCorrectionArray()));
            this.opMode.telemetry.update();
            
            setDriveMotorsVelocity(finalDriveVelocities);
            updateValues();
        }
        stopDrivingRobot(this.opMode);
    }
    
    private void prepareForStart()
    {
        resetAngle();
        resetDriveEncoders();
        runTime.reset();
    }
    
    private double[] getCorrectedVelocities(double[] velocityArray, double[] correctionArray)
    {
        for (int i = 0; i < 4; i++)
        {
            velocityArray[i] += correctionArray[i];
        }
        return velocityArray;
    }
    
    private double[] getInitialDriveVelocities()
    {
        double[] velocityArray = new double[4];
        
        if (this.distanceTraveled < INITIAL_SLOW)
        {
            Arrays.fill(velocityArray, INITIAL_SLOW_VELOCITY);
            currentVelocity = INITIAL_SLOW_VELOCITY;
        }
        else if (this.distanceTraveled > this.targetDistance - FINAL_SLOW)
        {
            Arrays.fill(velocityArray, FINAL_SLOW_VELOCITY);
            currentVelocity = FINAL_SLOW_VELOCITY;
        }
        else
        {
            Arrays.fill(velocityArray, this.velocity);
            currentVelocity = this.velocity;
        }
        return velocityArray;
    }
    
    private void updateValues()
    {
        cycles++;
        this.distanceTraveled = BL.getCurrentPosition() / COUNTS_PER_INCH;
        this.inchesError = Math.abs(this.distanceTraveled - this.targetDistance);
    }
}
