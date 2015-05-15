package nw15s305.plantplaces.com.plantplaces15s305;

import android.app.Application;
import android.test.ApplicationTestCase;



/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testFoo() {
        assertEquals(1, 1);
    }

    public void testBar() {
        assertEquals(1, 5);
    }
}