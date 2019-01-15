/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Nesma
 *
 */
@XmlRootElement(name = "Game")

@XmlType(propOrder = {"step", "result"})

public class GameComplexType implements Serializable {

    /**
     * @return the step
     */
    private ArrayList<StepComplexType> step; //<StepComplexType>
    private String Result;

    public GameComplexType(ArrayList<StepComplexType> step, String result) {

        this.step = step;
        this.Result = result;
    }

    public GameComplexType() {
    }

    @XmlElement(name = "step")
    public ArrayList<StepComplexType> getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(ArrayList<StepComplexType> step) {
        this.step = step;
    }

    /**
     * @return the Result
     */
    @XmlElement(name = "result")
    public String getResult() {
        return Result;
    }

    /**
     * @param Result the Result to set
     */
    public void setResult(String Result) {
        this.Result = Result;
    }

    public void addStep(StepComplexType step) {
        this.step.add(step);

    }

}
