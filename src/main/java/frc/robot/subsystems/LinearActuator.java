package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.subsystems.LinearActuator.LinearActuatorMotorState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LinearActuator extends SubsystemBase {

    
    //Initalize Motor
    public CANSparkMax linearActuatorMotor;
    public DigitalInput limitSwitch;

    //List States
    public enum LinearActuatorMotorState {
        ON,
        OFF,
        REVERSED
    }

    //Set Initial Motor State
    public LinearActuatorMotorState linearActuatorMotorState;

    //Motor Settings
    public LinearActuator() {
        // Motor Setup
        this.linearActuatorMotor = new CANSparkMax(Constants.LinearActuator.linearActuatorMotor, MotorType.kBrushless);
        this.linearActuatorMotor.setIdleMode(IdleMode.kBrake);
        this.linearActuatorMotor.setInverted(false);
        this.linearActuatorMotor.setSmartCurrentLimit(51);

        // Set state
        this.linearActuatorMotorState = LinearActuatorMotorState.OFF;

        // Limit
        this.limitSwitch = new DigitalInput(9);
    }

    //Changing Motor States
    public void setLinearActuatorMotorState(LinearActuatorMotorState state) {

        this.linearActuatorMotorState = state;

    }

    //Gets Motor Position
    public double getLinearActuatorMotorPosition() {
        return this.linearActuatorMotor.getEncoder().getPosition();
    }
    
    //Gets Motor Speed
    public double getLinearActuatorMotorSpeed() {
        return this.linearActuatorMotor.get();
    }

    //Sets Encoder Value 
    public void setLinearActuatorMotorPosition(double position) {
        this.linearActuatorMotor.getEncoder().setPosition(position);
    }

    public LinearActuatorMotorState getAngleControlMotorState() {
        return this.linearActuatorMotorState;
    }

    public boolean getLimitSwitch() {
        return this.limitSwitch.get();
    }

    public void periodic(){
        
        //ON State
        if (this.linearActuatorMotorState == LinearActuatorMotorState.ON) {
            this.linearActuatorMotor.set(Constants.LinearActuator.linearActuatorMotorSpeedUp);
            SmartDashboard.putString("State", "ON");

        }

        //OFF State
        if (this.linearActuatorMotorState == LinearActuatorMotorState.OFF){

            this.linearActuatorMotor.set(0);
            SmartDashboard.putString("State", "OFF");
            
        }

        //REVERSED State
        if (this.linearActuatorMotorState == LinearActuatorMotorState.REVERSED){
            this.linearActuatorMotor.set(Constants.LinearActuator.linearActuatorMotorSpeedDown);
            SmartDashboard.putString("State", "REVERSED");
        }

        if (getLimitSwitch() == true) {
            linearActuatorMotor.getEncoder().setPosition(0);
        }

        SmartDashboard.putBoolean("Limit Switch Arm", getLimitSwitch());
        SmartDashboard.putBoolean("Test", true);


        //SmartDashboard Displays
        SmartDashboard.putNumber("linearActuatorRot", getLinearActuatorMotorPosition());
        SmartDashboard.putNumber("linearActuatorMotorSpeed", getLinearActuatorMotorSpeed());

    }

}
 