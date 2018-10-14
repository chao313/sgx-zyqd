package cn.sgx.zyqd.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 2018/8/9    Created by   chao
 */
@Aspect
@Component
public class AnnotationAspect {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static int num;

    @Pointcut(value = "@annotation(DataSourceChange)")
    private void pointcut() {
    }


    @Before("pointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("before exec args:{}", Arrays.toString(args));
        String dataSource  = args[args.length-1].toString();//指定最后一个参数是DataSource
        //dataSource 只能是dataSource195/196/197/198
        //将方法体上的注解的值赋予给DataSourceHolder数据源持有类
        DataSourceHolder.setDataSourceType(dataSource);

    }

}

/**
 * Before
 */
//    @Before("pointcut()")
//    public void beforeAdvice(JoinPoint joinPoint) {
//        logger.info("=======>进入方法之前记录日志");
//        Object targetObject = joinPoint.getTarget();
//        Signature signature = joinPoint.getSignature();
//        String signatureName = signature.getName();
//        Object[] args = joinPoint.getArgs();
//        logger.info("before exec class:{},method:{},args:{}", targetObject, signatureName, args);
////        logger.debug("=======>进入方法之前记录日志切面结束");
//
//    }

//    /**
//     * After 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
//     */
//    @After(value = "doLogger()")
//    public void afterAdvice(JoinPoint joinPoint) {
//        logger.info("=======>退出方法记录日志");
//        Object targetObject = joinPoint.getTarget();
//        Signature signature = joinPoint.getSignature();
//        String signatureName = signature.getName();
//        logger.info("after exec class:{},method:{}", targetObject, signatureName);
////        logger.debug("=======>退出方法记录日志切面结束");
//    }
//
//    /**
//     * Around
//     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
//     * <p/>
//     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice
//     * 执行完AfterAdvice，再转到ThrowingAdvice
//     *
//     * @param pjp
//     * @return
//     * @throws Throwable
//     */
////    @Around(value = "doLogger()")
////    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
////        logger.debug("-----aroundAdvice().invoke-----");
////        Object targetObject = pjp.getTarget();
////        Signature signature = pjp.getSignature();
////        String signatureName = signature.getName();
////        Object[] args = pjp.getArgs();
////        logger.debug("aroundAdvice exec class:{},method:{},args:{}", targetObject, signatureName, args);
////        Object retVal = pjp.proceed();
////        logger.debug("aroundAdvice exec class:{},method:{},args:{},retVal:{}", targetObject, signatureName, args,retVal);
////        logger.debug("-----End of aroundAdvice()------");
////        return retVal;
////    }
//
//    /**
//     * AfterReturning 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
//     */
//    @AfterReturning(value = "doLogger()", returning = "retVal")
//    public void afterReturningAdvice(JoinPoint joinPoint, String retVal) {
//        logger.info("=======>方法正常退出记录日志");
//        Object targetObject = joinPoint.getTarget();
//        Signature signature = joinPoint.getSignature();
//        String signatureName = signature.getName();
//        Object[] args = joinPoint.getArgs();
//        logger.info("afterReturning exec class:{},method:{},args:{},retVal:{}", targetObject, signatureName, args, retVal);
////        logger.debug("=======>方法正常退出记录日志切面结束");
//    }
//
//    /**
//     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
//     * <p/>
//     * 注意：执行顺序在Around Advice之后
//     */
//    @AfterThrowing(value = "doLogger()", throwing = "ex")
//    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
//        logger.debug("=======>方法抛出异常记录日志");
//        Object targetObject = joinPoint.getTarget();
//        Signature signature = joinPoint.getSignature();
//        String signatureName = signature.getName();
//        logger.debug("afterThrowing exec class:{},method:{}", targetObject, signatureName);
//        logger.error("afterThrowing error msg:" + ex.getMessage(), ex);
//
////        logger.debug("=======>方法抛出异常记录日志切面结束");
//    }

