package com.zb.byb.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class BeanLocator implements ApplicationContextAware
{
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
    }

    /**
    * 获取自定类型的Spring对象
    * @param c
    * @return
    */
    public static <T> T getT(Class<T> c)
    {
        return context.getBean(c);
    }

    /**
     * 获取自定类型的Spring对象
     * @param id
     * @param c
     * @return
     */
    public static <T> T getT(String id, Class<T> c)
    {
        return context.getBean(id, c);
    }

}
