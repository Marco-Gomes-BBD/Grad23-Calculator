package com.grad23.calculator;

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
