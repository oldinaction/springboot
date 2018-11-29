package cn.aezo.springboot.datasource;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration //开启web上下文测试
public class DynamicAddTests {
    // 注入webApplicationContext
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    // 设置mockMvc
    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void multiRequestsTest() {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                // 测试内容
                if(Thread.currentThread().getId() % 2 == 0) {
                    test("mysql-one-dynamic", "dsKey = mysql-one-dynamic, data = [{password=123456, id=1, username=smalle}]");
                } else {
                    test("mysql-two-dynamic", "dsKey = mysql-two-dynamic, data = [{password=ABC123, id=1, username=test_two}]");
                }
            }
        };

        int runnerCount = 100;
        // Runner数组，想当于并发多少个。
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

    private void test(String dsKey, String expectContent) {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test3?dsKey=" + dsKey))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            Assert.assertEquals(expectContent, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
