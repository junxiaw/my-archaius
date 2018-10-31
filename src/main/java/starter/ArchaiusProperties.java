package starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangjunxia
 * @date 2018-10-29
 */
@ConfigurationProperties(prefix = "tenxcloud.archaius")
public class ArchaiusProperties {
    private String serverName = "default";
    private int initialDelayMillis = 30000;
    private int delayMillis = 60000;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getInitialDelayMillis() {
        return initialDelayMillis;
    }

    public void setInitialDelayMillis(int initialDelayMillis) {
        this.initialDelayMillis = initialDelayMillis;
    }

    public int getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }
}
