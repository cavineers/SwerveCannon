package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class SolenoidToggle extends CommandBase{

    private Cannon cannon;
    private boolean isFinished;
    
    public SolenoidToggle(Cannon Cannon1) {

        this.cannon = Cannon1;
        this.isFinished = false;

        addRequirements(Cannon1);
    }

    @Override
    public void initialize() {
        cannon.toggle();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
