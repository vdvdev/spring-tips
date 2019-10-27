package com.training.springtips.bpp;

import com.training.springtips.annotations.PostProxy;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        var applicationContext = event.getApplicationContext();
        var names = applicationContext.getBeanDefinitionNames();

        for (var name : names) {
            var beanDefinition = factory.getBeanDefinition(name);
            var originalClassName = beanDefinition.getBeanClassName();

            try {
                if (originalClassName == null){
                    originalClassName =
                            ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata().getReturnTypeName();
                }
                var originalClass = Class.forName(originalClassName);
                var methods = originalClass.getMethods();

                for (var method: methods) {
                    if (method.isAnnotationPresent(PostProxy.class)){
                        var bean = applicationContext.getBean(name);
                        var currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }



        }

    }
}
