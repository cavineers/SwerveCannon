package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Cannon extends SubsystemBase{

    private Solenoid sol;

    public Cannon(int channel) {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, channel);
    }

    public void open() {
        this.sol.set(true);
    }

    public void close() {
        this.sol.set(false);
    }

    public void toggle() {
        this.sol.toggle();
    }

    public boolean get() {
        return this.sol.get();
    }

}