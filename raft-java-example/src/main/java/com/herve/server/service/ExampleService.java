package com.herve.server.service;

/**
 * Created by chengwenjie on 2018/12/3.
 */
public interface ExampleService {

    ExampleMessage.SetResponse set(ExampleMessage.SetRequest request);

    ExampleMessage.GetResponse get(ExampleMessage.GetRequest request);
}
