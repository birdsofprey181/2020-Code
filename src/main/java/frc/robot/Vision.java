package frc.robot;

import edu.wpi.first.networktables.*;

public class Vision{
    private NetworkTable table;

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double getAngleX() {
        return table.getEntry("tx").getDouble(0);
    }

    public double getAngleY() {
        return table.getEntry("ty").getDouble(0);
    }

    public double getArea() {
        return table.getEntry("ta").getDouble(0);
    }

    public double getSkew() {
        return table.getEntry("ts").getDouble(0);
    }
}