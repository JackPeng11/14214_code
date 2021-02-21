package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_ARM_COUNTS_PER_DEG;
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
}
