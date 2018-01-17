package ch.abtomik.kura.example.motion_osgi;
import java.io.IOException;
import org.eclipse.kura.configuration.ConfigurableComponent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import static org.osgi.service.component.annotations.ConfigurationPolicy.REQUIRE;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;


@Designate(ocd = Config.class)
@Component(immediate = true, configurationPolicy = REQUIRE)

public class MotionOsgi implements ConfigurableComponent {

    public GpioController gpioSensor;
    public GpioPinDigitalInput sensor;

    public GpioController gpioLed; 
    public GpioPinDigitalOutput led;

    private static final Logger s_logger = LoggerFactory.getLogger(MotionOsgi.class);
    private static final String APP_ID = "ch.abtomik.kura.example.motion_osgi";

    @Activate
    public void activate() {
        System.out.println("activate");
        gpioSensor = GpioFactory.getInstance();    
        sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);

        gpioLed = GpioFactory.getInstance();
        led = gpioLed.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

        detectMotion(sensor,led);
    }

    @Deactivate
    public void deactivate() {

        s_logger.info("Bundle " + APP_ID + " has stopped!");
        System.out.println("deactivate");
        gpioSensor.shutdown();
        gpioSensor.unprovisionPin(sensor);

        gpioLed.shutdown();
        gpioLed.unprovisionPin(led);
    }

    private void turnOn(GpioPinDigitalOutput led){
        
        System.out.println("<--Pi4J--> GPIO Control Example ... started.");

        // set shutdown state for this pin
        //led.setShutdownOptions(true, PinState.LOW);

        System.out.println("--> GPIO state should be: ON");
        
        led.pulse(5000, true);

        // turn off gpio pin #01
        //led.low();
        System.out.println("--> GPIO state should be: OFF");

        //led.toggle();
        
        //try {
        //    Thread.sleep(5000);
        //} catch(InterruptedException ex) {
        //    Thread.currentThread().interrupt();
        //}

  

    }

    private void detectMotion(GpioPinDigitalInput motion_sensor, GpioPinDigitalOutput led) {
        System.out.println(motion_sensor);
        motion_sensor.addListener(new GpioPinListenerDigital() 
      {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) 
        {
          if (event.getState().isHigh())
          { 
            s_logger.info(" >>> GPIO pin state changed: time=" + System.currentTimeMillis() + ", " + event.getPin() + " = " + event.getState());
            turnOn(led);
            s_logger.info("Motion detected"); 
          }
        }
      });
    }
}
