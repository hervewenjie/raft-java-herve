package com.herve.util;

import com.herve.proto.RaftMessage;

import java.util.List;

/**
 * Created by chengwenjie on 2018/11/30.
 */
public class ConfigurationUtils {

    // configuration not too big, iterate directly
    public static boolean containsServer(RaftMessage.Configuration configuration, int serverId) {
        for (RaftMessage.Server server : configuration.getServersList()) {
            if (server.getServerId() == serverId) {
                return true;
            }
        }
        return false;
    }

    public static RaftMessage.Configuration removeServers(
            RaftMessage.Configuration configuration, List<RaftMessage.Server> servers) {
        RaftMessage.Configuration.Builder confBuilder = RaftMessage.Configuration.newBuilder();
        for (RaftMessage.Server server : configuration.getServersList()) {
            boolean toBeRemoved = false;
            for (RaftMessage.Server server1 : servers) {
                if (server.getServerId() == server1.getServerId()) {
                    toBeRemoved = true;
                    break;
                }
            }
            if (!toBeRemoved) {
                confBuilder.addServers(server);
            }
        }
        return confBuilder.build();
    }

    public static RaftMessage.Server getServer(RaftMessage.Configuration configuration, int serverId) {
        for (RaftMessage.Server server : configuration.getServersList()) {
            if (server.getServerId() == serverId) {
                return server;
            }
        }
        return null;
    }

}
