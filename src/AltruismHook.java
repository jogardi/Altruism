import org.epochx.life.Hook;
import org.epochx.representation.CandidateProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class AltruismHook implements Hook {
    public static double pop = 0;
    ZMath math;
    List<List<Creature>> groups;
    public AltruismHook(ZMath math, List<List<Creature>> groups) {
        this.math = math;
        this.groups = groups;
    }


    @Override
    public List<CandidateProgram> initialisationHook(List<CandidateProgram> pop) {

        List<Creature> group = new ArrayList<>();
        groups.add(group);
        for(CandidateProgram program : pop) {
            if(group.size() >= math.groupSize) {
                group = new ArrayList<>();
                groups.add(group);
            }

            ((Creature) program).setGroup(group);
        }

        return pop;
    }

    @Override
    public CandidateProgram mutationHook(CandidateProgram parent, CandidateProgram child) {

        Creature parentCreature = (Creature) parent;
        Creature childCreature = (Creature) child;

        childCreature.getParents().clear();
        childCreature.getParents().add(parentCreature);
        List<Creature> group = getGroup(Arrays.asList(parentCreature), childCreature);

        childCreature.setGroup(group);

        return childCreature;
    }

    public List<Creature> getGroup(List<Creature> parents, Creature child) {
        groups.sort((List g1, List g2) -> g1.size() - g2.size());
        return groups.get(0);
    }

    @Override
    public CandidateProgram[] crossoverHook(CandidateProgram[] parents, CandidateProgram[] children) {

        //iterate through children and parents to assign each child to a group
        Creature mommy = (Creature) parents[0];
        Creature daddy = (Creature) parents[1];


        for(CandidateProgram child : children) {
            Creature childCreature = (Creature) child;
            childCreature.getParents().clear();
            Collections.addAll(childCreature.getParents(), mommy, daddy);

            List<Creature> group = getGroup(Arrays.asList(mommy, daddy), ((Creature) child));

            childCreature.setGroup(group);
        }

        return children;
    }





    @Override
    public CandidateProgram reproductionHook(CandidateProgram parent) {
        System.out.println("REPRODUCE");
        return parent.clone();
    }
    @Override
    public List<CandidateProgram> elitismHook(List<CandidateProgram> elites) {
        return elites;
    }
    @Override
    public List<CandidateProgram> poolSelectionHook(List<CandidateProgram> pool) {
        return pool;
    }
    @Override
    public List<CandidateProgram> generationHook(List<CandidateProgram> pop) {

        AltruismHook.pop =0;
        return pop;
    }
}

