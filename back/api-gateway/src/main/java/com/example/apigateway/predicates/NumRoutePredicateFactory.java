package com.example.apigateway.predicates;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class NumRoutePredicateFactory extends AbstractRoutePredicateFactory<NumRoutePredicateFactory.Config> {

    public NumRoutePredicateFactory() {
        super(NumRoutePredicateFactory.Config.class);
    }

    //用于从配置文件中获取参数值赋值到配置类中的属性上
    @Override
    public List<String> shortcutFieldOrder() {
        //这里的顺序要跟配置文件中的参数顺序一致
        return Arrays.asList("minNum", "maxNum");
    }

    //断言
    @Override
    public Predicate<ServerWebExchange> apply(NumRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //从serverWebExchange获取传入的参数
                String numStr = serverWebExchange.getRequest().getQueryParams().getFirst("num");
                System.out.println("传入的数字为："+numStr);
                if (StringUtils.isNotEmpty(numStr)) {
                    int num = Integer.parseInt(numStr);
                    return num > config.getMinNum() && num < config.getMaxNum();
                }
                return true;
            }
        };
    }

    //自定义一个配置类, 用于接收配置文件中的参数
    @Data
    @NoArgsConstructor
    public static class Config {
        private int minNum;
        private int maxNum;
    }
}

