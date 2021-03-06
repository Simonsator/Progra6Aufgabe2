public class Polynomial {

	private final Monomial summand;

	private final Polynomial summands;

	public Polynomial(Monomial summand, Polynomial summands) {
		this.summand = summand;
		this.summands = summands;
	}

	public Polynomial(Polynomial poly) {
		summand = poly.summand;
		summands = poly.summands;
	}

	/**
	 * The empty polynomial interpreted as 0, which is the default representation.
	 */
	public static final Polynomial ZERO = new Polynomial(null, null);

	public Polynomial(Monomial toLift) {
		summand = toLift;
		summands = ZERO;
	}

	public boolean isZero() {
		if ((summand == null || summand.isZero()) && (summands == null || summands.isZero()))
			return true;
		return false;
	}

	public Monomial getSummand() {
		return summand;
	}

	public Polynomial getSummands() {
		return summands;
	}

	@Override
	public String toString() {
		if (isZero())
			return "0";
		if (summand == null)
			return String.valueOf(summands);
		String summandsToString = "";
		if (summands != null && !summands.isZero())
			summandsToString = "+" + summands;
		return summand + summandsToString;
	}

	int getDegree() {
		int degree = 0;
		if (summand != null)
			degree = summand.getDegree();
		int degreeNext = 0;
		if (summands != null)
			degreeNext = summands.getDegree();
		return Math.max(degree, degreeNext);
	}

	Polynomial substitute(String toSubstitute, double value) {
		if (!isZero() && summand != null) {
			return new Polynomial(summand.substitute(toSubstitute, value), summands.substitute(toSubstitute, value));
		}
		return new Polynomial(this);
	}

	double evaluate(double defaultValue) {
		double evaluated = 0.;
		if (summands != null)
			evaluated = summands.evaluate(defaultValue);
		if (summand != null)
			evaluated += summand.evaluate(defaultValue);
		return evaluated;
	}

	/**
	 * @param input String representation of polynomial
	 * @return the resulting polynomial
	 */
	public static Polynomial parse(String input) {
		if (input == null || input.equals("")) {
			return ZERO;
		}
		String[] splitted = input.split("\\+", 2);
		if (splitted.length == 1) {
			return new Polynomial(Monomial.parse(splitted[0]));
		}
		return new Polynomial(Monomial.parse(splitted[0]), parse(splitted[1]));
	}

	public static void main(String[] args) {
		Polynomial p;
		Polynomial q;
		String[] testValues = {"0", "(-3.1415)", "(-1)*x^3+(3.0)*x*y^2", "x+(-1)^5", "3^5+2^6+(3)*(2)*(5)*(4)", "x", "x^4", "x^2*y*z+2*x+(-3)", "x^2+2*x*y+y^2", "(0.0)*x^1000+(0.0)*x*y*z^100+(0.0)^7", "(0.0)*x^1+(0.0)^0"};
		int[] expectedDegrees = {0, 0, 3, 1, 0, 1, 4, 4, 2, 0, 0};
		int i = 0;
		for (String s : testValues) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Testing polynomial read from " + s + ".");
			p = parse(s);
			System.out.println(p);
			System.out.println("isZero?: " + p.isZero());
			System.out.println("degree: " + p.getDegree());
			System.out.println("degree as expected: " + (p.getDegree() == expectedDegrees[i]));
			i++;
			q = p.substitute("x", 1.);
			System.out.println("x substituted by 1: " + q);
			System.out.println("x substituted by 1, rest substituted by 0: " + q.evaluate(0.0));
		}
	}
}