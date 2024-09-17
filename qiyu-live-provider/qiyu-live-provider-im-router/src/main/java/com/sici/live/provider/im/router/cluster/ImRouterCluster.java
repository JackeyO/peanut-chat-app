package com.sici.live.provider.im.router.cluster;

import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.support.wrapper.AbstractCluster;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.router.cluster
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 5:05 PM
 * @version: 1.0
 */

/**
 * 基于Cluster去做SPI扩展,实现根据RPC上下文来选择具体请求的机器
 */
public class ImRouterCluster implements Cluster {
    @Override
    public <T> Invoker<T> join(Directory<T> directory, boolean buildFilterChain) throws RpcException {
        return new ImRouterClusterInvoker<>(directory);
    }
}

