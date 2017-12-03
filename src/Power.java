public class Power {
	private final int exponent;
	private final Literal literal;

	public Power(int exponent, Literal literal) {
		if (exponent < 0)
			this.exponent = 0;
		else
			this.exponent = exponent;
		this.literal = literal;
	}

	public Power(Literal literal) {
		this.literal = literal;
		this.exponent = 1;
	}

	public Power(Power p) {
		exponent = p.exponent;
		literal = p.literal;
	}

	public int getExponent() {
		return exponent;
	}

	public Literal getLiteral() {
		return literal;
	}

	public boolean isZero() {
		return literal == null || (exponent > 0 && literal.isZero());
	}

	@Override
	public String toString() {
		switch (exponent) {
			case 0:
				return "1";
			case 1:
				return String.valueOf(literal);
			default:
				return literal + "^" + exponent;
		}
	}

	int getDegree() {
		if (literal == null)
			return 0;
		return literal.getDegree() * exponent;
	}

	public Power substitute(String toSubstitute, double value) {
		if (literal != null)
			return new Power(exponent, literal.substitute(toSubstitute, value));
		return this;
	}

	double evaluate(double defaultValue) {
		return Math.pow(literal.evaluate(defaultValue), exponent);
	}

	/**
	 * Converts an input string to a power
	 * Input
	 *
	 * @param input String representation of literal ^ exponent
	 * @return the resulting power
	 */
	public static Power parse(String input) {
		if (input == null || input.equals("")) {
			return new Power(Literal.parse(""));
		}
		String[] splitted = input.split("\\^", 2);
		if (splitted.length == 1) {
			return new Power(Literal.parse(splitted[0]));
		}
		int exponent = 1;
		try {
			exponent = Integer.parseInt(splitted[1]);
		} catch (NumberFormatException e) {
			exponent = 1;
		}

		return new Power(exponent, Literal.parse(splitted[0]));

	}

}