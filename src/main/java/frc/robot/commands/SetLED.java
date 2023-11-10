package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Strip;

public class SetLED extends CommandBase {

    private final Strip strip;

    public SetLED(Strip strip) {
        this.strip = strip;
        this.addRequirements(strip);
    }

    @Override
    public void initialize() {
        strip.setStripState(Strip.stripLEDState.OCEANCOLOREDRAINBOW);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}