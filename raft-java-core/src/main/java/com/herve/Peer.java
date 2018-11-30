package com.herve;

import com.github.wenweihu86.rpc.client.EndPoint;
import com.github.wenweihu86.rpc.client.RPCClient;
import com.github.wenweihu86.rpc.client.RPCProxy;
import com.herve.proto.RaftMessage;
import com.herve.service.RaftConsensusService;
import com.herve.service.RaftConsensusServiceAsync;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by chengwenjie on 2018/11/30.
 */
public class Peer {

    @Getter private RaftMessage.Server server;
    @Getter private RPCClient rpcClient;
    @Getter private RaftConsensusService raftConsensusService;
    @Getter private RaftConsensusServiceAsync raftConsensusServiceAsync;
    // 需要发送给follower的下一个日志条目的索引值，只对leader有效
    @Getter @Setter private long nextIndex;
    // 已复制日志的最高索引值
    @Getter @Setter private long matchIndex;
    @Getter @Setter private volatile Boolean isVoteGranted;
    @Getter @Setter private volatile Boolean isCatchUp;


    public Peer(RaftMessage.Server server) {
        this.server = server;
        this.rpcClient = new RPCClient(new EndPoint(
                server.getEndPoint().getHost(),
                server.getEndPoint().getPort()));
        raftConsensusService = RPCProxy.getProxy(rpcClient, RaftConsensusService.class);
        raftConsensusServiceAsync = RPCProxy.getProxy(rpcClient, RaftConsensusServiceAsync.class);
        isCatchUp = false;
    }

}
