package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.wpilibj.Compressor;

public class Cannon extends SubsystemBase{

    private Solenoid sol;
    private Compressor compressor;

    public Cannon() {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, PnuematicsConstants.kCannonSolenoid1);
        compressor = new Compressor(PnuematicsConstants.kCannonCANID, PneumaticsModuleType.REVPH);
    }

    public void toggle() {
        this.sol.toggle();
    }

    public void startCompressor() {
        compressor.enableDigital();
    }

    

}