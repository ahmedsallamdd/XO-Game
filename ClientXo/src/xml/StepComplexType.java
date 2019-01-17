package xml;

import java.io.Serializable;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "step")
@XmlType(propOrder = {"player", "postion"})
public class StepComplexType implements Serializable {

    private int player;
    private int postion;

    @XmlElement(name = "player")
    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @XmlElement(name = "position")
    public int getPostion() {
        return postion;
    }

    public StepComplexType(int player, int postion) {
        this.player = player;
        this.postion = postion;
    }

    public StepComplexType() {

    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

}
