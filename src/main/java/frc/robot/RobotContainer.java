package frc.robot;

import frc.robot.Constants.OIConstants;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.LowerCannon;
import frc.robot.commands.RaiseCannon;
import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.LinearActuator;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Strip;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.PnuematicsConstants;


public class RobotContainer {

   
    private final SwerveDriveSubsystem swerveSubsystem = new SwerveDriveSubsystem();
    private final LinearActuator linearActuator;
    private final Cannon cannon;
    private final Strip strip;

    public Command raiseCannon;
    public Command lowerCannon;
    public Command HomeCannon;
    public Command ShootAngle;

    public final XboxController xbox = new XboxController(0);
    private JoystickButton buttonA = new JoystickButton(xbox, 1);
    private JoystickButton buttonB = new JoystickButton(xbox, 2);
    private JoystickButton buttonX = new JoystickButton(xbox, 3);
    private JoystickButton buttonY = new JoystickButton(xbox, 4);
    private JoystickButton leftBump = new JoystickButton(xbox, 5);
    private JoystickButton rightBump = new JoystickButton(xbox, 6);

    private SequentialCommandGroup fireCannon;
    private SequentialCommandGroup fireCannon2;

    public RobotContainer(Cannon cannon) {

        this.cannon = cannon;
        linearActuator = new LinearActuator();
        strip = new Strip();

        raiseCannon = new RaiseCannon(linearActuator);
        lowerCannon = new LowerCannon(linearActuator);

        this.fireCannon = new SequentialCommandGroup();
        this.fireCannon2 = new SequentialCommandGroup();

        this.fireCannon.addCommands(
            new InstantCommand(){
                @Override
                public void initialize() {
                    cannon.right();
                }
            },
            new WaitCommand(PnuematicsConstants.kOpenTime),
            new InstantCommand(){
                @Override
                public void initialize() {
                   cannon.right(); 
                   cannon.rightCycle();
                }
            }
        );

        this.fireCannon2.addCommands(
            new InstantCommand(){
                @Override
                public void initialize() {
                    cannon.left();
                }
            },
            new WaitCommand(PnuematicsConstants.kOpenTime),
            new InstantCommand(){
                @Override
                public void initialize() {
                    cannon.left(); 
                    cannon.leftCycle();
                }
            }

        ); 

        swerveSubsystem.setDefaultCommand(new SwerveCommand(
            swerveSubsystem,
            () -> -xbox.getRawAxis(OIConstants.kDriverYAxis),
            () -> xbox.getRawAxis(OIConstants.kDriverXAxis),
            () -> xbox.getRawAxis(OIConstants.kDriverRotAxis),
            () -> !xbox.getRawButton(OIConstants.kDriverTurboButton)));

        configureButtonBindings();
       
    };

    private void configureButtonBindings() {
        
        buttonX.onTrue(new InstantCommand(){
            @Override
            public void initialize() {
                if(leftBump.getAsBoolean()&&rightBump.getAsBoolean()&&!fireCannon2.isScheduled()){
                    fireCannon2.schedule();
                }
            }
        });

        buttonB.onTrue(new InstantCommand(){
            @Override
            public void initialize() {
                if(leftBump.getAsBoolean()&&rightBump.getAsBoolean()&&!fireCannon.isScheduled()){
                    fireCannon.schedule();
                }
            }
        });

        buttonY.onTrue(raiseCannon);
        buttonY.onFalse(new InstantCommand() {
            @Override
            public void initialize() {
                raiseCannon.cancel();
          }
        });

        buttonA.onTrue(lowerCannon);
        buttonA.onFalse(new InstantCommand() {
            @Override
            public void initialize() {
                lowerCannon.cancel();
          }
        });
    }
}
