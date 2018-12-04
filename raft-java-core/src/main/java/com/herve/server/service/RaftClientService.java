package com.herve.server.service;


import com.herve.proto.RaftMessage;

/**
 * raft集群管理接口。
 * Created by wenweihu86 on 2017/5/14.
 */
public interface RaftClientService {

    /**
     * get leader info
     * @param request
     * @return
     */
    RaftMessage.GetLeaderResponse getLeader(RaftMessage.GetLeaderRequest request);

    /**
     * get raft cluster info
     * @param request
     * @return
     */
    RaftMessage.GetConfigurationResponse getConfiguration(RaftMessage.GetConfigurationRequest request);

    /**
     * add node to cluster
     * @param request
     * @return
     */
    RaftMessage.AddPeersResponse addPeers(RaftMessage.AddPeersRequest request);

    /**
     * delete node from cluster
     * @param request
     * @return
     */
    RaftMessage.RemovePeersResponse removePeers(RaftMessage.RemovePeersRequest request);
}
