import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFlow extends PluggableActivitiTestCase {

    @Test
    @Deployment(resources = "process2.bpmn20.xml")
    public void test() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process");

        Task task = taskService.createTaskQuery().singleResult();
        Map<String,Object> vars=new HashMap();
        vars.put("days",8);
        taskService.complete(task.getId(),vars);

        task = taskService.createTaskQuery().singleResult();
        taskService.complete(task.getId(),vars);

        List<Task> tasks= taskService.createTaskQuery().list();
        for(Task t:tasks){
            taskService.complete(t.getId());
        }
        task = taskService.createTaskQuery().singleResult();
        assertProcessEnded(processInstance.getId());
    }
}
