import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by kiran.biliyawala on 15/03/20.
 */
public class JsonValidatorTest {

    JsonValidator validator = new JsonValidator();

    @Test
    public void validJson(){
        String jsonString = "{\"id\": \"0001\", \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
        Assert.assertTrue(validator.isValidJson(jsonString));
    }

    @Test
    public void commaMissingInTupple(){
        String jsonString = "{\"id\": \"0001\" \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
        Assert.assertFalse(validator.isValidJson(jsonString));
    }

    @Test
    public void commaMissingInArray(){
        String jsonString = "{\"id\": \"0001\", \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" }{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
        Assert.assertFalse(validator.isValidJson(jsonString));
    }

    @Test
    public void curlyOpeningBraceMissing(){
        String jsonString = "{\"id\": \"0001\", \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
        Assert.assertFalse(validator.isValidJson(jsonString));
    }

    @Test
    public void squareClosingBraceMissing(){
        String jsonString = "{\"id\": \"0001\", \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }}";
        Assert.assertFalse(validator.isValidJson(jsonString));
    }

    @Test
    public void doubleQuoteMissing(){
        String jsonString = "{id\": \"0001\", \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
        Assert.assertFalse(validator.isValidJson(jsonString));
    }

    @Test
    public void colonMissing(){
        String jsonString = "{\"id\" \"0001\", \"type\": \"donut\", \"name\": \"Cake\", \"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
        Assert.assertFalse(validator.isValidJson(jsonString));
    }

}
