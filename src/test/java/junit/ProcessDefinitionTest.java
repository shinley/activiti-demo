package junit;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class ProcessDefinitionTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     * 部署流程定义(从classpath)
     */
    @Test
    public void deploymentProcessDefine() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("helloworld入门程序")
                .addClasspathResource("diagrams/Helloworld.bpmn") // 从classpath的资源中加载，一次只能加载一个
                .addClasspathResource("diagrams/Helloworld.png")
                .deploy();

        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    /**
     * 部署流程定义(从zip)
     */
    @Test
    public void deploymentProcessDefinition_zip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("流程定义")
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称" + deployment.getName());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
//                .deploymentId()                                                 // 指定查询条件
//                .processDefinitionId()
//                .processDefinitionKey()
//                .orderByProcessDefinitionVersion().asc() // 按照版本升序排列
//                .count()                                                        // 返回结果集数据
//                .singleResult()                                                 // 返回唯一结果集
                .list();                                                         // 返回一个集合列表， 封装流程定义
//                .listPage(firstResult, maxResults);                             // 分页查询

        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义ID:" + pd.getId());            // 流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称：" + pd.getName());      // 对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());        // 对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本" + pd.getVersion());
                System.out.println("资源名称bpmn文件：" + pd.getResourceName());
                System.out.println("资源名称png文件：" + pd.getDeploymentId());
                System.out.println("部署对象ID:" + pd.getDeploymentId());
                System.out.println("###############################################3");
            }

        }
    }


    /***
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefinition() {
        String deploymentId = "1";
        /**
         * 不带级联的删除，
         *  只能删除没有启动的流程，如果流程启动， 就会抛出异常
         */
        processEngine.getRepositoryService()
                .deleteDeployment(deploymentId);

        /**
         * 级联删除，
         *  不管流程是否启动，都可以删除
         */
        processEngine.getRepositoryService()
                .deleteDeployment(deploymentId, true);
        System.out.println("删除成功");
    }

    /***
     * 查看流程图
     */
    @Test
    public void viewPic() throws IOException {
        /***
         * 将生的图片语文到文件夹下
         */
        String deploymentId = "801";

        // 获取图片资源名称
        List<String> list = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        // 定义图片资源的名称
        String resourceName = "";
        if (list != null && list.size() > 0) {
            for (String name : list) {
                if (name.indexOf(".png") > 0) {
                    resourceName = name;
                }
            }
        }

        InputStream in = processEngine.getRepositoryService()
                .getResourceAsStream(deploymentId, resourceName);

        // 将图片保存存到某个目录下
        File file = new File("D:/" + resourceName);
        FileUtils.copyInputStreamToFile(in, file);
    }
}
