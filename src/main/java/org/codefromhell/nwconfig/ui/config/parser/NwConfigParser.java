package org.codefromhell.nwconfig.ui.config.parser;

import org.codefromhell.nwconfig.ui.config.NwConfig;
import org.codefromhell.nwconfig.ui.config.dhcp.DhcpConfig;
import org.codefromhell.nwconfig.ui.config.stat.StaticConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1) read the main file
 * 2) get the actual interfaces
 * 3) read the config file for every interface
 * <p/>
 * For details about the config files: "man interfaces" on linux systems.
 *
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
public class NwConfigParser {
    private static List<NwConfig> nwConfigList = new ArrayList<NwConfig>();

    public static List<NwConfig> getConfigs(String baseConfigPath) throws NwConfigParseException {
        FileReader baseConfigFileReader = null;
        try {
            baseConfigFileReader = new FileReader(baseConfigPath + "/interfaces");
        } catch (FileNotFoundException fnfEx) {
            throw new NwConfigParseException("Unable to open file " + baseConfigPath + "/interfaces", fnfEx);
        }
        LineNumberReader baseConfigLineNumberReader = new LineNumberReader(baseConfigFileReader);
        try {
            String line = baseConfigLineNumberReader.readLine();
            while (null != line) {
                if (line.trim().startsWith("auto")) {
                    StringTokenizer interfaces = new StringTokenizer(line);
                    interfaces.nextToken();
                    while (interfaces.hasMoreTokens()) {
                        String ifName = interfaces.nextToken();
                        if (!ifName.equalsIgnoreCase("lo")) {
                            nwConfigList.add(parse(ifName, baseConfigPath + "/interfaces.d/" + ifName));
                        }
                    }
                }
                line = baseConfigLineNumberReader.readLine();
            }
        } catch (IOException fnfEx) {
            throw new NwConfigParseException("Unable to read from file " + baseConfigPath + "/interfaces", fnfEx);
        }
        return nwConfigList;
    }

    private static NwConfig parse(String ifName, String configFileName) throws NwConfigParseException {
        FileReader configFileReader = null;
        try {
            configFileReader = new FileReader(configFileName);
        } catch (FileNotFoundException fnfEx) {
            throw new NwConfigParseException("Unable to open file " + configFileName, fnfEx);
        }
        LineNumberReader configLineNumberReader = new LineNumberReader(configFileReader);

        String defLine = null;
        try {
            defLine = configLineNumberReader.readLine();
            while (null != defLine) {
                if (defLine.startsWith("iface " + ifName + " inet")) {
                    break;
                }
                defLine = configLineNumberReader.readLine();
            }
        } catch (IOException ioEx) {
            throw new NwConfigParseException("Unable to read from file " + configFileName, ioEx);
        }
        if (null == defLine) {
            throw new NwConfigParseException("Unable to find a definition line (iface " + ifName + " inet...) in file " + configFileName);
        }

        if (defLine.trim().endsWith("dhcp")) {
            return new DhcpConfig(ifName);
        } else {
            StaticConfig staticConfig = new StaticConfig(ifName);
            try {
                String parLine = configLineNumberReader.readLine();
                while (null != parLine) {
                    if (parLine.trim().startsWith("address")) {
                        staticConfig.setIp(parLine.trim().substring(7).trim());
                    } else if (parLine.trim().startsWith("netmask")) {
                        staticConfig.setNwMask(parLine.trim().substring(7).trim());
                    } else if (parLine.trim().startsWith("network")) {
                        staticConfig.setNetAddress(parLine.trim().substring(7).trim());
                    } else if (parLine.trim().startsWith("broadcast")) {
                        staticConfig.setBroadcastAddress(parLine.trim().substring(9).trim());
                    }
                    parLine = configLineNumberReader.readLine();
                }
            } catch (IOException ioEx) {
                throw new NwConfigParseException("Unable to read from file " + configFileName, ioEx);
            }
            return staticConfig;
        }
    }
}
