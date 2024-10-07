package frc.robot;

import frc.robot.Constants.OIConstants;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.HomeCannon;
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
    public Command homeCannon;
    public Command ShootAngle;

    public final XboxController xbox = new XboxController(0);
    private JoystickButton buttonA = new JoystickButton(xbox, 1);
    private JoystickButton buttonB = new JoystickButton(xbox, 2);
    private JoystickButton buttonX = new JoystickButton(xbox, 3);
    private JoystickButton buttonY = new JoystickButton(xbox, 4);
    private JoystickButton leftBump = new JoystickButton(xbox, 5);
    private JoystickButton rightBump = new JoystickButton(xbox, 6);
    private JoystickButton left_menu = new JoystickButton(xbox, 7);

    private SequentialCommandGroup fireCannon;
    private SequentialCommandGroup fireCannon2;

    private SequentialCommandGroup resetLeftBarrel;
    private SequentialCommandGroup resetRightBarrel;

    private boolean shootDebounce;

    public RobotContainer(Cannon cannon) {

        this.cannon = cannon;
        linearActuator = new LinearActuator();
        strip = new Strip();

        raiseCannon = new RaiseCannon(linearActuator);
        lowerCannon = new LowerCannon(linearActuator);
        homeCannon = new HomeCannon(linearActuator);

        this.fireCannon = new SequentialCommandGroup();
        this.fireCannon2 = new SequentialCommandGroup();

        this.resetLeftBarrel = new SequentialCommandGroup();
        this.resetRightBarrel = new SequentialCommandGroup();
        
        this.shootDebounce = false;

        this.resetRightBarrel.addCommands(
            new InstantCommand(){
                public void initialize(){
                    cannon.rightPrimerOff();
                }
            },
            new WaitCommand(0.1),
            new InstantCommand(){
                public void initialize(){
                    cannon.rightPrimerOn();
                }
            }
        );

        this.resetLeftBarrel.addCommands(
            new InstantCommand(){
                public void initialize(){
                    cannon.leftPrimerOff();
                }
            },
            new WaitCommand(0.1),
            new InstantCommand(){
                public void initialize(){
                    cannon.leftPrimerOn();
                }
            }
        );

        this.fireCannon.addCommands(
            new InstantCommand(){
                @Override
                public void initialize() {
                    cannon.right();
                }
            },
            new WaitCommand(0.075),
            new InstantCommand(){
                public void initialize() { cannon.right(); }
            },
            this.resetRightBarrel
        );

        this.fireCannon2.addCommands(
            new InstantCommand(){
                @Override
                public void initialize() {
                    cannon.left();
                }
            },
            new WaitCommand(0.075),
            new InstantCommand(){
                public void initialize() { cannon.left(); }
            },
            this.resetLeftBarrel
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
                if(leftBump.getAsBoolean() && rightBump.getAsBoolean())
                {
                    fireCannon2.schedule();
                }
            }
        }).debounce(0.5);

        buttonB.onTrue(new InstantCommand(){
            @Override
            public void initialize() {
                if(leftBump.getAsBoolean()&&rightBump.getAsBoolean()){
                    fireCannon.schedule();
                }
            }
        }).debounce(0.5);;

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

        left_menu.onTrue(homeCannon); // Home the cannon moving downward


    }
}
