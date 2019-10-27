package com.training.springtips.bpp;

import ch.qos.logback.core.joran.spi.DefaultClass;
import com.training.springtips.annotations.DeprecatedClass;
import com.training.springtips.config.Blabler2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class DeprecationHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) {

        var names = configurableListableBeanFactory.getBeanDefinitionNames();

        for (var name : names) {
            try {
                var beanDefinition = configurableListableBeanFactory.getBeanDefinition(name);
                var className = beanDefinition.getBeanClassName();
                if (className == null){
                    className =
                            ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata().getReturnTypeName();

                    var beanCass = Class.forName(className);

                    var annotation = beanCass.getAnnotation(DeprecatedClass.class);
                    if(annotation != null){
                        beanDefinition.setBeanClassName(annotation.newImpl().getName());
                    }
                }
            } catch (Exception e) {
               e.printStackTrace();
            }
        }


    }
}
