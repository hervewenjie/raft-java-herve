package com.herve;

/**
 * raft state machine
 * Created by chengwenjie on 2018/11/30.
 */
public interface StateMachine {

    /**
     * snapshot data in state machine, each node invokes it periodically
     * @param snapshotDir
     */
    void writeSnapshot(String snapshotDir);

    /**
     * read snapshot to state machine, invoked when node starts
     * @param snapshotDir
     */
    void readSnapshot(String snapshotDir);

    /**
     * apply data to state machine
     * @param dataBytes
     */
    void apply(byte[] dataBytes);
}
