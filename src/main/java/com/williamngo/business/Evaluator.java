package com.williamngo.business;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Locale;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
/**
 *
 * @author William
 */
public class Evaluator {

    //private final Logger log = LoggerFactory.getLogger(getClass().getName());
    private MyStack<String> stack;
    public MyQueue<String> infix;
    public MyQueue<String> postfix;

    public Evaluator() {
        this.stack = new MyStack();
        this.postfix = new MyQueue();
        this.infix = new MyQueue();
    }

    /**
     * Produces a postfix equation from an values of an infix queue. It will 
     * then evaluate the postfix queue and return a queue which contains only
     * one value which is the result of the evaluation.
     *
     * @param myInfix
     * @return
     */
    public MyQueue evaluate(MyQueue myInfix) {
        MyQueue answerQueue = new MyQueue();
        setInfixQueue(myInfix);
        
        validateValues(infix);
        postfix = convertInfixToPostfix(infix);
        answerQueue = calculatePostfix(postfix);
        /* Do we catch?
        try {    
        } catch (IllegalArgumentException iae) {
            //CHANGE FOR LOGS LATER
            System.out.println(iae.getMessage());
        }*/
            
        return answerQueue;
    }

    /**
     * This method will convert an infix expression into a postfix expression
     * using two queues and a stack.
     *
     * @param myInfix
     * @return postfix - The postfix expression converted from the infix
     */
    public MyQueue convertInfixToPostfix(MyQueue myInfix) {
        setInfixQueue(myInfix);
        MyQueue myPostfix = new MyQueue();
        
        for (int i = 0; i < infix.getSize(); i++) {
            //If it's a number, add directly to our postfix queue
            if (isNumber(infix.peek())) {
                myPostfix.push(infix.pop());
            } else//If it's an operator
            //Check if there's an operator in the stack
            if (!stack.isEmpty()) {
                if(infix.peek().equals("(")){
                    stack.push(infix.pop());
                }
                //When encounter closing parenthesis,
                else if(infix.peek().equals(")")){
                    // pop everything from the stack until the opening parenthesis
                    while(!stack.peek().equals("("))
                        myPostfix.push(stack.pop());
                    
                    //Get rid of opening and closing parenthesis
                    stack.pop();
                    infix.pop();
                }
                //Evaluate if current operator in the queue is of lower precedence
                //than the current operator in the stack
                else if(checkIfLower(infix.peek(), stack.peek())) {
                    //Replace operator in the stack with operator in the queue
                    //And put stack operator in the postfix queue
                    myPostfix.push(stack.pop());
                    stack.push(infix.pop());
                } else {//Higher precedence = just put it in the stack
                    stack.push(infix.pop());
                }
            } else { //If it's first operator to be put in the stack, 
                stack.push(infix.pop());
            }

            //Once reached last element of the infixQueue, put the rest of
            //the stack operators in the post fix queue
            if (i == (infix.getSize() - 1)){
                while (!stack.isEmpty()) {
                    myPostfix.push(stack.pop());
                }
            }
        }
        
        return myPostfix;
    }

    /**
     * This method will iterate through the postfix expression and calculate
     * the answer. It will perform the calculations with the help of a stack
     * and put the final answer inside a queue
     *
     * @param myPostfix  
     * @return answerQueue The queue containing the answer of the expression
     */
    private MyQueue calculatePostfix(MyQueue<String> myPostfix) {
        MyStack<String> answerStack = new MyStack();
        MyQueue<String> answerQueue = new MyQueue();
        
        for(int i = 0; i < myPostfix.getSize(); i++){
            if(isNumber(myPostfix.peek())){
                answerStack.push(myPostfix.pop());
            }
            //If it's an operator, pop last two values of the answer stack,
            //calculate them with the operator and then push the result back
            //into the answer stack
            else{
                double secondOperand = Double.valueOf(answerStack.pop());
                double firstOperand = Double.valueOf(answerStack.pop());
                String operator = myPostfix.pop();
                
                answerStack.push(doMath(firstOperand, secondOperand, operator));
            }
        }
        
        //Once all the calculations has been done, pop the answer to the queue
        double answer = Double.valueOf(answerStack.pop());
        //Allows maximum of 2 decimal numbers.
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        answerQueue.push(formatter.format(answer));
        return answerQueue;
    }
    
    

    /*************************************************************/
    /********************* UTILITY METHODS ***********************/
    /*************************************************************/
    
    /**
     * Perform the arithmetic operation designated by the given operands and
     * operator.
     * 
     * @param first first operand
     * @param second second operand
     * @param operator
     * @return Answer of calculation in the form of a string to be put in a stack
     */
    private String doMath(double first, double second, String operator){
        double answer = 0;
        switch(operator){
            case "+":
                answer = first + second;
                break;
            case "-":
                answer = first - second;
                break;
            case "*":
                answer = first * second;
                break;
            case "/":
                answer = first / second;
                break;
            default:
                throw new IllegalArgumentException("Unknown operator, unable to perform arithmetic.");
        }
        
        return answer + "";
    }
    
