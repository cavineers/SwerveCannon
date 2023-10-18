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

    private Solenoid power0;
    private Solenoid power1;
    private Solenoid power2;
    private Solenoid power3;
    private Solenoid power4;

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.isEnabled());
    }

    public Cannon() {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, PnuematicsConstants.kCannonSolenoid1);
        this.power0 = new Solenoid(PneumaticsModuleType.REVPH, 0);
        this.power1 = new Solenoid(PneumaticsModuleType.REVPH, 1);
        this.power2 = new Solenoid(PneumaticsModuleType.REVPH, 2);
        this.power3 = new Solenoid(PneumaticsModuleType.REVPH, 3);
        this.power4 = new Solenoid(PneumaticsModuleType.REVPH, 4);
        compressor = new Compressor(PneumaticsModuleType.REVPH);
    }

    public void startPnuematics(){
        compressor.enableHybrid(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
        this.power0.set(true);
        this.power1.set(true);
        this.power2.set(true);
        this.power3.set(true);
        this.power4.set(true);
    }

    public void toggle() {
        this.sol.toggle();
    }

}