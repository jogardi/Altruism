import org.epochx.stats.StatField;
import org.epochx.stats.Stats;

import java.util.List;

/**
 *
 */
public class Experiment {
    List<Creature> pop;
    Altruism altruism;

    public  Experiment(Altruism altruism) {
        this.altruism = altruism;
        pop = (List<Creature>) Stats.get().getStat(StatField.GEN_POP);


        System.out.println();

        System.out.println("control");
        reset();
        runTest();

        System.out.println("brother");
        altruism.getRelation().setValue(1.0);
        runTest();

        System.out.println("friend");
        reset();
        altruism.getIsFriend().setValue(1.0);
        runTest();

        System.out.println("\n WHO'S HUNGRY??");
        System.out.println("hungry");
        reset();
        altruism.getFood().setValue(2.0);
        runTest();

        System.out.println("plentiful");
        altruism.getFood().setValue(20.0);
        runTest();


        System.out.println("it's all good");
        altruism.getFood().setValue(20.0);
        altruism.getIsFriend().setValue(1.0);
        altruism.getRelation().setValue(1.0);
        altruism.getBystanders().setValue(3);
        runTest();

        System.out.println("with few bystanders");
        reset();
        altruism.getRelation().setValue(1.0);
        altruism.getBystanders().setValue(3);
        runTest();

        System.out.println("with many bystanders");
        altruism.getBystanders().setValue(14);
        runTest();

        System.out.println("if thief");
        reset();
        altruism.getIsThief().setValue(10.0);
        altruism.getIsEnemy().setValue(10.0);
        runTest();


    }

    public void reset() {
        altruism.getFood().setValue(8.0);
        altruism.getCuzRequired().setValue(1.0);
        altruism.getBystanders().setValue(12);
        altruism.getIsFriend().setValue(10.0);
        altruism.getRelation().setValue(10.0);
        altruism.getIsThief().setValue(1.0);
        altruism.getIsEnemy().setValue(1.0);
        altruism.getFoodHeHas().setValue(-.1);

    }


    public void runTest() {
        double givers = 0;
        double thiefs = 0;
        for (Creature creature : pop) {
            creature.setFood((Double) altruism.getFood().getValue());
            double foodGiven = (double) creature.evaluate();
            if(foodGiven > 0 && foodGiven < creature.getFood()) {
                givers++;
            }
            altruism.getCuzRequired().setValue(-1.0);
            altruism.getFoodHeHas().setValue(19.0);
            double foodTaken = (double) creature.evaluate();
            if(foodTaken > 1 && foodTaken < 19.0) {
                thiefs++;
            }

        }

        System.out.println("rate: " + givers/pop.size());
        System.out.println("thievery rate: " + thiefs/pop.size());
    }
}
