package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.wpilibj.Compressor;

public class Cannon extends SubsystemBase{

    private Solenoid sol;
    private Compressor compressor;

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.isEnabled());
    }

    public Cannon() {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, PnuematicsConstants.kCannonSolenoid1);

        compressor = new Compressor(PneumaticsModuleType.REVPH);

    }

    public void startHybrid(){
        compressor.enableAnalog(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
    }

    public void toggle() {
        this.sol.toggle();
    }

}