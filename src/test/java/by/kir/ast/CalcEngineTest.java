package by.kir.ast;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

@RunWith(DataProviderRunner.class)
public class CalcEngineTest {

    @DataProvider
    public static Object[][] data() {
        return new Object[][] {
                { "2 + 2",4 },
                { "3 * 3",9 },
                { "4 / 2",2 },
                { "5 - 1",4 },
                { "2/2 + (1 + 2*(5+2)) + 3*3", 25},
        };
    }

    @Test
    @UseDataProvider("data")
    public void eval(String in, Integer out) {
        Assert.assertEquals(out, new CalcEngine().eval(in));
    }

}
