package com.herve.server;

import com.github.wenweihu86.rpc.server.RPCServer;
import com.herve.RaftNode;
import com.herve.RaftOptions;
import com.herve.proto.RaftMessage;
import com.herve.server.service.ExampleService;
import com.herve.server.service.RaftClientService;
import com.herve.server.service.RaftConsensusService;
import com.herve.server.service.impl.ExampleServiceImpl;
import com.herve.server.service.impl.RaftClientServiceImpl;
import com.herve.server.service.impl.RaftConsensusServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengwenjie on 2018/12/3.
 */
public class ServerMain {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.printf("Usage: ./run_server.sh DATA_PATH CLUSTER CURRENT_NODE\n");
            System.exit(-1);
        }

        // parse args
        // raft data dir
        String dataPath = args[0];
        // peers, format is "host1:port1:serverId1,host2:port2:serverId2"
        String servers = args[1];
        String[] splitArray = servers.split(",");
        List<RaftMessage.Server> serverList = new ArrayList<>();
        for (String serverString : splitArray) {
            RaftMessage.Server server = parseServer(serverString);
            serverList.add(server);
        }
        // local server
        RaftMessage.Server localServer = parseServer(args[2]);

        // init RPCServer
        RPCServer server = new RPCServer(localServer.getEndPoint().getPort());
        // RaftOptions
        // just for test snapshot
        RaftOptions raftOptions = new RaftOptions();
        raftOptions.setDataDir(dataPath);
        raftOptions.setSnapshotMinLogSize(10 * 1024);
        raftOptions.setSnapshotPeriodSeconds(30);
        raftOptions.setMaxSegmentFileSize(1024 * 1024);
        // application state machine
        ExampleStateMachine stateMachine = new ExampleStateMachine(raftOptions.getDataDir());
        // init RaftNode
        RaftNode raftNode = new RaftNode(raftOptions, serverList, localServer, stateMachine);
        // register rpc between raft nodes
        RaftConsensusService raftConsensusService = new RaftConsensusServiceImpl(raftNode);
        server.registerService(raftConsensusService);
        // register for client
        RaftClientService raftClientService = new RaftClientServiceImpl(raftNode);
        server.registerService(raftClientService);
        // register for self
        ExampleService exampleService = new ExampleServiceImpl(raftNode, stateMachine);
        server.registerService(exampleService);
        // start RPCServer, init raft node
        server.start();
        raftNode.init();
    }

    private static RaftMessage.Server parseServer(String serverString) {
        String[] splitServer = serverString.split(":");
        String host = splitServer[0];
        Integer port = Integer.parseInt(splitServer[1]);
        Integer serverId = Integer.parseInt(splitServer[2]);
        RaftMessage.EndPoint endPoint = RaftMessage.EndPoint.newBuilder()
                .setHost(host).setPort(port).build();
        RaftMessage.Server.Builder serverBuilder = RaftMessage.Server.newBuilder();
        RaftMessage.Server server = serverBuilder.setServerId(serverId).setEndPoint(endPoint).build();
        return server;
    }
}
