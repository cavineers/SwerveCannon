package frc.robot.subsystems;

import frc.robot.Constants;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDs extends SubsystemBase {
    private AddressableLED ledStrip = new AddressableLED(Constants.LED.kLEDPWMPort);
    private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(111);

    private AddressableLEDBufferView right = new AddressableLEDBufferView(ledBuffer, 0, 39);
    private AddressableLEDBufferView left = new AddressableLEDBufferView(ledBuffer, 40, 79);

    private boolean compressorOn = false;
    private boolean leftBarrelArmed = false;
    private boolean rightBarrelArmed = false;

    public LEDs(Cannon cannon){
        this.compressorOn = cannon.getCompressorON();
        this.leftBarrelArmed = cannon.getLeftBarrelArmed();
        this.rightBarrelArmed = cannon.getRightBarrelArmed();
    }
    public void periodic() {
        LEDPattern basePattern = LEDPattern.solid(Color.kDarkRed);

        basePattern.applyTo(left);
        basePattern.applyTo(right);

        if(compressorOn){
            setCompressorEffect();
        }
        if(leftBarrelArmed || rightBarrelArmed){
            setBarrelArmedEffect();
        }
    }

    //Scroll Pattern -- Compressor on
    public void setCompressorEffect(){
        LEDPattern redWhiteGradient = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kRed, Color.kWhiteSmoke);
        LEDPattern pattern = redWhiteGradient.scrollAtRelativeSpeed(Percent.per(Second).of(25));
        //LEDPattern absolute = redWhiteGradient.scrollAtAbsoluteSpeed(Centimeters.per(Second).of(12.5), Constants.LED.kLedSpacing);
        pattern.applyTo(left);
        pattern.applyTo(right);
    }

    //Blinking Pattern -- Barrel Armed
    public void setBarrelArmedEffect(){
        LEDPattern redWhiteGradient = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kRed, Color.kWhiteSmoke);
        LEDPattern pattern = redWhiteGradient.blink(Seconds.of(2), Seconds.of(1));
        if(leftBarrelArmed && rightBarrelArmed){
            pattern.applyTo(left);
            pattern.applyTo(right);
        } else if(leftBarrelArmed){
            pattern.applyTo(left);
        } else if(rightBarrelArmed){
            pattern.applyTo(right);
        }
    }
}
