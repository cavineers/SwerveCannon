package frc.robot;

import frc.robot.Constants.OIConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.SolenoidToggle;
import frc.robot.commands.SwerveCommand;
import frc.robot.commands.SwitchFunction;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;


public class RobotContainer {

   
    private final SwerveDriveSubsystem swerveSubsystem = new SwerveDriveSubsystem();
    private final Cannon cannon;
    public Command m_balance;
    private final Joystick driverJoystick = new Joystick(OIConstants.kDriverJoystickPort);
    private JoystickButton buttonX = new JoystickButton(driverJoystick, 3);
    private JoystickButton buttonY = new JoystickButton(driverJoystick, 4);

    public RobotContainer() {

        cannon = new Cannon(0);

        swerveSubsystem.setDefaultCommand(new SwerveCommand(
            swerveSubsystem,
            () -> -driverJoystick.getRawAxis(OIConstants.kDriverYAxis),
            () -> driverJoystick.getRawAxis(OIConstants.kDriverXAxis),
            () -> driverJoystick.getRawAxis(OIConstants.kDriverRotAxis),
            () -> !driverJoystick.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));

        configureButtonBindings();

    };

    private void configureButtonBindings() {
        buttonX.onTrue(new SolenoidToggle(cannon));
    }
}
