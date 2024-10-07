package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PnuematicsConstants;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.Compressor;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;



public class Cannon extends SubsystemBase{

    private Solenoid sol3;
    private Solenoid sol4;

    private Compressor compressor;

    private Solenoid power0;
    private Solenoid power1;
    private Solenoid power2;

    // Logging
    private DataLog log;
    private BooleanLogEntry compressorLog;
    private DoubleLogEntry psiLog;
    private StringLogEntry barrelLog;

    // Barrel Status
    private boolean leftSolenoidStatus;
    private boolean rightSolenoidStatus;

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.isEnabled());
        SmartDashboard.putBoolean("Software Compressor Order", compressor.getPressureSwitchValue());
        SmartDashboard.putNumber("Tank PSI", compressor.getPressure());

        SmartDashboard.putBoolean("Left Barrel Software Command", leftSolenoidStatus);
        SmartDashboard.putBoolean("Right Barrel Software Command", rightSolenoidStatus);

        SmartDashboard.putBoolean("Left Barrel Solenoid Status", this.sol4.get()); 
        SmartDashboard.putBoolean("Right Barrel Solenoid Status", this.sol3.get());
    }

    public Cannon() {
        this.sol4 = new Solenoid(PneumaticsModuleType.REVPH, 4); // reset on channel 1
        this.sol3 = new Solenoid(PneumaticsModuleType.REVPH, 3); // reset on channel 0 after

        this.power0 = new Solenoid(PneumaticsModuleType.REVPH, 0); // enables relay 
        this.power1 = new Solenoid(PneumaticsModuleType.REVPH, 1); // enables relay
        this.power2 = new Solenoid(PneumaticsModuleType.REVPH, 2); // enables board
        compressor = new Compressor(PneumaticsModuleType.REVPH);

        // Data Logging
        DataLogManager.start();

        this.log = DataLogManager.getLog();
        compressorLog = new BooleanLogEntry(log, "/cannonLogs/compressorLog");
        psiLog = new DoubleLogEntry(log, "/cannonLogs/psiLog");
        barrelLog = new StringLogEntry(log, "/cannonLogs/barrelLog");

        // Barrel Status
        this.leftSolenoidStatus = false;
        this.rightSolenoidStatus = false;
    }

    public void rightPrimerOff(){ // Resets barrel primer after a solenoid is fired
        this.power0.set(false);
        barrelLog.append("Right Barrel Primer: UNARMED");
    }

    public void rightPrimerOn(){ // Resets barrel primer after a solenoid is fired
        this.power0.set(true);
        barrelLog.append("Right Barrel Primer: ARMED");
    }

    public void leftPrimerOff(){
        this.power1.set(false);
        barrelLog.append("Left Barrel Primer: UNARMED");
    }

    public void leftPrimerOn(){
        this.power1.set(true);
        barrelLog.append("Left Barrel Primer: ARMED");
    }

    public void startPnuematics(){
        compressor.enableHybrid(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
        this.power0.set(true);
        this.power1.set(true);
        this.power2.set(true);
        barrelLog.append("Pneumatic System Panel: Enabled");
        barrelLog.append("Pneumatic System Armed: All Barrels Primers Armed");
    }

    public void left() {
        this.sol4.toggle(); // barrel 1
        this.leftSolenoidStatus = !this.leftSolenoidStatus;
    }
    
    public void right() {
        this.sol3.toggle(); // barrel 2
        this.rightSolenoidStatus = !this.rightSolenoidStatus;
    }

    public void insanityCheck()
    {
        
    }
}