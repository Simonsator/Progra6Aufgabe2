public class Literal {
	private final Typ type;
	private final double value;
	private final String name;


	public Literal(double value) {
		this.value = value;
		type = Typ.VALUE;
		name = "";
	}

	public Literal(String name) {
		value = 0;
		this.name = name;
		type = Typ.VAR;
	}

	public Literal(Literal toCopy) {
		type = toCopy.getType();
		value = toCopy.getValue();
		name = toCopy.getName();
	}

	public Typ getType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		switch (type) {
			case VAR:
				return name;
			case VALUE:
				return "(" + value + ")";
			default:
				return name;
		}
	}

	int getDegree() {
		switch (type) {
			case VALUE:
				return 0;
			case VAR:
				return 1;
			default:
				return 0;
		}
	}

	public boolean isZero() {
		switch (type) {
			case VAR:
				return false;
			case VALUE:
				return value == 0.;
			default:
				return false;
		}
	}

	public Literal substitute(String toSubstitute, double value) {
		switch (type) {
			case VALUE:
				return this;
			case VAR:
				if (toSubstitute.equals(name))
					return new Literal(value);
				return this;
			default:
				return this;
		}
	}

	public double evaluate(double defaultValue) {
		switch (type) {
			case VAR:
				return defaultValue;
			case VALUE:
				return value;
			default:
				return value;
		}
	}

	/**
	 * Converts an input string to a literal
	 *
	 * @param input String representation of literal
	 * @return the resulting literal
	 */
	public static Literal parse(String input) {
		if (input == null || input.equals("")) {
			return new Literal(1.);
		}
		double value = 0.;
		String name = "";
		input = input.replaceAll("[()]", "");
		try {
			value = Double.parseDouble(input);
			return new Literal(value);
		} catch (NumberFormatException e) {
			name = input;
		}
		return new Literal(name);
	}

}
