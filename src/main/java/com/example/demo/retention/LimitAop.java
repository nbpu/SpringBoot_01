package com.example.demo.retention;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 自定义切面方法，指定调用方法前后来完成一些操作
 *
 */
//@Around注解可以用来在调用一个具体方法前和调用后来完成一些具体的任务
@Aspect
@Component
public class LimitAop {
    private static Logger logger = LoggerFactory.getLogger(LimitAop.class);
    /**
     * 不同的接口，不同的流量控制
     * map的key为 Limiter.key
     */
    private final Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();
    //标注要执行的方法或者注解类
    @Around("@annotation(com.example.demo.retention.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //拿limit的注解
        Limit limit = method.getAnnotation(Limit.class);
        if (limit != null) {
            //key作用：不同的接口，不同的流量控制
            String key=limit.key();
            RateLimiter rateLimiter = null;
            //验证缓存是否有命中key
            if (!limitMap.containsKey(key)) {
                // 创建令牌桶
                rateLimiter = RateLimiter.create(limit.permitsPerSecond());
                limitMap.put(key, rateLimiter);
                logger.info("新建了令牌桶={}，容量={}",key,limit.permitsPerSecond());
            }
            rateLimiter = limitMap.get(key);
            // 拿令牌
            boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            // 拿不到命令，直接返回异常提示
            if (!acquire) {
                logger.debug("令牌桶={}，获取令牌失败",key);
//                this.responseFail(limit.msg());
                return null;
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 直接向前端抛出异常
     * @param msg 提示信息
     */
   /* private void responseFail(String msg)  {
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        ResultData<Object> resultData = ResultData.fail(ReturnCode.LIMIT_ERROR.getCode(), msg);
        WebUtils.writeJson(response,resultData);
    }*/
}

