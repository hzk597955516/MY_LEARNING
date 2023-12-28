import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    List<Bear> sortedbears = new ArrayList<>();
    List<Bed> sortedbeds = new ArrayList<>();

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // TODO: Fix me.
        Pair<List<Bear>, List<Bed>> zip = quicksort(bears, beds);
        sortedbears = zip.first();
        sortedbeds = zip.second();
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return sortedbears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return sortedbeds;
    }

    public void partitionBears(List<Bear> bears, Bed pivot, List<Bear> less, List<Bear> equare, List<Bear> greater){
        for (Bear bear: bears){
            int cmp = bear.compareTo(pivot);
            if (cmp < 0){
                less.add(bear);
            }
            else if (cmp > 0){
                greater.add(bear);
            }
            else {
                equare.add(bear);
            }
        }
    }

    public void partitionBed(List<Bed> beds, Bear pivot, List<Bed> less, List<Bed> equare, List<Bed> greater){
        for (Bed bed: beds){
            int cmp = bed.compareTo(pivot);
            if (cmp < 0){
                less.add(bed);
            }
            else if (cmp > 0){
                greater.add(bed);
            }
            else {
                equare.add(bed);
            }
        }
    }

    public List<Bear> maketoaBears(List<Bear> bear1, List<Bear> bear2){
        List<Bear> newbears = new ArrayList<>();

        for (Bear bear: bear1){
            newbears.add(bear);
        }

        for (Bear bear: bear2){
            newbears.add(bear);
        }

        return newbears;
    }

    public List<Bed> maketoaBeds(List<Bed> bed1, List<Bed> bed2){
        List<Bed> newbeds = new ArrayList<>();

        for (Bed bed: bed1){
            newbeds.add(bed);
        }

        for (Bed bed: bed2){
            newbeds.add(bed);
        }

        return newbeds;
    }

    public Pair<List<Bear>, List<Bed>> quicksort(List<Bear> bears, List<Bed> beds){
        if (bears.size() <= 1 && beds.size() <= 1){
            return new Pair<>(bears, beds);
        }
        else {
            Bed bedpivot = beds.get(0);

            List<Bear> less_bear = new ArrayList<>();
            List<Bear> equare_bear = new ArrayList<>();
            List<Bear> greater_bear = new ArrayList<>();

            partitionBears(bears, bedpivot, less_bear, equare_bear, greater_bear);

            Bear bearpivot = equare_bear.get(0);

            List<Bed> less_bed = new ArrayList<>();
            List<Bed> equare_bed = new ArrayList<>();
            List<Bed> greater_bed = new ArrayList<>();

            partitionBed(beds, bearpivot, less_bed, equare_bed, greater_bed);

            Pair<List<Bear>, List<Bed>> less = quicksort(less_bear, less_bed);
            Pair<List<Bear>, List<Bed>> greater = quicksort(greater_bear, greater_bed);

            List<Bear> newbears = maketoaBears(maketoaBears(less.first(), equare_bear), greater.first());
            List<Bed> newbeds = maketoaBeds(maketoaBeds(less.second(), equare_bed), greater.second());

            return new Pair<>(newbears, newbeds);
        }
    }
}
