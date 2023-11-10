package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LinearActuator;

public class HomeCannon extends CommandBase {

    private boolean isDone = false;
    private double timestamp;
    private LinearActuator linearActuator;

    public HomeCannon(LinearActuator linearActuator) {
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
        // Homes cannon
        // if (sensor != true) {
        linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.REVERSED);
        // } else {
        linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
        linearActuator.setLinearActuatorMotorPosition(0.0);

        // }
    }

    @Override
    public void end(boolean interrupted) {
        linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
    }

    @Override
    public boolean isFinished() {

        this.isDone = true;

        return this.isDone;
    }
}
