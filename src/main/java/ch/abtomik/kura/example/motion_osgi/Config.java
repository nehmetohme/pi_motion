package ch.abtomik.kura.example.motion_osgi;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.AttributeType;


@ObjectClassDefinition(id="ch.abtomik.kura.example.motion_osgi.MotionOsgi", name="LED RPI", description="Simple RPI LED example")
/*
@interface Config {
        @AttributeDefinition(name = "Enabled", description = "Whether the component is enabled or not")
        boolean enabled() default true;

        //@AttributeDefinition(name = "Motion Detection Sensor Pin Number", required = true)
        //public int motionSensorPinNumber() default 0;

        @AttributeDefinition(name = "Led Pin Number", required = true)
        public int ledPinNumber() default 1;

        @AttributeDefinition(name = "Dummy Field")
        public String dummyField() default "test";
}
*/
@interface Config {

    @AttributeDefinition(
        name = "Led Pin Number",
        description = "Please specify the led pin number.",
        type = AttributeType.LONG
    )
    public int led() default 1;

    @AttributeDefinition(
        name = "Motion Detector Pin Number",
        description = "Please specify the motion detection sensor pin number.",
        type = AttributeType.LONG
    )
    public int sensor() default 0;
}

