import org.epochx.epox.Node;
import org.epochx.epox.Variable;
import org.epochx.epox.math.*;
import org.epochx.gp.model.GPModel;
import org.epochx.gp.op.init.FullInitialiser;
import org.epochx.gp.representation.GPCandidateProgram;
import org.epochx.op.selection.LinearRankSelector;
import org.epochx.representation.CandidateProgram;
import org.epochx.stats.StatField;
import org.epochx.stats.Stats;
import org.epochx.tools.random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleBinaryOperator;
/**
 *
 */
public class Altruism extends GPModel {
    private Variable cuzRequired = new Variable("cuzRequired", Double.class);
    private Variable food = new Variable("food", Double.class);
    private Variable relation = new Variable("relation", Double.class);
    private Variable bystanders = new Variable("bystanders", Integer.class);
    private Variable isFriend = new Variable("bystanders", Double.class);
    private Variable foodHeHas = new Variable("foodHeHas", Double.class);
    private Variable isThief = new Variable("isThief", Double.class);
    private Variable isEnemy = new Variable("isEnemy", Double.class);

    public Altruism() {
        setProgramSelector(new LinearRankSelector(new MersenneTwisterFast(), 0));
        List<Node> syntax = new ArrayList<>();

        //syntax.add(cuzRequired);
        syntax.add(food);
        //syntax.add(relation);
        syntax.add(bystanders);
        //syntax.add(isFriend);
        syntax.add(foodHeHas);
        //syntax.add(isThief);
        //syntax.add(isEnemy);

        syntax.add(new AddFunction());
        syntax.add(new SubtractFunction());
        syntax.add(new MultiplyFunction());
        syntax.add(new DivisionProtectedFunction());
        syntax.add(new Node() {
            @Override
            public Object evaluate() {
                
                return new Random().nextDouble();
            }

            @Override
            public String getIdentifier() {
                return "RAND";
            }
        });


        setSyntax(syntax);

        setInitialiser(new FullInitialiser(this) {
            @Override
            public GPCandidateProgram getInitialProgram() {
                final Node root = getFullNodeTree();

                return new Creature(root, getModel());
            }
        });
    }

    /**
     * Calculates a fitness score of a program. In EpochX fitness is
     * standardised so a fitness score of 0.1 is better than 0.2. It is
     * essential that this method is implemented to provide a measure of how
     * good the given program solution is.
     *
     * @param p the candidate program to be evaluated.
     * @return a fitness score for the program given as a parameter.
     */
    @Override
    public double getFitness(final CandidateProgram p) {
        return p.getFitness();
    }

    @Override
    public double getTerminationFitness() {


        return -10;
    }

    @Override
    public Class<?> getReturnType() {
        return Double.class;
    }

    public Variable getFood() {
        return food;
    }

    public Variable getRelation() {
        return relation;
    }

    public Variable getBystanders() {
        return bystanders;
    }

    public Variable getCuzRequired() {
        return cuzRequired;
    }

    public Variable getIsFriend() {
        return isFriend;
    }

    public Variable getFoodHeHas() {
        return foodHeHas;
    }

    public Variable getIsThief() {
        return isThief;
    }

    public Variable getIsEnemy() {
        return isEnemy;
    }

    @Override
    public int getNoGenerations() {
        return 70;
    }

    @Override
    public int getPoolSize() {
        return 250;
    }
}
