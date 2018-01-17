package ch.abtomik.kura.example.motion_osgi;

import java.util.Map;
import java.io.IOException;
import org.eclipse.kura.configuration.ConfigurableComponent;
import static org.eclipse.kura.camel.component.Configuration.asInt;
import static org.eclipse.kura.camel.component.Configuration.asString;
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
import com.pi4j.io.gpio.Pin;


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
    public void activate(final Map<String, ?> properties) throws Exception {
        System.out.println("activate");

        //fetch the Led Light Pin Number from the properties
        int ledPinNumber = asInt(properties, "ledPinNumber", 1);
        //int ledPinNumber = 01;


        //fetch the Motion Detection Pin Number from the properties 
        int motionSensorPinNumber = asInt(properties, "sensor",0);
        //int motionSensorPinNumber = 00;
        gpioSensor = GpioFactory.getInstance();    
        sensor = gpioSensor.provisionDigitalInputPin(getPinId(motionSensorPinNumber), PinPullResistance.PULL_DOWN);

        gpioLed = GpioFactory.getInstance();
        led = gpioLed.provisionDigitalOutputPin(getPinId(ledPinNumber), "MyLED", PinState.HIGH);

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

    private Pin getPinId(Integer pinId) {
    	switch(pinId.intValue()){
    	case 0:
    		return RaspiPin.GPIO_00;
    	case 1:
    		return RaspiPin.GPIO_01;
    	case 2:
    		return RaspiPin.GPIO_02;
    	case 3:
    		return RaspiPin.GPIO_03;
    	case 4:
    		return RaspiPin.GPIO_04;
    	case 5:
    		return RaspiPin.GPIO_05;
    	case 6:
    		return RaspiPin.GPIO_06;
    	case 7:
    		return RaspiPin.GPIO_07;
    	case 8:
    		return RaspiPin.GPIO_08;
    	case 9:
    		return RaspiPin.GPIO_09;
    	case 10:
    		return RaspiPin.GPIO_10;
    	case 11:
    		return RaspiPin.GPIO_11;
    	case 12:
    		return RaspiPin.GPIO_12;
    	case 13:
    		return RaspiPin.GPIO_13;
    	case 14:
    		return RaspiPin.GPIO_14;
    	case 15:
    		return RaspiPin.GPIO_15;
    	case 16:
    		return RaspiPin.GPIO_16;
    	case 17:
    		return RaspiPin.GPIO_17;
    	case 18:
    		return RaspiPin.GPIO_18;
    	case 19:
    		return RaspiPin.GPIO_19;
    	case 20:
    		return RaspiPin.GPIO_20;
    	default:
    		throw new IllegalArgumentException("endereco inexistente.");
    	}
    }
}
