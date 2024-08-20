package tech.skullprogrammer.taxicab.msprofile.model;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "tech.skullprogrammer.taxicab.msprofile")
public interface ProfileConfig {

    int minYears();

}
