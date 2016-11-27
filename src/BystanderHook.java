import java.util.List;

/**
 *
 */
public class BystanderHook extends AltruismHook {
    public BystanderHook(ZMath math, List<List<Creature>> groups) {
        super(math, groups);
    }

    @Override
    public List<Creature> getGroup(List<Creature> parents, Creature child) {
        for(Creature parent : parents) {
            if(parent.getGroup().size() < math.maxGroupSize) {
                return parent.getGroup();
            }
        }

        for(List<Creature> group : groups) {
            if(group.size() < math.maxGroupSize) {
                return group;
            }
        }

        throw new RuntimeException("All groups exceeded max group size");
    }
}

