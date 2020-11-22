import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BoopingSiteTest.class, SpaceshipDepositoryTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("-------- FAILURE IN TEST --------");
        }

        System.out.println("-------- ALL TESTS PASSED --------");
    }
}  	