package com.fundamentosplatzi.springboot.fundamentos.configuration;

import com.fundamentosplatzi.springboot.fundamentos.bean.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigurationBean {
    @Bean
    public MyBean beanOperetion(){
        return new MyBean2Implement();
    }

    @Bean
    public MyOperation beanOperetionOperation() {
        return new MyOperationImplement();
    }

    @Bean
    public MyBeanWithDependency beanOperetionOperationWithDepencency(MyOperation myOperation) {
            return new MyBeanWithDependencyImplement(myOperation);
    }
}
