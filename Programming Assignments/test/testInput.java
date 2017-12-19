import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testInput {
  List one = new ArrayList(Arrays.asList(4, 4, 4, 1, 4, 1, 3, 1, 2, 3, 4, 4, 4, 2, 1));
  main(one);

  public void test {
    assertEquals(new Dijkstra(one));
  }

}
