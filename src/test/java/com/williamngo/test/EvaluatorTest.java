package com.williamngo.test;

import com.williamngo.business.Evaluator;
import com.williamngo.business.MyQueue;
import com.williamngo.business.MyStack;
import static java.lang.Character.isDigit;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Assert;
import org.junit.Ignore;
/**
 *
 * @author William Ngo
 */

@RunWith(Parameterized.class)
public class EvaluatorTest {
    
    String expression;
    double expectedAnswer;
    String expectedPostfix;
    
    public EvaluatorTest(String expression, double answer, String expectedPostfix) {
        this.expression = expression;
        this.expectedAnswer = answer;
        this.expectedPostfix = expectedPostfix;
        
    }
    
    @Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
            {//Simple Addition
                "2 + 5",
                7,
                "2 5 +"
            },
            {//Simple Substraction
                "10 - 7",
                3,
                "10 7 -"
            },
            {//Simple Multiplication
                "5 * 9",
                45,
                "5 9 *"
            },
            {//Simple Division
                "80 / 4",
                20,
                "80 4 /"
            },
            {
                "5 / 2",
                2.5,
                "5 2 /"
            },
            {//>1 operand addition
                "2 + 5 + 6",
                13,
                "2 5 + 6 +"
            },
            {//>1 operand substraction
                "20 - 5 - 2",
                13,
                "20 5 - 2 -"
            },
            {//>1 operand multiplication
                "3 * 5 * 4",
                60,
                "3 5 * 4 *"
            },
            {//>1 operand division
                "10 / 5 / 10",
                0.2,
                "10 5 / 10 /"
            },
            {//floating point addition
                "5.3 + 12.5",
                17.8,
                "5.3 12.5 +"
            },
            {//floating point substract
                "55.3 - 12.5",
                42.8,
                "55.3 12.5 -"
            },
            {//mixed operators
                "12 + 8 - 5 * 4",
                0,
                "12 8 + 5 4 * -"
            },
            {//More mixed operators
                "12 * 2 + 24 / 6",
                12*2+24/6,
                "12 2 * 24 6 / +"
            },
            {//floating point mixed operators
                "55.3 - 12.5 * 2.1",
                29.05,
                "55.3 12.5 2.1 * -"
            },
            {//Parenthesis
                "( 55.3 - 12.5 ) * 2",
                85.6,
                "55.3 12.5 - 2 *"
            },
            {//Parentheses inside parenthesis
                "( ( 5 - 1 + 6 ) * ( 2 + 2 ) - 1 ) * 2",
                ((5-1+6)*(2+2)-1)*2,
                "5 1 - 6 + 2 2 + * 1 - 2 *"
            },
            {//Floating point parenthesis
                "( 5.5 + 2.8 ) * 6.9",
                (5.5+2.8)*6.9,
                "5.5 2.8 + 6.9 *"
            },
            {//Negative answer
                "8 - 9",
                8-9,
                "8 9 -"
            },
            {//Negative answer
                " 8 - 9 * 10",
                -82,
                "8 9 10 * -"
            },
            {// Addition with a negative number
                "( 50 - 100 ) / 2 + -12.5",
                -37.5,
                "50 100 - 2 / -12.5 +"
            },
            {//
                "-9 + 19 - 12 + -23",
                -9+19-12-23,
                "-9 19 + 12 - -23 +"
            },
            {
                "( 23 - 23 ) * 2 * ( 34 - 14 )",
                (23-23)*2*(34-14),
                "23 23 - 2 * 34 14 - *"
            },
            {//redundant parenthesis does not affect equation
                "( 2 + 5 )",
                7,
                "2 5 +"
            },
            {
                "2 * ( ( 54 - 4.5 ) - 40 )",
                2*((54-4.5)-40),
                "2 54 4.5 - 40 - *"
            },
            {
                "( ( ( ( ( ( 2 + 3 ) - 54 * 0.5 ) + 212 / 2 ) + 33 - 21 ) ) * 2 ) - 1",
                ((((((2+3)-54*0.5)+212/2)+33-21))*2)-1,
                "2 3 + 54 0.5 * - 212 2 / + 33 + 21 - 2 * 1 -"
            },
            {
                "( ( 2 * ( 5 + 4 ) - ( 12 / 3 * 4 * 2 - 32 ) + 18 / ( 18 / 3 ) + 9 - ( 2 + 5 ) * ( 3 - 4 ) * ( 5 - 2 + 3 - 4 ) ) + 15 ) - 7",
                ((2*(5+4)-(12/3*4*2-32)+18/(18/3)+9-(2+5)*(3-4)*(5-2+3-4))+15)-7,
                "2 5 4 + * 12 3 / 4 * 2 * 32 - - 18 18 3 / / 9 + 2 5 + 3 4 - * 5 2 - 3 + 4 - * - + 15 + 7 -"
            }
        });
    }
    
    @Test
    public void evaluatePostfixTest(){
        System.out.println("/***********************************************/");
        System.out.println("/TESTING EVALUATION OF POSTFIX/");
        Evaluator eval = new Evaluator();
        MyQueue<String> infixQueue = stringToQueue(expression);
        MyQueue<String> answerQueue = eval.evaluate(infixQueue);
        double myAnswer = Double.valueOf(answerQueue.peek());
        
        //Displaying answer
        
        System.out.println("Evaluating expression:\t" + expression);
        System.out.println("Expected answer:\t" + expectedAnswer);
        System.out.println("Calculated answer:\t" + myAnswer);
        System.out.println("");
        
        assertEquals(expectedAnswer, myAnswer, 0.005);
    }
    //@Ignore
    @Test
    public void convertToPostfixTest(){
        Evaluator eval = new Evaluator();
        MyQueue<String> infixQueue = stringToQueue(expression);
        MyQueue<String> myPostfixQueue = eval.convertInfixToPostfix(infixQueue);
        MyQueue<String> expectedPostfixQueue = stringToQueue(expectedPostfix);
        
        System.out.println("/TESTING INFIX TO POSTFIX/");
        System.out.println("My infix:\t\t" + infixQueue.toString());
        System.out.println("My postfix:\t\t" + myPostfixQueue.toString());
        System.out.println("Expected postfix:\t" + expectedPostfixQueue.toString());
        System.out.println("");
        
        assertEquals(expectedPostfixQueue.toString(), myPostfixQueue.toString());
    }
    
    /**
     * Converts a string expression into a queue. The expression should have
     * every element seperated by a whitespace.
     * @param expression
     * @return 
     */
    public MyQueue stringToQueue(String expression){
        MyQueue<String> queue = new MyQueue();
        String value;
        String delimiter = " ";
        int index = 0;
        int endIndex = expression.indexOf(delimiter);
        
        //While it can still find the delimiter (white space)
        while(endIndex != -1){
            value = expression.substring(index, endIndex);
            queue.push(value);
            
            //update index to get next value
            index = endIndex + 1;
            endIndex = expression.indexOf(delimiter, index);
        }
        
        //Add the last element of the string expression
        value = expression.substring(index);
        queue.push(value);
        
        return queue;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
}
