package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LinearActuator extends SubsystemBase {
    
    //Initalize Motor
    public CANSparkMax linearActuatorMotor = new CANSparkMax(Constants.LinearActuator.linearActuatorMotor, MotorType.kBrushless);;

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
                    this.linearActuatorMotor.set(Constants.LinearActuator.linearActuatorMotorSpeedUp);
                break;
            
            case OFF:
                this.linearActuatorMotor.set(0);
                break;

            case REVERSED:
                this.linearActuatorMotor.set(Constants.LinearActuator.linearActuatorMotorSpeedDown);
                break;

            default:
                this.setLinearActuatorMotorState(linearActuatorMotorState.OFF);
        }

    }

    //Gets Motor Position
    public double getLinearActuatorMotorPosition() {
        return this.linearActuatorMotor.getEncoder().getPosition();
    }

    //Sets Encoder Value 
    public void setLinearActuatorMotorPosition(double position) {
        this.linearActuatorMotor.getEncoder().setPosition(position);
    }

    public LinearActuatorMotorState getAngleControlMotorState() {
        return this.linearActuatorMotorState;
    }
}
