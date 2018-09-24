import cn.aezo.springboot.webservice.cxf.client.MyClient;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Test;

public class MyTest {
    @Test
    public void main() {
        for (int i = 0; i < 1000; i++) {
            multiRequestsTest();
        }
    }

    // 基于groboutils实现多线程并发测试
    public void multiRequestsTest() {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                // 测试内容
                new MyClient().invoke4();
            }
        };

        int runnerCount = 100;
        // Runner数组，相当于并发多少个
        TestRunnable[] arrTestRunner = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            arrTestRunner[i] = runner;
        }

        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(arrTestRunner);
        try {
            // 开发并发执行数组里定义的内容
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
