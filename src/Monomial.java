public class Monomial {

	private final Power factor;

	private final Monomial factors;

	public Monomial(Power factor, Monomial factors) {
		this.factor = factor;
		this.factors = factors;
	}

	public Monomial(Monomial mon) {
		factor = mon.factor;
		factors = mon.factors;
	}

	/**
	 * The empty monomial interpreted as 1
	 */
	public static final Monomial ONE = new Monomial(null, null);

	public Monomial(Power toLift) {
		factor = toLift;
		factors = ONE;
	}

	public Power getFactor() {
		return factor;
	}

	public Monomial getFactors() {
		return factors;
	}

	public boolean isZero() {
		return factor == null || factor.isZero() || (factors != null && factors.isZero());
	}

	@Override
	public String toString() {
		if (factor == null || factor.isZero())
			return "1";
		return factor + "*" + factors;
	}

	int getDegree() {
		if (isZero())
			return 0;
		int degree = 0;
		if (factor != null)
			degree = factor.getDegree();
		if (factors != null)
			degree += factor.getDegree();
		return degree;
	}

	Monomial substitute(String toSubstitute, double value) {
		if (!isZero())
			return new Monomial(factor.substitute(toSubstitute, value), factors.substitute(toSubstitute, value));
		return new Monomial(this);
	}

	double evaluate(double defaultValue) {
		if (isZero())
			return 1.;
		return factor.evaluate(defaultValue) * factors.evaluate(defaultValue);
	}

	/**
	 * @param input String representation of monomial
	 * @return the resulting monomial
	 */
  /*public static Monomial parse(String input){
    if(input==null||input.equals("")){
      return ONE;
    }
    String[] splitted = input.split("\\*",2);
    if(splitted.length==1){
      return new Monomial(Power.parse(splitted[0]));
    }
    return new Monomial(Power.parse(splitted[0]),parse(splitted[1]));
  }*/

}