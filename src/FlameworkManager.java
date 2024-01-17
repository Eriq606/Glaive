import java.io.File;
import java.io.IOException;

import handyman.Utils;

public class FlameworkManager {
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public FlameworkManager(String projectName) {
        this.projectName = projectName;
    }
    public void generateSkeleton(String skeletonSource, String targetPath) throws IOException{
        File projectDir=new File(getProjectName());
        projectDir.mkdir();
        Utils.extractDirFromInsideJar(FlameworkManager.class, skeletonSource, projectDir.getPath()+targetPath);
    }
    public void setProjectNameInSkeleton(String skeletonPath) throws IOException{
        String webxmlPath=skeletonPath+"/web/WEB-INF/web.xml";
        String indexjspPath=skeletonPath+"/web/index.jsp";
        String layoutjspPath=skeletonPath+"/web/pages/layout/layout.jsp";
        String helloControllerPath=skeletonPath+"/src/controller/HelloController.java";
        String replacementWebXml=Utils.getFileContent(webxmlPath).replace("[project-name]", getProjectName());
        String replacementIndexJsp=Utils.getFileContent(indexjspPath).replace("[project-name]", getProjectName());
        String replacementLayoutJsp=Utils.getFileContent(layoutjspPath).replace("[project-name]", getProjectName());
        String replacementController=Utils.getFileContent(helloControllerPath).replace("[project-name]", getProjectName());
        Utils.overwriteFileContent(webxmlPath, replacementWebXml);
        Utils.overwriteFileContent(indexjspPath, replacementIndexJsp);
        Utils.overwriteFileContent(layoutjspPath, replacementLayoutJsp);
        Utils.overwriteFileContent(helloControllerPath, replacementController);
    }
    public void generateConfig(String skeletonPath) throws IOException{
        Config config=new Config();
        config.setProject(getProjectName());
        config.setDefaultPackage(Utils.minAll(getProjectName()));
        String packagedir=config.getDefaultPackage().isEmpty()?"":"/"+config.getDefaultPackage().replace(".", "/");
        config.setClassSavepath("./src"+packagedir);
        config.setControllerSavepath("./src"+packagedir);
        config.setViewpageSavepath("./web/pages");
        String configContent=Utils.toJson(config);
        String configPath=skeletonPath+"/tools/config.json";
        Utils.createFile(configPath);
        Utils.overwriteFileContent(configPath, configContent);
    }
    public void installGenesis(String datapath, String skeletonPath) throws IOException{
        Utils.copyFileFromInsideJar(FlameworkManager.class, datapath+"/Genesis.jar", skeletonPath+"/tools/Genesis.jar");
    }
}
