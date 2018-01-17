package ch.abtomik.kura.example.motion_osgi;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Meta type information for {@link MotionOsgi }
 * <p>
 * <strong>Note: </strong> The id must be the full qualified name of the assigned component.
 * </p>
 */
@ObjectClassDefinition(id="ch.abtomik.kura.example.motion_osgi.MotionOsgi", name="LED RPI", description="Simple RPI LED example")
@interface Config {
        @AttributeDefinition(name = "Some string", description = "This is just some string value")
        String someString() default "Hello World";

        @AttributeDefinition(name = "Enabled", description = "Whether the component is enabled or not")
        boolean enabled() default true;
}
