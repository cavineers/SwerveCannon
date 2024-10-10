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
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;

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

    // Shuffle Board Settings
    ShuffleboardTab pressureTab;
    GenericEntry maxPressureEntry;
    double maxPressureDouble;
    private int tick;

    // Compressor state
    public enum compressorStateEnum {
        CHARGING,
        STANDBY,
        OFF
    }

    private boolean automaticCompressorControl;

    private compressorStateEnum compressorState;

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.isEnabled());
        SmartDashboard.putBoolean("Compressor State", (compressorState == compressorStateEnum.CHARGING));
        SmartDashboard.putNumber("Tank PSI", compressor.getPressure());

        SmartDashboard.putBoolean("Left Barrel Software Command", leftSolenoidStatus);
        SmartDashboard.putBoolean("Right Barrel Software Command", rightSolenoidStatus);

        SmartDashboard.putBoolean("Left Barrel Solenoid Status", this.sol4.get()); 
        SmartDashboard.putBoolean("Right Barrel Solenoid Status", this.sol3.get());

        SmartDashboard.putNumber("Max Pressure Setpoint", maxPressureDouble);
        updateMaxPressure();

        // Actually open the solenoids
        this.sol4.set(leftSolenoidStatus); // barrel 1
        this.sol3.set(rightSolenoidStatus);

        determineCompressorState();

        switch (this.compressorState) {
            case CHARGING:
                compressor.enableAnalog(maxPressureDouble - 1, maxPressureDouble);
                SmartDashboard.putString("Compressor State Label", "CHARGING");
                compressorLog.append(true);
                break;
            case OFF:
                SmartDashboard.putString("Compressor State Label", "OFF");
                compressor.disable();
                compressorLog.append(false);
                break;
            case STANDBY:
            default:
                SmartDashboard.putString("Compressor State Label", "STANDBY");
                compressor.disable();
                compressorLog.append(false);
                break;
        }

        // Don't constantly log PSI (every 100 cycles)
        if (tick % 100 == 0){
            this.psiLog.append(this.compressor.getPressure());
        }
        this.tick++;
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


        // Automatic compressor charging on by default
        this.compressorState = compressorStateEnum.CHARGING;
        this.automaticCompressorControl = true;

        // Barrel Status
        this.leftSolenoidStatus = false;
        this.rightSolenoidStatus = false;

        this.pressureTab = Shuffleboard.getTab("Pressure Settings");
        this.maxPressureEntry = pressureTab.add("Max Pressure", 50).getEntry();
    }

    private void determineCompressorState(){
        if (compressor.getPressure() > this.maxPressureDouble){
            this.compressorState = (automaticCompressorControl) ? compressorStateEnum.STANDBY : compressorStateEnum.OFF;        
        }
        else{
            if (compressor.getPressure() < maxPressureDouble - 3){ // PSI Tolerance
                this.compressorState = compressorStateEnum.CHARGING;
            }
        }
    }

    private void updateMaxPressure(){
        if (this.maxPressureEntry.getDouble(0) > 120) return; // guard clause

        if (this.maxPressureEntry.getDouble(0) != this.maxPressureDouble){
            System.out.println("UPDATING MAX PRESSURE: ");
            this.maxPressureDouble = this.maxPressureEntry.getDouble(0);
            System.out.println("UPDATED: " + this.maxPressureDouble);
        }
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
        compressor.enableAnalog(PnuematicsConstants.kMinPressure, PnuematicsConstants.kMaxPressure);
        this.power0.set(true);
        this.power1.set(true);
        this.power2.set(true);
        barrelLog.append("Pneumatic System Panel: Enabled");
        barrelLog.append("Pneumatic System Armed: All Barrels Primers Armed");
    }

    public void left() {
        this.leftSolenoidStatus = !this.leftSolenoidStatus;
    }
    
    public void right() {
        this.rightSolenoidStatus = !this.rightSolenoidStatus;
    }
}