package com.herve.server.service.impl;

import com.github.wenweihu86.rpc.client.RPCClient;
import com.github.wenweihu86.rpc.client.RPCProxy;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.herve.RaftNode;
import com.herve.proto.RaftMessage;
import com.herve.server.ExampleStateMachine;
import com.herve.server.service.ExampleMessage;
import com.herve.server.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chengwenjie on 2018/12/3.
 */
public class ExampleServiceImpl implements ExampleService {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleServiceImpl.class);
    private static JsonFormat.Printer printer = JsonFormat.printer().omittingInsignificantWhitespace();

    private RaftNode raftNode;
    private ExampleStateMachine stateMachine;

    public ExampleServiceImpl(RaftNode raftNode, ExampleStateMachine stateMachine) {
        this.raftNode = raftNode;
        this.stateMachine = stateMachine;
    }

    @Override
    public ExampleMessage.SetResponse set(ExampleMessage.SetRequest request) {
        ExampleMessage.SetResponse.Builder responseBuilder = ExampleMessage.SetResponse.newBuilder();

        if (raftNode.getLeaderId() <= 0) {
            responseBuilder.setSuccess(false);
        }
        // if i am not leader, relay request to leader
        else if (raftNode.getLeaderId() != raftNode.getLocalServer().getServerId()) {
            RPCClient rpcClient = raftNode.getPeerMap().get(raftNode.getLeaderId()).getRpcClient();
            ExampleService exampleService = RPCProxy.getProxy(rpcClient, ExampleService.class);
            ExampleMessage.SetResponse responseFromLeader = exampleService.set(request);
            responseBuilder.mergeFrom(responseFromLeader);
        }
        // if i am leader
        else {
            // write to raft cluster
            byte[] data = request.toByteArray();
            boolean success = raftNode.replicate(data, RaftMessage.EntryType.ENTRY_TYPE_DATA);
            responseBuilder.setSuccess(success);
        }

        ExampleMessage.SetResponse response = responseBuilder.build();
        try {
            LOG.info("set request, request={}, response={}", printer.print(request),
                    printer.print(response));
        } catch (InvalidProtocolBufferException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public ExampleMessage.GetResponse get(ExampleMessage.GetRequest request) {
        ExampleMessage.GetResponse response = stateMachine.get(request);
        try {
            LOG.info("get request, request={}, response={}", printer.print(request),
                    printer.print(response));
        } catch (InvalidProtocolBufferException ex) {
            ex.printStackTrace();
        }
        return response;
    }

}
