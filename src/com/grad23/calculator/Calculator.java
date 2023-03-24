package com.grad23.calculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Calculator {
    static final int maxBuffer = 25;

    private String display = "";
    private String eString = " ";
    private String negString = " ";
    private String mString = " ";

    private double accumulator = 0.0;
    private double memory = 0.0;
    private double current = 0.0;

    private boolean done = false;
    private boolean equalPressed = false;

    private Operator currentOperator = Operator.NONE;
    private Operator lastOperator = Operator.NONE;

    public void clearAC() {
        accumulator = 0.0;
        memory = 0.0;
        currentOperator = null;
        clearC();
    }

    public void clearC() {
        current = 0.0;
        populateDisplay();
        display = "";
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double value) {
        current = value;
    }

    public double getAccumulator() {
        return accumulator;
    }

    public double getMemory() {
        return memory;
    }

    public String getDisplay() {
        return display;
    }

    public boolean isDone() {
        return done;
    }

    public void setOperator(Operator operator) {
        lastOperator = currentOperator;

        switch (operator) {
            case ADDITION:
            case SUBTRACTION:
            case DIVISION:
            case MULTIPLICATION:
                currentOperator = operator;
                accumulator = current;
                break;
            case PERCENT:
            case SQUARE_ROOT:
                currentOperator = operator;
                calculate();
                break;
            default:
                break;
        }
    }

    public void calculate() throws UnsupportedOperationException {
        if (currentOperator == Operator.PERCENT) {
            switch (lastOperator) {
                case ADDITION:
                case SUBTRACTION:
                case NONE:
                    current = Operator.PERCENT.apply(accumulator, current);
                    currentOperator = lastOperator;
                    break;
                case DIVISION:
                case MULTIPLICATION:
                    current = Operator.PERCENT.apply(current, 1.0);
                    currentOperator = lastOperator;
                    break;
                case PERCENT:
                case SQUARE_ROOT:
                    // Do nothing
                    break;
                default:
                    throw new UnsupportedOperationException("Unimplemented operation: " + lastOperator);
            }
        }

        if (currentOperator == Operator.SQUARE_ROOT) {
            accumulator = currentOperator.apply(current);
        } else {
            accumulator = currentOperator.apply(accumulator, current);
        }
        current = accumulator;
        populateDisplay();
    }

    private void populateDisplay() {
        double abs = Math.abs(current);
        display = formatScientificNotation(abs);

        eString = isExponential(current) ? "E" : " ";
        negString = isNegative(current) ? "-" : " ";
        mString = hasMemory(current) ? "M" : " ";
    }

    private boolean hasMemory(double value) {
        return memory != 0;
    }

    public void PerformMemory(String function) {
        switch (function) {
            case "MRC":
                break;
            case "M+":
                memory = Operator.ADDITION.apply(memory, current);
                break;
            case "M-":
                memory = Operator.SUBTRACTION.apply(memory, current);
                break;
        }

        current = memory;
        populateDisplay();
    }

    public boolean isNegative(double value) {
        return (value >= 0.0) ? false : true;
    }

    public boolean isExponential(double value) {
        return String.valueOf(value).toLowerCase().contains("e");
    }

    public String formatScientificNotation(double value) {
        String result = formatNumber(value);
        if (result.length() > maxBuffer) {
            String format = "0." + "#".repeat(maxBuffer - 2) + "E0";
            NumberFormat formatter = new DecimalFormat(format);

            result = formatter.format(value);
        }

        return result;
    }

    public String formatNumber(double number) {
        String buffer = "#".repeat(maxBuffer);
        String format = buffer + '.' + buffer;
        NumberFormat formatter = new DecimalFormat(format);
        return formatter.format(number);
    }

    public void addDigit(String digit) {
        if (display.length() < Calculator.maxBuffer) {
            display = display + digit;

            long dotCount = display.chars().filter(ch -> ch == '.').count();
            if (dotCount > 1) {
                return;
            }
            double value = Double.valueOf(display);
            current = value;
        }
    }

    public boolean performAction(String actionCommand) {
        boolean result = true;
        switch (actionCommand) {
            case "AC":
                clearAC();
                break;
            case "C":
                clearC();
                break;
            case "OFF":
                done = true;
                break;
            case ".":
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                if(equalPressed){
                  clearC();
                  equalPressed = false;
                }
                addDigit(actionCommand);
                break;
            case "MRC", "M-", "M+":
                PerformMemory(actionCommand);
                break;
            case "/", "*", "-", "+", "%", "√":
                Operator operator = Operator.fromString(actionCommand);
                setOperator(operator);
                clearC();
                break;
            case "=":
                if(accumulator != 0){
                  calculate();
                  equalPressed = true;
                }
                
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    public String getEDisplay() {
        return eString;
    }

    public String getNegDisplay() {
        return negString;
    }

    public String getMDisplay() {
        return mString;
    }
}
