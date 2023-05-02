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
import edu.wpi.first.wpilibj.XboxController;


public class RobotContainer {

   
    private final SwerveDriveSubsystem swerveSubsystem = new SwerveDriveSubsystem();
    private final Cannon cannon;
    public Command m_balance;

    public final XboxController xbox = new XboxController(0);
    public JoystickButton buttonX = new JoystickButton(xbox, 3);
    public JoystickButton buttonY = new JoystickButton(xbox, 4);
    public JoystickButton l_bump = new JoystickButton(xbox, 5);
    public JoystickButton r_bump = new JoystickButton(xbox, 6);

    public RobotContainer() {

        cannon = new Cannon(0);

        swerveSubsystem.setDefaultCommand(new SwerveCommand(
            swerveSubsystem,
            () -> -xbox.getRawAxis(OIConstants.kDriverYAxis),
            () -> xbox.getRawAxis(OIConstants.kDriverXAxis),
            () -> xbox.getRawAxis(OIConstants.kDriverRotAxis),
            () -> !xbox.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));

        configureButtonBindings();
       

    };

    private void configureButtonBindings() {
        
        buttonX.onTrue(new InstantCommand(){
            @Override
            public void initialize() {
                if(l_bump.getAsBoolean()&&r_bump.getAsBoolean()){
                    cannon.toggle();
                }
            }
        });
        
    }
}
