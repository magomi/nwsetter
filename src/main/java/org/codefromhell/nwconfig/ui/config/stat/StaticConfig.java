package org.codefromhell.nwconfig.ui.config.stat;

import org.codefromhell.nwconfig.ui.config.NwConfig;

/**
 * TODO
 *
 * @author Grunert, Marco (magomi@gmail.com)
 */
public class StaticConfig implements NwConfig {
    private String ifName;
    private String ip;
    private String nwMask;
    private String netAddress;
    private String broadcastAddress;
    private String nameServer;
    private String defaultGw;

    public StaticConfig(String ifName) {
        this.ifName = ifName;
    }

    @Override
    public String getConfig() {
        return "auto " + ifName + "\n"
                + "iface " + ifName
                + "address " + ip
                + "netmask " + nwMask
                + "gateway " + defaultGw
                + "network " + netAddress
                + "broadcast " + broadcastAddress;
    }

    @Override
    public void setConfig(String config) {

    }

    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNwMask() {
        return nwMask;
    }

    public void setNwMask(String nwMask) {
        this.nwMask = nwMask;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public String getBroadcastAddress() {
        return broadcastAddress;
    }

    public void setBroadcastAddress(String broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getDefaultGw() {
        return defaultGw;
    }

    public void setDefaultGw(String defaultGw) {
        this.defaultGw = defaultGw;
    }
}
