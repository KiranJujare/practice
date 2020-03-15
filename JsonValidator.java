package stack.usecases;

import com.google.common.collect.Lists;
import java.math.BigInteger;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class JsonValidator {

  public static void main(String[] args) {

    /***

     {
     "boolean": false,
     "integer": 1,
     "integerList": [1, 2, 3],
     "object": [
     {
     "inner_integer": 1,
     "inner_integerList": [1,2,3],
     "inner_boolean": false,
     "inner_string": "stringValue",
     "inner_stringList": ["a","b","c"],
     "inner_special@": "valid!@#$%^&*("
     },
     {
     "inner1_integer": 1,
     "inner1_integerList": [1,2,3],
     "inner1_boolean": false,
     "inner1_string": "stringValue",
     "inner1_stringList": ["a","b","c"],
     "inner1_special@": "valid!@#$%^&*("
     }
     ],
     "special@": "valid!@#$%^&*(",
     "string": "stringValue",
     "stringList": ["a","b","c"]
     }

     */
    String validateJson = "{\n"
        + "  \"boolean\": false,\n"
        + "  \"integer\": 1,\n"
        + "  \"integerList\": [1, 2, 3],\n"
        + "  \"object\": [\n"
        + "    {\n"
        + "      \"inner_integer\": 1,\n"
        + "      \"inner_integerList\": [1,2,3],\n"
        + "      \"inner_boolean\": false,\n"
        + "      \"inner_string\": \"stringValue\",\n"
        + "      \"inner_stringList\": [\"a\",\"b\",\"c\"],\n"
        + "      \"inner_special@\": \"valid!@#$%^&*(\"\n"
        + "    },\n"
        + "    {\n"
        + "      \"inner1_integer\": 1,\n"
        + "      \"inner1_integerList\": [1,2,3],\n"
        + "      \"inner1_boolean\": false,\n"
        + "      \"inner1_string\": \"stringValue\",\n"
        + "      \"inner1_stringList\": [\"a\",\"b\",\"c\"],\n"
        + "      \"inner1_special@\": \"valid!@#$%^&*(\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"special@\": \"valid!@#$%^&*(\",\n"
        + "  \"string\": \"stringValue\",\n"
        + "  \"stringList\": [\"a\",\"b\",\"c\"]\n"
        + "}";
//    System.out.println(isValidJson(validateJson));
  }

 

  private static boolean isValidJson(String validateJson) {
    List<Character> jsonList = getList(validateJson);

    Stack<Character> stack = new Stack<Character>();



    for (int i = 0; i < jsonList.size(); i++) {
      char charAt = jsonList.get(i);
//      System.out.println("Char: "+ charAt + " stack: "+  stack);


      if (charAt == '{' || charAt == '[') {
        stack.push(charAt);
        continue;
      }

      if (charAt == '"') {
        if (stack.peek() == '"'){
          if  (stack.peek() == ',' && ((i+1) > jsonList.size() && jsonList.get(i+1) == '"')) {
            continue;
          }

          stack.pop();
        }else{
          stack.push(charAt);
        }
        continue;
      }

      if (charAt == ':'){
        // String
        if  (stack.peek() == '"' && ((i+1) > jsonList.size() && jsonList.get(i+1) == '"')) {
          continue;
        }
        //Integers
        if  (stack.peek() == '"') {
          continue;
        }
      }

      if ((charAt == '}' && stack.peek() == '{') || (charAt == ']' && stack.peek() == '[')) {
        stack.pop();
      }

    }

    System.out.println(stack);
    return stack.isEmpty();
  }

  private static List<Character> getList(String validateJson) {
    List<Character> jsonList = Lists.newLinkedList();
    for (int i = 0; i < validateJson.length(); i++) {
      char charAt = validateJson.charAt(i);
      if (charAt == ' ' || charAt == '\n' || charAt == '\r' || charAt == '\t') {
        continue;
      }
      jsonList.add(charAt);
    }
    return jsonList;
  }

}

