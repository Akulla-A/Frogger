package util;

public class Case {
	public final int absc;
	public final int ord;

	public Case(int absc, int ord) {
		super();
		this.absc = absc;
		this.ord = ord;

		/*
		Random r = new Random();
		float rand = r.nextFloat();

		if(rand < 0.5){
			type = CaseType.Normal;
		} else {
			type = CaseType.values()[r.nextInt(4)];
		}
		*/
	}
}
