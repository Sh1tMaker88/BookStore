package servicesTest;

import com.model.Book;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.*;

public class BookServiceTest {

    @Test
    public void assertString() {
        Book book = new Book();
        String st = "Hi";
        String st2 = "Hi";
//        List<String> strings = Arrays.asList("One", "Two");
//        List<String> strings2 = Arrays.asList("One", "Two");
//        assertEquals("lo", "Hello".substring(3));
        assertThat(st, equalTo(st2));
    }


}
