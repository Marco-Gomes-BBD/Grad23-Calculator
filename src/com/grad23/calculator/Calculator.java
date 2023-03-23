package com.grad23.calculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Calculator {
    static final int maxBuffer = 25;

    private double accumulator = 0.0;
    private double memory = 0.0;
    private double current = 0.0;

    private Operator currentOperator = null;

    public void clearAC() {
        accumulator = 0.0;
        memory = 0.0;
        currentOperator = null;
        clearC();
    }

    public void clearC() {
        current = 0.0;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double value) {
        current = value;
    }

    public void setOperator(Operator operator) {
        currentOperator = operator;
    }

    public void calculate() {
        accumulator = currentOperator.apply(accumulator, current);
  
    }

    public boolean isNegative(double value){
        return (value > 0)?false:true;
    }

    public boolean isExponential(double value){
        return String.valueOf(value).toLowerCase().contains("e");
    }

    public double formatScientificNotation(double value){
        if(String.valueOf(value).length() > 25){
            NumberFormat numFormat = new DecimalFormat();
            numFormat = new DecimalFormat("0.###E0");

            return numFormat.format(x);
        }

        return value;
    }

    public void PerformMemory(String function) {
        switch (function) {
            case "MRC":
                break;
            case "M-":
                break;
            case "M+":
                break;
        }
    }
}