    /**
     * Sets given infix to this evaluator
     * @param infix 
     */
    public void setInfixQueue(MyQueue infix) {
        this.infix = infix;
    }

    /**
     * This method will iterate through the string array containing each value
     * of the expression and validate. It will ensure that the infixQueue can
     * only contain numbers or operators and nothing else. It will also ensure
     * that the expression of the queue is properly formatted e.g. no consecutive
     * operators.
     *
     * @param infix
     */
    public void validateValues(MyQueue infix) {
        //Takes each individual values into a string[] for easier iteration
        String[] values = queueToStringArray(infix);
        
        if(values.length == 0){
            throw new IllegalArgumentException("Expression cannot be empty.");
        }
        //Check if there's an operator at the beginning or at the end of the string
        if (isOperator(values[0]) || isOperator(values[values.length - 1])) {
            throw new IllegalArgumentException("Expression cannot start/end with an operator.");
        }
        //Checks for characters other than numbers and operators
        if (containsIllegalCharacters(values)) {
            throw new IllegalArgumentException("Expression contains illegal characters.");
        }
        //Check if there's any parenthesis. If there is, should check for correct pairs.
        if(checkParenthesis(values)){
            throw new IllegalArgumentException("Wrong number of parentheses");
        }
        //Checks if expression contains consecutive operators
        if (containsConsecutiveOperator(values)) {
            throw new IllegalArgumentException("Expression not formulated properly: consecutive operators.");
        }
    }
    
    /**
     * Determines if expression contains a parenthesis. If it does, this method
     * will check if there are a correct numbers of parenthesises.
     * 
     * @param values
     * @return 
     */
    private boolean checkParenthesis(String[] values){
        int pairs = 0;
        if (Arrays.asList(values).contains("(")) {
            for (String parenthesis : values) {
                if(parenthesis.equals("(") || parenthesis.equals(")")) {
                    pairs++;
                }
            }
        }
        
        return (pairs % 2 != 0);
    }

    /**
     * Converts a queue into a string array. The array will contain all the values
     * of the queue
     *
     * @param queue
     * @return
     */
    private String[] queueToStringArray(MyQueue<String> queue) {
        String[] values = new String[queue.getSize()];
        for (int i = 0; i < queue.getSize(); i++) {
            values[i] = queue.get(i);
        }
        return values;
    }

    /**
     * This method checks if the expression contains any consecutive operators
     * which is considered not properly formatted.
     *
     * @param expression
     * @return
     */
    private boolean containsConsecutiveOperator(String[] expression) {
        boolean result = false;
        int next = 0; //Index to check for upcoming character

        //For each character in the expression, check the next one if it is also
        //an operator
        for (int i = 0; i < expression.length; i++) {
            next = i + 1;
            if (isOperator(expression[i])) {
                if (isOperator(expression[next]) && !isParenthesis(expression[next])) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Returns true if the expression does not contain a number OR an operator
     * somewhere in the expression
     * @param expression to be evaluated
     * @return true if string expression contains a letter
     */
    private boolean containsIllegalCharacters(String[] values) {
        for (String str : values) {
            if (!isNumber(str) && !isOperator(str) && !isParenthesis(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if given char is an operator
     *
     * @param c
     * @return
     */
    private boolean isOperator(String c) {
        return (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/"));
    }
    
    /**
     * Returns true if given string is a parenthesis. This is to allow the use
     * of parenthesis at the beginning of an infix expression as opposed to an
     * arithmetic operator.
     * 
     * @param c
     * @return 
     */
    private boolean isParenthesis(String c){
        return(c.equals("(") || c.equals(")"));
    }

    /**
     * Determines if the operator in the front of the infixQueue is of lower 
     * precedence than the operator at the top of the stack
     *
     * @param stackChar - The character that is in the stack.
     * @param queueChar - The character that is in the queue.
     * @return
     */
    private boolean checkIfLower(String queueChar, String stackChar) {
        int queueCh = getOperatorLevel(queueChar);
        int stackCh = getOperatorLevel(stackChar);

        return(queueCh <= stackCh);
    }

    /**
     * Returns the level of precedence of the operator.
     *
     * @param c
     * @return
     */
    private int getOperatorLevel(String c) {
        switch (c) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "(":
            case ")":
                return 0;
            default:
                throw new IllegalArgumentException("Unknown operator.");
        }
    }

    /**
     * Determines if the given string is a valid number.
     *
     * @param num
     * @return
     */
    private boolean isNumber(String num) {
        try {
            Double d = Double.parseDouble(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
