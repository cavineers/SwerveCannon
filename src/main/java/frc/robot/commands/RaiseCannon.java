package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.LinearActuator;

public class RaiseCannon extends CommandBase{
    
    private boolean isDone = false;
    private double m_timestamp;

    public RaiseCannon() {
        this.addRequirements(Robot.linearActuator);
    }

     // Set Motor State to ON / OFF
     @Override
     public void initialize() {
         this.isDone = false;
     }
 
     @Override
     public void execute() {
         // 
         if (Robot.linearActuator.getAngleControlMotorPosition() < Constants.AngleControl.angleControlMotorMaxRot) {
             Robot.linearActuator.setAngleControlMotorState(LinearActuator.angleControlMotorState.ON);
    }   else {
             Robot.linearActuator.setAngleControlMotorState(LinearActuator.angleControlMotorState.OFF);
    }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.linearActuator.setAngleControlMotorState(LinearActuator.angleControlMotorState.OFF);
    }

}
