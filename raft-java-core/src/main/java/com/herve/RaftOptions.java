package com.herve;

import lombok.Data;

/**
 * raft config
 * Created by chengwenjie on 2018/11/29.
 */
@Data
public class RaftOptions {

    // A follower would become a candidate if it doesn't receive any message
    // from the leader in electionTimeoutMs milliseconds
    private int electionTimeoutMilliseconds = 5000;

    // A leader sends RPCs at least this often, even if there is no data to send
    private int heartbeatPeriodMilliseconds = 500;

    // snapshot timer
    private int snapshotPeriodSeconds = 3600;
    // log entry size reaches snapshotMinLogSize, do snapshot
    private int snapshotMinLogSize = 100 * 1024 * 1024;
    private int maxSnapshotBytesPerRequest = 500 * 1024; // 500k

    private int maxLogEntriesPerRequest = 5000;

    // single segment file size, default 100m
    private int maxSegmentFileSize = 100 * 1000 * 1000;

    // follower & leader gap catchupMargin, can attend election and provide service
    private long catchupMargin = 500;

    // replicate max wait timeout, ms
    private long maxAwaitTimeout = 1000;

    // consensus thread pool size
    private int raftConsensusThreadNum = 20;

    // if async write
    private boolean asyncWrite = false;

    // raft的log和snapshot父目录，绝对路径s
    private String dataDir = System.getProperty("com.github.wenweihu86.raft.data.dir");
}
