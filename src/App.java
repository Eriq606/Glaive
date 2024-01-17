public class App {
    public static void main(String[] args) throws Exception {
        String projectName="HelloWorld";
        String projectPath=".";
        String dataGlaive="data_glaive";
        int projectFramework=Constantes.FLAMEWORK_ID;
        switch(projectFramework){
            case Constantes.FLAMEWORK_ID:
                FlameworkManager manager=new FlameworkManager(projectName);
                manager.generateSkeleton(dataGlaive+"/skeleton-flamework.zip", projectPath);
                manager.setProjectNameInSkeleton(projectName);
                manager.generateConfig(projectName);
                break;
        }
    }
}
