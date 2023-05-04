package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Cannon extends SubsystemBase{

    private Solenoid sol;
    private Compressor compressor;

    public Cannon() {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, PnuematicsConstants.kCannonSolenoid1);
        compressor = new Compressor(PneumaticsModuleType.REVPH);

        compressor.enableDigital();
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