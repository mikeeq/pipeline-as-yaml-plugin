package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Optional;


@Getter
@Setter
public class StageModel extends AbstractModel implements ParsableModelInterface {

    public static String directive = "stage";
    public static String failFastKey = "failFast";
    public static String beforeAgentKey = "beforeAgent";
    public static String beforeOptionsKey = "beforeOptions";
    public static String beforeInputKey = "beforeInput";
    private String name;
    private Optional<StepsModel> stepsModel;
    private Optional<AgentModel> agentModel;
    private Optional<PostModel> postModel;
    private Optional<ToolsModel> toolsModel;
    private Optional<StagesModel> stagesModel;
    private Optional<EnvironmentModel> environmentModel;
    private Optional<ParallelModel> parallelModel;
    private Optional<Boolean> failFast;
    private Optional<InputModel> inputModel;
    private Optional<WhenModel> whenModel;


    public StageModel(String name, Optional<StepsModel> stepsModel, Optional<AgentModel> agentModel, Optional<PostModel> postModel, Optional<ToolsModel> toolsModel, Optional<StagesModel> stagesModel, Optional<EnvironmentModel> environmentModel, Optional<ParallelModel> parallelModel, Optional<Boolean> failFast, Optional<InputModel> inputModel, Optional<WhenModel> whenModel, Optional<Boolean> beforeAgent, Optional<Boolean> beforeOptions, Optional<Boolean> beforeInput) {
        this.name = name;
        this.stepsModel = stepsModel;
        this.agentModel = agentModel;
        this.postModel = postModel;
        this.toolsModel = toolsModel;
        this.stagesModel = stagesModel;
        this.environmentModel = environmentModel;
        this.parallelModel = parallelModel;
        this.failFast = failFast;
        this.inputModel = inputModel;
        this.whenModel = whenModel;
        if (this.whenModel.isPresent()) {
            this.whenModel.get().setBeforeAgent(beforeAgent);
            this.whenModel.get().setBeforeOptions(beforeOptions);
            this.whenModel.get().setBeforeInput(beforeInput);
        }
    }

    @Override
    public String toGroovy() {
        //FIXME There should be order
        StringBuffer groovyString = new StringBuffer();
        groovyString.append(getStageOpen())
                .append(this.name)
                .append(getStageClose())
                .append(getDirectiveOpen())
                .append(agentModel.map(AgentModel::toGroovy).orElse(""))
                .append(environmentModel.map(EnvironmentModel::toGroovy).orElse(""))
                .append(toolsModel.map(ToolsModel::toGroovy).orElse(""))
                .append(inputModel.map(InputModel::toGroovy).orElse(""))
                .append(whenModel.map(WhenModel::toGroovy).orElse(""))
                .append(stagesModel.map(StagesModel::toGroovy).orElse(""))
                .append(optionalBooleanToGroovy(failFast, failFastKey))
                .append(parallelModel.map(ParallelModel::toGroovy).orElse(""))
                .append(stepsModel.map(StepsModel::toGroovy).orElse(""))
                .append(postModel.map(PostModel::toGroovy).orElse(""))
                .append(getDirectiveClose());
        return groovyString.toString();
    }
}
