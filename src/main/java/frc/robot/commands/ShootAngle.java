package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.LinearActuator;

public class ShootAngle extends Command {

    private double angle;
    private double rotations;
    private boolean isDone = false;
    private double timestamp;
    private LinearActuator linearActuator;

    public ShootAngle(LinearActuator linearActuator) {

        // Get the desired number of rotations from the SmartDashboard

        /*
         * MATH NEEDED HERE THAT CONVERTS ANGLE TO AMOUNT OF ROTATIONS. TBD
         */

        angle = SmartDashboard.getNumber("Shooting angle", 0);
        // rotations = angle
        // Insert math here

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

        if (linearActuator.getLinearActuatorMotorPosition() >= Constants.LinearActuator.linearActuatorMotorMinRot
                && linearActuator
                        .getLinearActuatorMotorPosition() <= Constants.LinearActuator.linearActuatorMotorMaxRot) {
            // Raises cannon
            if (linearActuator.getLinearActuatorMotorPosition() <= angle) {
                linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.ON);
            } else {
                linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
            }

            // Raises cannon
            if (linearActuator.getLinearActuatorMotorPosition() >= angle) {
                linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.REVERSED);
            } else {
                linearActuator.setLinearActuatorMotorState(LinearActuator.LinearActuatorMotorState.OFF);
            }
        }
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
