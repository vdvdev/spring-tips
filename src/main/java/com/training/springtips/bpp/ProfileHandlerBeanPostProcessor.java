package com.training.springtips.bpp;

import com.training.springtips.annotations.Profile;
import com.training.springtips.annotations.RandomInt;
import com.training.springtips.jmx.ProfileController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ProfileHandlerBeanPostProcessor implements BeanPostProcessor {

    Map<String, Class> map = new HashMap<>();

    ProfileController profileController = new ProfileController();

    public ProfileHandlerBeanPostProcessor() throws Exception {
        var mBeanServer = ManagementFactory.getPlatformMBeanServer();

        mBeanServer.registerMBean(profileController, new ObjectName("profiling", "name", "controller"));
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        var beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profile.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        var beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (profileController.isEnable()){
                                System.out.println("Profiling...");
                                var before = System.nanoTime();
                                var res = method.invoke(bean, args);
                                var after = System.nanoTime();
                                System.out.println("Profiling Ended : " + (after - before));
                                return res;
                            }else {
                                return method.invoke(bean, args);
                            }
                        }
                    });
        }

        return bean;
    }
}
