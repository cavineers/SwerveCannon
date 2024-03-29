package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;
import java.lang.Math;
import java.util.function.Supplier;
import edu.wpi.first.math.filter.SlewRateLimiter;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveCommand extends CommandBase {

    private final SwerveDriveSubsystem swerveSubsystem;
    private final Supplier<Double> xSpdFunction, ySpdFunction, turningSpdFunction;
    private final Supplier<Boolean> turboFunction;
    private final SlewRateLimiter xLimiter, yLimiter, turningLimiter;

    public SwerveCommand(SwerveDriveSubsystem swerveSubsystem,
            Supplier<Double> xSpdFunction, Supplier<Double> ySpdFunction, Supplier<Double> turningSpdFunction,
            Supplier<Boolean> turboFunction) {
        this.swerveSubsystem = swerveSubsystem;
        this.xSpdFunction = xSpdFunction;
        this.ySpdFunction = ySpdFunction;
        this.turningSpdFunction = turningSpdFunction;
        this.turboFunction = turboFunction;
        this.xLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
        this.yLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
        this.turningLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAngularAccelerationUnitsPerSecond);
        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Get real-time joystick inputs
        double xSpeed = xSpdFunction.get();
        double ySpeed = ySpdFunction.get();
        double turningSpeed = turningSpdFunction.get();

        // Apply deadband -- compensated for when the joystick value does not return to exactly zero
        xSpeed = Math.abs(xSpeed) > OIConstants.kDeadband ? xSpeed : 0.0;
        ySpeed = Math.abs(ySpeed) > OIConstants.kDeadband ? ySpeed : 0.0;
        turningSpeed = Math.abs(turningSpeed) > OIConstants.kDeadband ? turningSpeed : 0.0;

        // Smooths driving for jerky joystick movement & eases acceleration
        xSpeed = xLimiter.calculate(xSpeed) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        ySpeed = yLimiter.calculate(ySpeed) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        turningSpeed = turningLimiter.calculate(turningSpeed)
            * DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;

        // Construct desired chassis speeds
        ChassisSpeeds chassisSpeeds;
        if (turboFunction.get()){
            xSpeed = xLimiter.calculate(xSpeed) * DriveConstants.kTeleDriveMinSpeedMetersPerSecond;
            ySpeed = yLimiter.calculate(ySpeed) * DriveConstants.kTeleDriveMinSpeedMetersPerSecond;
        } else {
            xSpeed = xLimiter.calculate(xSpeed) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
            ySpeed = yLimiter.calculate(ySpeed) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        }

        // //Relative to field
        // chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
        //     xSpeed, ySpeed, turningSpeed, swerveSubsystem.getRotation2d());

        // // Relative to robot
        chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, turningSpeed);
        
        // Convert chassis speeds to individual module states
        SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);
        
        // Output each module states to wheels
        swerveSubsystem.setModuleStates(moduleStates);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSubsystem.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
