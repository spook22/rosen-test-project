package rspasov.program;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters, correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class TFinder {

	private final double error;

	private double probabilityTarget, t = 1.0, d = 0.5;

	private int dof = 2;

	public TFinder(double error) {
		super();
		this.error = error;
	} // end

	public double getD() {
		return d;
	} // end

	public synchronized double find(double probabilityTarget, int dof, double t, double d) {
		this.probabilityTarget = probabilityTarget;
		this.dof = dof;
		this.t = t;
		this.d = d;
		return findT();
	} // end

	protected double findT() {
		double currentProbability = Calculator.calculateProbability(dof, t, 2);
		// System.out.println("t=" + t + ", d=" + d + ", prob=" + currentProbability);
		double delta = Math.abs(probabilityTarget - currentProbability);
		if (delta < error) {
			return t;
		} else {
			boolean greater = false;
			if (currentProbability > probabilityTarget) {
				greater = true;
			}
			adjustD(greater);
			t += d;
			return findT();
		}
	} // end

	protected void adjustD(boolean greater) {
		if (greater && d > 0 || !greater && d < 0) {
			d = -d / 2;
		}
	} // end

} // end
