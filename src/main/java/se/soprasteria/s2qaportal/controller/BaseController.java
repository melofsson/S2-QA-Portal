package se.soprasteria.s2qaportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.repository.TestRepository;

@Component
public class BaseController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TestRepository testRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){
        testRepository.deleteAll();
    }

}
