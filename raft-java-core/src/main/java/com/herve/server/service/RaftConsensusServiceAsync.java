package com.herve.server.service;

import com.github.wenweihu86.rpc.client.RPCCallback;
import com.herve.proto.RaftMessage;

import java.util.concurrent.Future;

/**
 *
 * Created by chengwenjie on 2018/11/30.
 */
public interface RaftConsensusServiceAsync extends RaftConsensusService {

    Future<RaftMessage.VoteResponse> preVote(
            RaftMessage.VoteRequest request,
            RPCCallback<RaftMessage.VoteResponse> callback);

    Future<RaftMessage.VoteResponse> requestVote(
            RaftMessage.VoteRequest request,
            RPCCallback<RaftMessage.VoteResponse> callback);

    Future<RaftMessage.AppendEntriesResponse> appendEntries(
            RaftMessage.AppendEntriesRequest request,
            RPCCallback<RaftMessage.AppendEntriesResponse> callback);

    Future<RaftMessage.InstallSnapshotResponse> installSnapshot(
            RaftMessage.InstallSnapshotRequest request,
            RPCCallback<RaftMessage.InstallSnapshotResponse> callback);
}
