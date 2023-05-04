package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LinearActuator extends SubsystemBase {
    
    //Initalize Motor
    public CANSparkMax angleControlMotor;

    //List States
    public enum AngleControlMotorState {
        ON,
        OFF,
        REVERSED
    }

    //Set Initial Motor State
    public AngleControlMotorState angleControlMotorState;

    //Motor Settings
    public LinearActuator() {

        angleControlMotor = new CANSparkMax(Constants.AngleControl.angleControlMotor, MotorType.kBrushless);

        angleControlMotorState = AngleControlMotorState.OFF;


        this.angleControlMotor.setIdleMode(IdleMode.kBrake);

        this.angleControlMotor.setInverted(false);

        this.angleControlMotor.setSmartCurrentLimit(51);
    }

    //Changing Motor States
    public void setAngleControlMotorState(angleControlMotorState state) {

        this.angleControlMotorState = state;

        switch(state) {
            
            case ON:
                    this.angleControlMotor.set(Constants.AngleControl.angleControlMotorSpeedUp);
                break;
            
            case OFF:
                this.angleControlMotor.set(0);
                break;

            case REVERSED:
                this.angleControlMotor.set(Constants.AnlgeControl.angleControlMotorSpeedDown);
                break;

            default:
                this.setAngleControlMotorState(angleControlMotorState.OFF);
        }

    }

    //Gets Motor Position
    public double getAngleControlMotorPosition() {
        return this.angleControlMotor.getEncoder().getPosition();
    }

    //Sets Encoder Value 
    public void setAngleControlMotorPosition(double position) {
        this.angleControlMotor.getEncoder().setPosition(position);
    }

    public AngleControlMotorState getAngleControlMotorState() {
        return this.angleControlMotorState;
    }
}
