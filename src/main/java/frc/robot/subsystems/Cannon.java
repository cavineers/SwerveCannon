package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;

public class Cannon extends SubsystemBase{

    private Solenoid sol;
    private Compressor compressor;
    private PneumaticsControlModule pcm;

    @Override
    public void periodic() {
        SmartDashboard.putNumber("PSI", getPressure());
        SmartDashboard.putBoolean("Compressor State", isEnabled());
    }

    public Cannon() {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, PnuematicsConstants.kCannonSolenoid1);
        pcm = new PneumaticsControlModule(PnuematicsConstants.kCannonCANID);
        pcm.enableCompressorHybrid(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
    }

    public boolean isEnabled(){
        return pcm.getCompressor();
    }

    public double getPressure(){
        return pcm.getPressure(PnuematicsConstants.kCannonCANID);
    }

    public void toggle() {
        this.sol.toggle();
    }

}