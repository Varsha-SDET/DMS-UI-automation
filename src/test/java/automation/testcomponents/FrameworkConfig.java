package automation.testcomponents;

import org.aeonbits.owner.Config;
/*
 *@className - FrameworkConfig
 * @classObjective- To define an Interface for all the values for framework config properties value and providing default values.
 */
@Config.Sources(value = "file:${user.dir}/src/main/java/automation/resources/FrameworkConfig.properties")
//config file path pass to access its values
public interface FrameworkConfig extends Config {
    //In absense of browser value passed chrome will be taken by default
    @DefaultValue("CHROME")
    BrowserType browser();
}
