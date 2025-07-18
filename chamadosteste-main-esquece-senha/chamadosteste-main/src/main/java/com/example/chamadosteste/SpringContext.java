package com.example.chamadosteste;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ConfigurableApplicationContext springContext) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setContext'");
    }
}
