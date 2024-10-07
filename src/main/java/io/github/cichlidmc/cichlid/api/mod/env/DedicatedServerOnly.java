package io.github.cichlidmc.cichlid.api.mod.env;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alias for {@link EnvOnly}, equivalent to specifying the environment as {@link Environments#DEDICATED_SERVER}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DedicatedServerOnly {
}
