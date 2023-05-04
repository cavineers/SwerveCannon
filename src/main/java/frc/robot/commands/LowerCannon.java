package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.LinearActuator;

public class LowerCannon extends CommandBase {
    
    private boolean isDone = false;
    private double m_timestamp;

    public LowerCannon() {
        this.addRequirements(Robot.linearActuator);
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        this.isDone = false;
    }

    @Override
    public void execute() {
        // Raises cannon
        if (Robot.linearActuator.getLinearActuatorMotorPosition() >= Constants.LinearActuator.linearActuatorMotorMinRot) {
            Robot.linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.REVERSED);
        } else {
            Robot.linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
        }
    }   

    @Override
    public void end(boolean interrupted) {
        Robot.linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
    }

    @Override
    public boolean isFinished() {
        if (Timer.getFPGATimestamp() - this.m_timestamp >= 0 && Robot.m_robotContainer.xbox.getRawButton(0)) {
            this.isDone = true;
        }

        return this.isDone;
    }
}