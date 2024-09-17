package com.sici.live.provider.im.router.cluster;

import com.sici.common.constant.im.router.ImRouterConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.router.cluster
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 5:06 PM
 * @version: 1.0
 */

@Slf4j
public class ImRouterClusterInvoker<T> extends AbstractClusterInvoker<T> {
    public ImRouterClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, List list, LoadBalance loadbalance) throws RpcException {
        checkWhetherDestroyed();

        String ip = (String) RpcContext.getContext().get(ImRouterConstant.IP_ATTRIBUTE_NAME);
        if (StringUtils.isEmpty(ip)) {
            log.error("[ImRouterClusterInvoker]==>ip is null");
            throw new RuntimeException("[ImRouterClusterInvoker]==>ip is null");
        }

        // 获取指定的rpc服务所有提供者的暴露地址信息
        List<Invoker<T>> invokers = list(invocation);

        Invoker<T> matchedInvoker = invokers.stream()
                .filter(invoker -> {
                    String host = invoker.getUrl().getHost();
                    int port = invoker.getUrl().getPort();

                    return ip.equals(host + ":" + port);
                }).findFirst().orElse(null);


        if (matchedInvoker == null) {
            log.error("[ImRouterClusterInvoker]==>ip is invalid, no service provider found");
            throw new RuntimeException("[ImRouterClusterInvoker]==>ip is invalid, no service provider found");
        }
        return matchedInvoker.invoke(invocation);
    }
}
