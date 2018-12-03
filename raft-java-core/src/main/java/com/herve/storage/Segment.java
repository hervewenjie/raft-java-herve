package com.herve.storage;

import com.herve.proto.RaftMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengwenjie on 2018/11/30.
 */
public class Segment {

    @AllArgsConstructor
    public static class Record {
        public long offset;
        public RaftMessage.LogEntry entry;
    }

    @Getter @Setter private boolean canWrite;
    @Getter @Setter private long startIndex;
    @Getter @Setter private long endIndex;
    @Getter @Setter private long fileSize;
    @Getter @Setter private String fileName;
    @Getter @Setter private RandomAccessFile randomAccessFile;
    @Getter @Setter private List<Record> entries = new ArrayList<>();

    public RaftMessage.LogEntry getEntry(long index) {
        if (startIndex == 0 || endIndex == 0) {
            return null;
        }
        if (index < startIndex || index > endIndex) {
            return null;
        }
        int indexInList = (int) (index - startIndex);
        return entries.get(indexInList).entry;
    }

}
