import javafx.scene.chart.PieChart;
import org.epochx.epox.Node;
import org.epochx.gp.model.GPModel;
import org.epochx.gp.representation.GPCandidateProgram;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Creature extends GPCandidateProgram {
    JComponent component = new JComponent() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

        }
    };

    private double uid;
    private List<Creature> group;
    private double fitness = 550.0;
    private double food = 0;
    private List<Creature> friends = new ArrayList<>();
    private List<Creature> parents = new ArrayList<>();
    private List<Creature> enemies = new ArrayList<>();


    /**
     * Constructs a new program individual where <code>rootNode</code> is the
     * top most node in the program, and which may have 0 or more child nodes.
     *
     * @param rootNode the Node of the program tree which is the parent to
     *                 all other nodes. It may be either a FunctionNode or a
     *                 TerminalNode
     * @param model    the controlling model which provides the configuration
     */
    public Creature(Node rootNode, GPModel model) {
        super(rootNode, model);
        uid = new Random().nextDouble();
    }

    public List<Creature> getGroup() {
        return group;
    }

    public void setGroup(List<Creature> group) {

        if(!group.contains(this)) {
            AltruismHook.pop++;
            group.add(this);
        }
        this.group = group;

    }


    @Override
    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness1)
    {
        this.fitness = fitness1;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }


    public List<Creature> getParents() {
        return parents;

    }

    @Override
    public Creature clone() {
        Creature creature = (Creature) super.clone();
        //creature.setGroup(getGroup());
        creature.uid = new Random().nextDouble();
        creature.setFitness(getFitness());


        return creature;
    }

    public List<Creature> getFriends() {
        return friends;
    }

    public List<Creature> getEnemies() {
        return enemies;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
