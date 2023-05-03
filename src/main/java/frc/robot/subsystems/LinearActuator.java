package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class LinearActuator extends subsystems {
    
    //Initalize Motor
    public CANSparkMax angleControlMotor;

    //List States
    public enum AngleControlMotorState {
        ON,
        OFF,
        REVERSED
    }

    //Set Initial Motor State
    public AngleControlMotorState angleControlMotor;

    //Motor Settings
    public LinearActuator() {
        angleControlMotor = new CANSparkMax(Constants.AngleControl.angleControlMotor, MotorType.kBrushless);

        angleControlMotor = AngleControlMotorState.OFF


        this.angleControlMotor.setIdleMode(IdleMode.kBrake);

        this.angleControlMotor.setInverted(false);

        this.angleControlMotor.setSmartCurrentLimit(51);
    }

    //Changing Motor States
    public void setAngleControlMotorState(angleControlMotorState, State)

    //Gets Motor Position
    public double getAngleControlMotorPosition() {
        return this.angleControlMotor.getEncoder().getPosition();
    }

    //Sets Encoder Value 
    public void setAngleControlMotorPosition(double position) {
        this.angleControlMotor.getEncoder().setPosition(position);
    }

    public angleControlMotor getAngleControlMotorState() {
        return this.angleControlMotorState
    }
}
