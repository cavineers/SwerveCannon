package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.wpilibj.Compressor;

public class Cannon extends SubsystemBase{

    private Solenoid sol;
    private Solenoid sol2;

    private Compressor compressor;

    private Solenoid power1;
    private Solenoid power2;
    private Solenoid power3;

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.isEnabled());
    }

    public Cannon() {
        this.sol = new Solenoid(PneumaticsModuleType.REVPH, 4);
        this.sol2 = new Solenoid(PneumaticsModuleType.REVPH, 5);

        this.power1 = new Solenoid(PneumaticsModuleType.REVPH, 1);
        this.power2 = new Solenoid(PneumaticsModuleType.REVPH, 2);
        this.power3 = new Solenoid(PneumaticsModuleType.REVPH, 3);
        compressor = new Compressor(PneumaticsModuleType.REVPH);
    }

    public void startPnuematics(){
        compressor.enableHybrid(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
        this.power1.set(true);
        this.power2.set(true);
        this.power3.set(true);
    }

    public void barrel1() {
        this.sol.toggle(); // right barrel
    }
    
    public void barrel2() {
        this.sol2.set(true); // left barrel
    }

}