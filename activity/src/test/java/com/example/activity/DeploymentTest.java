package com.example.activity;

import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/4/11 15:52
 * @注释
 */
@SpringBootTest
public class DeploymentTest {
    @Autowired
    private RepositoryService repositoryService;

    //流程部署
    @Test
    public void initDeployment() {
        String fileName = "processes/test.bpmn20.xml";
        Deployment deployment = this.repositoryService.createDeployment()
                .addClasspathResource(fileName)
                .name("流程部署测试")
                .deploy();
        System.out.println("流程部署测试 = " + deployment.getName());

    }


    //流程部署
    @Test
    public void initDeploymentByZip(){
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/Part1_Deployment.zip");

        assert inputStream != null;

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deploy = this.repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("流程部署测试（ZIP包）")
                .deploy();
        System.out.println("流程部署名称 = " + deploy.getName());
    }


    //查询流程部署列表
    @Test
    public void listDeployments() {
        List<Deployment> list = this.repositoryService.createDeploymentQuery().list();
        if (!list.isEmpty()) {
            list.forEach(deployment -> {
                System.out.println("id = " + deployment.getId());
                System.out.println("name = " + deployment.getName());
                System.out.println("DeploymentTime = " + deployment.getDeploymentTime());
                System.out.println("Key = " + deployment.getKey());
                }
            );
        }
    }


}
