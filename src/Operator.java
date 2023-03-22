public enum Operator
{
    ADDITION("+") {
        @Override public float apply(float l, float r) {
            return l + r;
        }
    },
    SUBTRACTION("-") {
        @Override public float apply(float l, float r) {
            return l - r;
        }
    },
    DIVISION("/") {
      @Override public float apply(float l, float r) {
          return l / r;
      }
    },
    MULTIPLICATION("*") {
      @Override public float apply(float l, float r) {
          return l * r;
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
    public abstract float apply(float l, float r);

    @Override public String toString() {
        return text;
    }
  
}
