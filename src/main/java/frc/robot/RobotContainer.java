package frc.robot;

import frc.robot.Constants.OIConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.LowerCannon;
import frc.robot.commands.RaiseCannon;
import frc.robot.commands.HomeCannon;
import frc.robot.commands.ShootAngle;
import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.LinearActuator;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Strip;


public class RobotContainer {

   
    private final SwerveDriveSubsystem swerveSubsystem = new SwerveDriveSubsystem();
    // private final LinearActuator linearActuator;
    private final Cannon cannon;
    private final Strip strip;

    public Command m_balance;
    public Command raiseCannon;
    public Command lowerCannon;
    public Command HomeCannon;
    public Command ShootAngle;

    public final XboxController xbox = new XboxController(0);
    public JoystickButton buttonA = new JoystickButton(xbox, 1);
    public JoystickButton buttonB = new JoystickButton(xbox, 2);
    public JoystickButton buttonX = new JoystickButton(xbox, 3);
    public JoystickButton buttonY = new JoystickButton(xbox, 4);
    public JoystickButton l_bump = new JoystickButton(xbox, 5);
    public JoystickButton r_bump = new JoystickButton(xbox, 6);

    public RobotContainer() {

        cannon = new Cannon();
        // linearActuator = new LinearActuator();
        strip = new Strip();

        // raiseCannon = new RaiseCannon(linearActuator);
        // lowerCannon = new LowerCannon(linearActuator);

        swerveSubsystem.setDefaultCommand(new SwerveCommand(
            swerveSubsystem,
            () -> -xbox.getRawAxis(OIConstants.kDriverYAxis),
            () -> xbox.getRawAxis(OIConstants.kDriverXAxis),
            () -> xbox.getRawAxis(OIConstants.kDriverRotAxis),
            () -> !xbox.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));

        configureButtonBindings();
       
    };

    private void configureButtonBindings() {
        
        buttonB.onTrue(new InstantCommand(){
            @Override
            public void initialize() {
                if(l_bump.getAsBoolean()&&r_bump.getAsBoolean()){
                    cannon.toggle();
                }
            }
        });

        buttonA.onTrue(new InstantCommand(){
            @Override
            public void initialize() {
                strip.setStripState(Strip.stripLEDState.OCEANCOLOREDRAINBOW);
            }
        });

        buttonY.onTrue(raiseCannon);
        buttonY.onFalse(new InstantCommand() {
            @Override
            public void initialize() {
                raiseCannon.cancel();
          }
        });

        buttonX.onTrue(lowerCannon);
        buttonX.onFalse(new InstantCommand() {
            @Override
            public void initialize() {
                lowerCannon.cancel();
          }
        });
    }
}
