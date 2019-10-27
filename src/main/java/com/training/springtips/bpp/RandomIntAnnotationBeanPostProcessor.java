package com.training.springtips.bpp;

import com.training.springtips.annotations.RandomInt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.Random;

@Service
public class RandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        var fields = bean.getClass().getDeclaredFields();

        for (var field : fields) {
            var annotation = field.getAnnotation(RandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                int i = min + new Random().nextInt(max - min + 1);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, i);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
