package chapter14;

import java.awt.font.NumericShaper;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simjunbo on 2018-04-23.
 */
public class Caching {
    final Map<NumericShaper.Range, Integer> numberOfNodes = new HashMap<>();

    Integer computeNumberOfNodesUsingCache(NumericShaper.Range range) {
        Integer result = numberOfNodes.get(range);
        if (result != null) {
            return result;
        }
        result = computeNumberOfNodesUsingCache(range);
        numberOfNodes.put(range, result);
        return result;
    }
}
