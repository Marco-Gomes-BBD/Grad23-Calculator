package com.grad23.calculator;

import java.lang.Math;

public enum Operator {
    NONE("NONE") {
        @Override
        public double apply(double l, double r) {
            return r;
        }
    },
    ADDITION("+") {
        @Override
        public double apply(double l, double r) {
            return l + r;
        }
    },
    SUBTRACTION("-") {
        @Override
        public double apply(double l, double r) {
            return l - r;
        }
    },
    DIVISION("/") {
        @Override
        public double apply(double l, double r) {
            return (r != 0.0) ? l / r : Double.NaN;
            // return l / r; // Fat zero :)
        }
    },
    MULTIPLICATION("*") {
        @Override
        public double apply(double l, double r) {
            return l * r;
        }
    },
    PERCENT("%") {
        @Override
        public double apply(double l, double r) {
            return l * r / 100.0;
        }
    },
    SQUARE_ROOT("√") {
        @Override
        public double apply(double l) {
            return Math.sqrt(l);
        }
    };

    private final String text;

    private Operator(String text) {
        this.text = text;
    }

    public double apply(double l, double r) {
        return Double.NaN;
    }

    public double apply(double l) {
        return Double.NaN;
    }

    @Override
    public String toString() {
        return text;
    }

    static public Operator fromString(String op) {
        Operator result = null;
        switch (op) {
            case "NONE":
                result = Operator.NONE;
                break;
            case "+":
                result = Operator.ADDITION;
                break;
            case "-":
                result = Operator.SUBTRACTION;
                break;
            case "/":
                result = Operator.DIVISION;
                break;
            case "*":
                result = Operator.MULTIPLICATION;
                break;
            case "%":
                result = Operator.PERCENT;
                break;
            case "√":
                result = Operator.SQUARE_ROOT;
                break;
        }
        return result;
    }
}
