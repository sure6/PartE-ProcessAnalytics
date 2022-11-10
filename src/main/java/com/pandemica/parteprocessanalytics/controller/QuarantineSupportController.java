package com.pandemica.parteprocessanalytics.controller;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("quarantine")
public class QuarantineSupportController {

//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private TaskService taskService;
//    @Autowired
//    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;



    @ResponseBody
    @RequestMapping("definition")
    public String processDefinition() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().
                addClasspathResource("processes/QuarantineSupportForUser.bpmn20.xml").
                name("QuarantineSupport").
                deploy();
        return "deployment successful";
    }

    @ResponseBody
    @RequestMapping("/processInstance")
    public String processInstance(){
        RuntimeService runtimeService1 = processEngine.getRuntimeService();
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser","user01");
        map.put("gender", "male");
        map.put("age", "30");
        map.put("description", "buy something");
        map.put("quarantine test", "negative");

        ProcessInstance processInstance = runtimeService1.startProcessInstanceByKey("user_process", map);
        return "successfull";
    }


    @ResponseBody
    @RequestMapping("/processInstance2")
    public String processInstance2(){
        RuntimeService runtimeService1 = processEngine.getRuntimeService();
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser","user02");
        map.put("gender", "female");
        map.put("age", "25");
        map.put("description", "write something");
        map.put("quarantine test", "positive");

        ProcessInstance processInstance = runtimeService1.startProcessInstanceByKey("user_process", map);
        return "successfull";
    }
}
