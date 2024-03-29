package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class Constants {

    public static final class LinearActuator {
        public static final int linearActuatorMotor = 1;
        public static final double linearActuatorMotorSpeedUp = 0.5; //TBD
        public static final double linearActuatorMotorSpeedDown = -0.5; //TBD
        public static final double linearActuatorMotorMaxRot = 5.0;
        public static final double linearActuatorMotorMinRot = 0;
        public static final double linearActuatorMotorEaseFactor = 0.02; //Easing Increment added to speed //TBD
        public static final double linearActuatorMotorEaseOutMultiplier = 3; //Higher Value = More responsive; Less smooth
        public static final double linearActuatorMotorEaseOutThreshold = (linearActuatorMotorEaseOutMultiplier * linearActuatorMotorEaseFactor); //Minimum speed while easing before speed is set to 0
     
        public static final double linearActuatorMotorGearRat = 10; //TBD
    }

    public static class PWM {
        public static final int kStripLEDPort = 0;
    }

    public static final class PnuematicsConstants {
        public static final int kMinPressure = 20;
        public static final int kMaxPressure = 40;

        public static final int kAnalogChannel = 0;

        public static final int kCannonCANID = 14; //TBD
        public static final int kCannonSolenoid1 = 0;
    }

    public static final class ModuleConstants {
        public static final double kWheelDiameterMeters = Units.inchesToMeters(4); //TBD
        public static final double kDriveMotorGearRatio = 1 / 6.67; //TBD
        public static final double kTurningMotorGearRatio = 1 / (41.0 + (2.0/3.0)); //TBD
        public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters; //TBD
        public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI; //TBD
        public static final double kDriveEncoderRPM2MeterPerSec = kDriveEncoderRot2Meter / 60; //TBD
        public static final double kTurningEncoderRPM2RadPerSec = kTurningEncoderRot2Rad / 60; //TBD
        public static final double kPTurning = 0.5; //TBD
    }

    public static final class DriveConstants {

        public static final double kPhysicalMaxSpeedMetersPerSecond = 2;
        public static final double kPhysicalMaxAngularSpeedRadiansPerSecond = 2 * 2 * Math.PI;
        

        public static final int kFrontLeftDriveCanID = 3;
        public static final int kBackLeftDriveCanID = 5;
        public static final int kFrontRightDriveCanID = 8;
        public static final int kBackRightDriveCanID = 6;

        public static final int kFrontLeftTurningCanID = 4;
        public static final int kBackLeftTurningCanID = 2;
        public static final int kFrontRightTurningCanID = 9;
        public static final int kBackRightTurningCanID = 7;

        public static final boolean kFrontLeftTurningEncoderReversed = true;
        public static final boolean kBackLeftTurningEncoderReversed = true;
        public static final boolean kFrontRightTurningEncoderReversed = true;
        public static final boolean kBackRightTurningEncoderReversed = true;

        public static final boolean kFrontLeftDriveEncoderReversed = true;
        public static final boolean kBackLeftDriveEncoderReversed = true;
        public static final boolean kFrontRightDriveEncoderReversed = false;
        public static final boolean kBackRightDriveEncoderReversed = false;

        public static final double kTeleDriveMaxSpeedMetersPerSecond = kPhysicalMaxSpeedMetersPerSecond;
        public static final double kTeleDriveMinSpeedMetersPerSecond = 5/3;
        public static final double kTeleDriveMaxAngularSpeedRadiansPerSecond = kPhysicalMaxAngularSpeedRadiansPerSecond / 4;
        public static final double kTeleDriveMaxAccelerationUnitsPerSecond = 3;
        public static final double kTeleDriveMaxAngularAccelerationUnitsPerSecond = 3;
        
        // Distance between right and left wheels
        public static final double kTrackWidth = Units.inchesToMeters(17.5); //TBD
        // Distance between front and back wheels
        public static final double kWheelBase = Units.inchesToMeters(32); //TBD
       
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2));
    }

    public static final class OIConstants {
        public static final int kDriverJoystickPort = 0;

        public static final int kDriverYAxis = 1;
        public static final int kDriverXAxis = 0;
        public static final int kDriverRotAxis = 4;
        public static final int kDriverTurboButton = 9;

        public static final double kDeadband = 0.1;
    }

}

