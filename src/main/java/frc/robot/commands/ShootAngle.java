package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LinearActuator;

public class ShootAngle extends CommandBase {
    
    private double angle;
    private boolean isDone = false;
    private double m_timestamp;
    private LinearActuator linearActuator;

    public ShootAngle(LinearActuator linearActuator) {

        // Get the desired number of rotations from the SmartDashboard

        /*
         * MATH NEEDED HERE THAT CONVERTS ANGLE TO AMOUNT OF ROTATIONS. TBD
         */

        angle = SmartDashboard.getNumber("Shooting angle", 0);

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

        if (linearActuator.getLinearActuatorMotorPosition() >= Constants.LinearActuator.linearActuatorMotorMinRot && linearActuator.getLinearActuatorMotorPosition() <= Constants.LinearActuator.linearActuatorMotorMaxRot) {
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
