package com.grad23.calculator;

import java.lang.Math;

public enum Operator {
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
            return l / r;
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
        public double apply(double l) {
            return l / 100;
        }

        @Override
        public double apply(double l, double r) {
            return l * (r / 100);
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

    /*
     * Use enum class like this:
     * double answer = Operator.SUBTRACTION.apply(3, 2)
     */
    public double apply(double l, double r) {
        return 0;
    }

    public double apply(double l) {
        return 0;
    }

    @Override
    public String toString() {
        return text;
    }

    static public Operator fromString(String op) {
        Operator result = null;
        switch (op) {
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
