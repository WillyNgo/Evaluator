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
import static java.lang.Character.isDigit;
import org.junit.Assert;

/**
 *
 * @author William Ngo
 */

@RunWith(Parameterized.class)
public class ExceptionTest {
    
    String expression;
    
    public ExceptionTest(String expression) {
        this.expression = expression;
    }
    
    @Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
            
            {//Empty expression
                ""
            },
            {//Contains alphetical characters
                "5+3+a"
            },
            {//Expression not complete
                "5+3+"
            },
            {//Wrong number of parentheses
                "(5+2"
            },
            {//Consecutive operator
                "12++2"
            }
        });
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void assessExceptionTest(){
        System.out.println("/***********************************************/");
        System.out.println("/TESTING ILLEGAL ARGUMENT EXCEPTION/");
        System.out.println("Evaluating expression:\t" + expression);
        System.out.println("");
        Evaluator eval = new Evaluator();
        MyQueue<String> infixQueue = stringToQueue(expression);
        MyQueue<String> answerQueue = eval.evaluate(infixQueue);
        
        //We should be throwing an exception so it shouldn't run this line 
        fail();
    }
    
    /*
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
    }*/
    
    
    /**
     * Utility method to convert a string expression into a queue. It will
     * take into account numbers that are higher than 10 (2 digits or more) as well
     * as negative numbers (starting number of expression is negative)
     * 
     * @param expression
     * @return Queue containing each value of the expression.
     */
    private MyQueue stringToQueue(String expression) {
        //Convert string expression to char[] for easy iterations
        char[] chars = new char[expression.length()];
        for(int i = 0; i < expression.length(); i++){
            
            chars[i] = expression.charAt(i);
        }
        
        MyQueue queue = new MyQueue();
        String number = "";
        for (int i = 0; i < chars.length; i++) {
            //If the first value of the expression is negative, store the substraction
            //sign with the following number
            if(chars[i] == '-' && i == 0){
                number = chars[i]+"";
            }
            else if(chars[i] == '(')
            {
                queue.push(chars[i] + "");
            }
            //If is a digit, concatenate the digit to the number string
            //This is to add numbers that contain more than two characters into the list
            else if (isDigit(chars[i]) || chars[i]=='.') {
                number = number + chars[i];
            }else { //If it's an operator
                //Now we know that the number does not contain any more digit
                //so we add the current number string into the list
                queue.push(number);
                
                number = "";
                //Now add the operator to the list
                queue.push(chars[i] + "");
            }
            //Add the last number that is located at the end of the expression
            if (i == chars.length - 1) {
                queue.push(number);
            }
        }
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
