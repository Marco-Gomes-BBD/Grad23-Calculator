import java.lang.Math;

public enum Operator
{
    ADDITION("+") {
      @Override 
      public float apply(float l, float r) {
          return l + r;
      }
    },
    SUBTRACTION("-") {
      @Override 
      public float apply(float l, float r) {
          return l - r;
      }
    },
    DIVISION("/") {
      @Override 
      public float apply(float l, float r) {
          return l / r;
      }
    },
    MULTIPLICATION("*") {
      @Override 
      public float apply(float l, float r) {
          return l * r;
      }
    },
    PERCENT("%") {
      @Override 
      public float apply(float l) {
          return l/100 ;
      }
      @Override
      public float apply(float l, float r) {
        return l * (r/100);
      }
    },
    SQUARE("√") {
      @Override 
      public float apply(float l) {
          return Math.sqrt(l);
      }
    }
    ;

    private final String text;

    private Operator(String text) {
        this.text = text;
    }

    /*
    * Use enum class like this:
    * float answer = Operator.SUBTRACTION.apply(3, 2)
    */
    public float apply(float l, float r){
      return 0;
    }
    public float apply(float l){
      return 0;
    }

    @Override public String toString() {
        return text;
    }
  
}
