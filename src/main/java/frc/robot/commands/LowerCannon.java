package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.LinearActuator;

public class LowerCannon extends Command {

    private boolean isDone = false;
    private double timestamp;
    private LinearActuator linearActuator;

    public LowerCannon(LinearActuator linearActuator) {
        this.linearActuator = linearActuator;
        this.addRequirements(linearActuator);
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        this.isDone = false;
    }

    @Override
    public void execute() {
        // Lower cannon
        if (linearActuator.getLinearActuatorMotorPosition() >= (Constants.LinearActuator.linearActuatorMotorMinRot
                + Constants.LinearActuator.linearActuatorMotorEaseOutThreshold)) {
            linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.REVERSED);
        } else {
            System.out.println("CAN NOT LOWER");            
            linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
        }
    }

    @Override
    public void end(boolean interrupted) {
        linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
    }

    @Override
    public boolean isFinished() {
        if (Timer.getFPGATimestamp() - this.timestamp >= 0 && Robot.robotContainer.xbox.getRawButton(0)) {
            this.isDone = true;
        }
        return this.isDone;
    }
}
