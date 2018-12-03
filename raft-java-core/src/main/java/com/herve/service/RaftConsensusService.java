package com.herve.service;

import com.herve.proto.RaftMessage;

/**
 * communication between raft nodes
 * Created by chengwenjie on 2018/11/30.
 */
public interface RaftConsensusService {

    RaftMessage.VoteResponse preVote(RaftMessage.VoteRequest request);

    /**
     * Invoked by candidates to gather votes
     * @param request
     * @return
     */
    RaftMessage.VoteResponse requestVote(RaftMessage.VoteRequest request);

    /**
     * Invoked by leader to replicate log entries, also used as heartbeat
     * @param request
     * @return
     */
    RaftMessage.AppendEntriesResponse appendEntries(RaftMessage.AppendEntriesRequest request);

    /**
     * install snapshot request
     * @param request
     * @return
     */
    RaftMessage.InstallSnapshotResponse installSnapshot(RaftMessage.InstallSnapshotRequest request);

}
