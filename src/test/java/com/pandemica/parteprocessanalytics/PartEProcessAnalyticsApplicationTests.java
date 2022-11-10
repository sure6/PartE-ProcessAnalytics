package com.pandemica.parteprocessanalytics;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class PartEProcessAnalyticsApplicationTests {

    @Autowired
    private ProcessEngine processEngine;

//    define process and deploy
    @Test
    void processDefinition(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().
                addClasspathResource("processes/QuarantineSupportForUser.bpmn20.xml").
                name("QuarantineSupport").
                deploy();
        System.out.println("deployment successful");
    }

    // create process instance
    @Test
    void createProcessInstance(){
        RuntimeService runtimeService1 = processEngine.getRuntimeService();
        for (int i = 0; i < 100; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("taskUser","user"+i);
            map.put("description", "buy something"+i);
            map.put("quarantine test", i%2==0?"negative":"positive");
            ProcessInstance processInstance = runtimeService1.startProcessInstanceByKey("user_process", map);
        }

        System.out.println("successfull");
    }

// Assignee execute tasks
    @Test
    void executeProcess(){
        String assignee="User";
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee(assignee).list();
        if(!list.isEmpty()){
            for (Task task:list){
                System.out.println("case id: "+task.getId());
                System.out.println("Assignee: "+task.getAssignee());
                System.out.println("task name: "+task.getName());
                System.out.println("create time: "+task.getCreateTime());
                System.out.println("due time: "+task.getDueDate());
            }
        }
    }
// Assignee handle task.
    @Test
    void processTask(){
        processEngine.getTaskService().complete("5009");
        System.out.println("task completed");
    }



}
