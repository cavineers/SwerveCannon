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
    public CANSparkMax linearActuatorMotor = new CANSparkMax(Constants.LinearActuator.linearActuatorMotor, MotorType.kBrushless);
    public DigitalInput limitSwitch = new DigitalInput(0);

    //List States
    public enum LinearActuatorMotorState {
        ON,
        OFF,
        REVERSED
    }

    //Set Initial Motor State
    public LinearActuatorMotorState linearActuatorMotorState = LinearActuatorMotorState.OFF;

    //Motor Settings
    public LinearActuator() {

        this.linearActuatorMotor.setIdleMode(IdleMode.kBrake);

        this.linearActuatorMotor.setInverted(false);

        this.linearActuatorMotor.setSmartCurrentLimit(51);
    }

    //Changing Motor States
    public void setLinearActuatorMotorState(LinearActuatorMotorState state) {

        this.linearActuatorMotorState = state;

        switch(state) {
            
            case ON:
                
                break;
            
            case OFF:
                
                break;

            case REVERSED:
                
                break;

            default:
                this.setLinearActuatorMotorState(LinearActuatorMotorState.OFF);
        }

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

    public void periodic(){
        
        //ON State
        if (this.linearActuatorMotorState == LinearActuatorMotorState.ON) {

            if (this.linearActuatorMotor.get() < Constants.LinearActuator.linearActuatorMotorSpeedUp) {
                this.linearActuatorMotor.set(this.linearActuatorMotor.get() + Constants.LinearActuator.linearActuatorMotorEaseFactor);
            }
            
            else {
                this.linearActuatorMotor.set(Constants.LinearActuator.linearActuatorMotorSpeedUp);
            }

            SmartDashboard.putString("State", "ON");

        }

        //OFF State
        if (this.linearActuatorMotorState == LinearActuatorMotorState.OFF){

            if (this.linearActuatorMotor.get() > Constants.LinearActuator.linearActuatorMotorEaseOutThreshold) {
                this.linearActuatorMotor.set(this.linearActuatorMotor.get() - (Constants.LinearActuator.linearActuatorMotorEaseOutMultiplier * Constants.LinearActuator.linearActuatorMotorEaseFactor));
            }
            else if (this.linearActuatorMotor.get() < -(Constants.LinearActuator.linearActuatorMotorEaseOutThreshold)) {
                this.linearActuatorMotor.set(this.linearActuatorMotor.get() + (Constants.LinearActuator.linearActuatorMotorEaseOutMultiplier * Constants.LinearActuator.linearActuatorMotorEaseFactor));
            }
            else {
                this.linearActuatorMotor.set(0);
            }

            SmartDashboard.putString("State", "OFF");
            
        }

        //REVERSED State
        if (this.linearActuatorMotorState == LinearActuatorMotorState.REVERSED){
            
            if (this.linearActuatorMotor.get() > Constants.LinearActuator.linearActuatorMotorSpeedDown) {
                this.linearActuatorMotor.set(this.linearActuatorMotor.get() - Constants.LinearActuator.linearActuatorMotorEaseFactor);
            } 
            
            else {
                this.linearActuatorMotor.set(Constants.LinearActuator.linearActuatorMotorSpeedDown);
            }

            SmartDashboard.putString("State", "REVERSED");

        }

        if (limitSwitch.get() == true) {

            linearActuatorMotor.getEncoder().setPosition(0);
        }

        //SmartDashboard Displays
        SmartDashboard.putNumber("linearActuatorRot", getLinearActuatorMotorPosition());
        SmartDashboard.putNumber("linearActuatorMotorSpeed", getLinearActuatorMotorSpeed());

    }

}
