package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.wpilibj.Compressor;

public class Cannon extends SubsystemBase{

    private Solenoid sol3;
    private Solenoid sol4;

    private Compressor compressor;

    private Solenoid power0;
    private Solenoid power1;
    private Solenoid power2;

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.isEnabled());
        SmartDashboard.putNumber("PSI", compressor.getPressure());
    }

    public Cannon() {
        this.sol4 = new Solenoid(PneumaticsModuleType.REVPH, 4); // reset on channel 1
        this.sol3 = new Solenoid(PneumaticsModuleType.REVPH, 3); // reset on channel 0 after

        this.power0 = new Solenoid(PneumaticsModuleType.REVPH, 0); // enables relay 
        this.power1 = new Solenoid(PneumaticsModuleType.REVPH, 1); // enables relay
        this.power2 = new Solenoid(PneumaticsModuleType.REVPH, 2); // enables board
        compressor = new Compressor(PneumaticsModuleType.REVPH);
    }

    public void rightPrimerOff(){ // Resets barrel primer after a solenoid is fired
        this.power0.set(false);
    }

    public void rightPrimerOn(){ // Resets barrel primer after a solenoid is fired
        this.power0.set(true);
    }

    public void leftPrimerOff(){
        this.power1.set(false);
    }

    public void leftPrimerOn(){
        this.power1.set(true);
    }

    public void startPnuematics(){
        compressor.enableHybrid(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
        this.power0.set(true);
        this.power1.set(true);
        this.power2.set(true);
    }

    public void left() {
        this.sol4.toggle(); // barrel 1
    }
    
    public void right() {
        this.sol3.toggle(); // barrel 2
    }

}