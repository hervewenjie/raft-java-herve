package com.herve.service;

import com.herve.proto.RaftMessage;

/**
 * communication between raft nodes
 * Created by chengwenjie on 2018/11/30.
 */
public interface RaftConsensusService {

    RaftMessage.VoteResponse preVote(RaftMessage.VoteRequest request);

    RaftMessage.VoteResponse requestVote(RaftMessage.VoteRequest request);

    RaftMessage.AppendEntriesResponse appendEntries(RaftMessage.AppendEntriesRequest request);

    RaftMessage.InstallSnapshotResponse installSnapshot(RaftMessage.InstallSnapshotRequest request);

}
