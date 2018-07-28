import com.google.common.testing.EqualsTester;
import com.google.common.testing.EquivalenceTester;
import org.junit.ClassRule;
import org.junit.Test;

public class TestFoo {
//  @ClassRule
//  public static TestResources res = TestResources.getTestResources("Hello world");

  @Test
  public void testFoo() {
    new EqualsTester()
            .addEqualityGroup("hello", "he" + "llo")
            .addEqualityGroup("world", "w" + "orld")
            .addEqualityGroup(18, 9 + 9)
            .testEquals();

//    System.out.println("testFoo");
  }
}
