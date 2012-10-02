package org.codefromhell.nwconfig.ui.config.dhcp;

import org.codefromhell.nwconfig.ui.config.NwConfig;

/**
 * TODO
 *
 * @author Grunert, Marco (magomi@gmail.com)
 */
public class DhcpConfig implements NwConfig {
    private String ifName;

    public DhcpConfig(String ifName) {
        this.ifName = ifName;
    }

    @Override
    public String getConfig() {
        return "auto " + ifName + "\niface " + ifName + " inet dhcp";
    }

    @Override
    public void setConfig(String config) {

    }

    @Override
    public String getIfName() {
        return ifName;
    }
}
