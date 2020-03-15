import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by kiran.biliyawala on 15/03/20.
 */
public class JsonValidator {

    boolean isValidJson(String jsonString){
        boolean valid = Boolean.TRUE;
        Stack<Character> op = new Stack<>();
        Set<Character> openingChr = ImmutableSet.of('{', '[', '(');
        Set<Character> quotes = ImmutableSet.of('\"', '\'', '`');
        Map<Character, Character> op2Cls = ImmutableMap.of(
                '{', '}',
                '[', ']',
                '(', ')'
        );
        int quoteCounter, dq, tupleCounter;
        quoteCounter = dq = tupleCounter = 0;
        char prevOperator;
        boolean keyClosure, valueClosure, tupleIP;
        keyClosure = valueClosure = tupleIP = Boolean.FALSE;
        try {
            for (char c : stripJson(jsonString).toCharArray()) {
                System.out.println(c);

                if(tupleCounter < 0) { valid = Boolean.FALSE; break; }

                if(tupleIP){
                    if(!openingChr.contains(c)) { tupleCounter++; }
                    tupleIP = Boolean.FALSE;
                }

                if(valueClosure) {
                    if(c == '\"' || openingChr.contains(c) || op.peek() == '[') {
                        valueClosure = Boolean.FALSE;
                    } else {
                        valid = Boolean.FALSE;
                        break;
                    }

                }
                if (openingChr.contains(c)) {
                    op.push(c);

                } else if (op2Cls.values().contains(c)) {
                    prevOperator = op.pop();
                    if (c != op2Cls.get(prevOperator)) {
                        valid = Boolean.FALSE;
                        break;
                    }

                } else if (quotes.contains(c)) {
                    quoteCounter++;
                    if(quoteCounter % 2 == 1) {
                        op.push(c);
                    } else {
                        prevOperator = op.pop();
                        if (prevOperator != c) {
                            valid = Boolean.FALSE;
                            break;
                        }
                    }
                    if(c == '\"') {
                        dq++;
                        if(dq % 2 == 0){
                            keyClosure = Boolean.TRUE;
                        }
                    }

                } else if (':' == c) {
                    tupleIP = Boolean.TRUE;
                    if(dq % 2 != 0 || !keyClosure) {
                        valid = Boolean.FALSE;
                        break;
                    }
                    keyClosure = valueClosure = Boolean.FALSE;


                } else if (',' == c) {
                    if(dq % 2 != 0) {
                        valid = Boolean.FALSE;
                        break;
                    }
                    valueClosure = Boolean.TRUE;
                    keyClosure = Boolean.FALSE;
                    tupleCounter--;
                }

            }

            if(!op.isEmpty()) valid = Boolean.FALSE;
            if(tupleCounter != 1) valid = Boolean.FALSE;
        } catch (ArrayIndexOutOfBoundsException| EmptyStackException e) {
            valid = Boolean.FALSE;
        }
        return valid;
    }

    private String stripJson(String jsonString) {
        return jsonString.replace(" ", "").replace("\t", "").replace("\n", "");
    }
}
