package cn.aezo.springboot.datasource.config.dynamic.aop;

import cn.aezo.springboot.datasource.config.dynamic.ThreadLocalDSKey;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DynamicDataSourceAspect {

    // AbstractRoutingDataSource 只支持单库事务，也就是说切换数据源要在开启事务之前执行
    @Before("@annotation(DS)")
    public void beforeSwitchDS(JoinPoint point){
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dsKey = ThreadLocalDSKey.DEFAULT_DS_KEY;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dsKey = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        ThreadLocalDSKey.setDS(dsKey);
    }

    @After("@annotation(DS)")
    public void afterSwitchDS(JoinPoint point) {
        // ThreadLocalDSKey.clearDS();
    }
}
