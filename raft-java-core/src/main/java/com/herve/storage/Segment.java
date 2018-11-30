package com.herve.storage;

import com.herve.proto.RaftMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengwenjie on 2018/11/30.
 */
@Data
public class Segment {

    @AllArgsConstructor
    public static class Record {
        public long offset;
        public RaftMessage.LogEntry entry;
    }

    private boolean canWrite;
    private long startIndex;
    private long endIndex;
    private long fileSize;
    private String fileName;
    private RandomAccessFile randomAccessFile;
    private List<Record> entries = new ArrayList<>();

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
