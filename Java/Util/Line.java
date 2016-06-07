package de.uni_passau.sep.portalgolf.util;

import java.io.Serializable;

/**
 * The class <code>Line</code> represents a two-dimensional line.
 */
public class Line implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2290157632073474799L;
    private Vector2D start;
    private Vector2D end;
    private static final double EPSILON_GAUSS = 1e-10;

    /**
     * Creates a new line with <code>start</code> as start point and
     * <code>end</code> as end point.
     *
     * @param start
     *            The start point of the line.
     * @param end
     *            The end point of the line.
     * @throws IllegalArgumentException
     *             in case an argument is <code>null</code>
     */
    public Line(Vector2D start, Vector2D end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start point of the line.
     *
     * @return Returns the start point of the line.
     */
    public Vector2D getStart() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return Returns the end point of the line.
     */
    public Vector2D getEnd() {
        return this.end;
    }

    /**
     * Returns the intersection of the line with the received line
     * <code>otherLine</code>.
     *
     * @param otherLine
     *            The received line whose intersection with the line is.
     *            calculated.
     * @return Returns the intersection of the line with the received line.
     *         <code>otherLine</code>.
     * @throws IllegalArgumentException
     *             In case <code>otherLine</code> is <code>null</code>.
     */
    public Vector2D getIntersectionPosition(Line otherLine) {
        if (otherLine == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        double[] x = getLambdas(otherLine);
        if (x == null) {
            return null;
        }
        if (!(x[0] < 0 || x[0] > 1 || x[1] < 0 || x[1] > 1)) {
            Vector2D directionalVector = this.getEnd().sub(this.getStart());
            Vector2D intersectionPoint = new Vector2D(
                    directionalVector.getX() * x[0],
                    directionalVector.getY() * x[0]);
            intersectionPoint = this.getStart().add(intersectionPoint);
            return intersectionPoint;
        }
        return null;
    }

    /**
     * Returns the intersection position of the line and a semi-infinite line
     * defined by <code>position</code> and a directional vector
     * <code>direction</code>.
     *
     * @param position
     *            the reference point of the infinite line.
     * @param direction
     *            the direction of the infinite line.
     * @return the intersection position.
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             <code>null</code>.
     */
    public Vector2D getIntersectionPosition(Vector2D position,
            Vector2D direction) {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        Line directionLine = new Line(position, position.add(direction));
        double[] x = getLambdas(directionLine);
        if (x == null) {
            return null;
        }
        if (!(x[0] < 0 || x[0] > 1 || x[1] < 0)) {
            Vector2D directionalVector = this.getEnd().sub(this.getStart());
            Vector2D intersectionPoint = new Vector2D(
                    directionalVector.getX() * x[0],
                    directionalVector.getY() * x[0]);
            intersectionPoint = this.getStart().add(intersectionPoint);
            return intersectionPoint;
        }
        return null;
    }
    
    /**
     * Returns the intersection position with a line and an infinite line
     * defined by <code>position</code> and a directional vector
     * <code>direction</code>.
     *
     * @param position
     *            the reference point of the infinite line.
     * @param direction
     *            the direction of the infinite line.
     * @return the intersection position.
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             <code>null</code>.
     */
    public Vector2D getIntersectionPositionInfiniteLine(Vector2D position,
            Vector2D direction) {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        Line directionLine = new Line(position, position.add(direction));
        double[] x = getLambdas(directionLine);
        if (x == null) {
            return null;
        }
        if (!(x[0] < 0 || x[0] > 1)) {
            Vector2D directionalVector = this.getEnd().sub(this.getStart());
            Vector2D intersectionPoint = new Vector2D(
                    directionalVector.getX() * x[0],
                    directionalVector.getY() * x[0]);
            intersectionPoint = this.getStart().add(intersectionPoint);
            return intersectionPoint;
        }
        return null;
    }

    /**
     * Checks, whether the line is intersecting the received line
     * <code>lineNew</code>.
     *
     * @param lineNew
     *            The received line whose intersection with the line is checked.
     * @return Returns <code>true</code>, if the line intersects the received
     *         line <code>lineNew</code>. Otherwise returns <code>false</code>.
     * @throws IllegalArgumentException
     *             In case <code>lineNew</code> is <code>null</code>.
     */
    public boolean isIntersecting(Line lineNew) {
        if (lineNew == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        double[] x = getLambdas(lineNew);
        if(isOverlapping(lineNew)) {
            return true;
        }
        if (x == null) {
            return false;
        }
        return !(x[0] < 0 || x[0] > 1 || x[1] < 0 || x[1] > 1);

    }

    private boolean isOverlapping(Line newLine) {
        Vector2D directionLine = getEnd().sub(getStart());
        Vector2D directionNewLine = newLine.getEnd().sub(newLine.getStart());
        directionLine = new Vector2D(directionLine.getX()/directionLine.getNorm(),
                directionLine.getY()/directionLine.getNorm());
        directionNewLine = new Vector2D(directionNewLine.getX()/directionNewLine.getNorm(),
                directionNewLine.getY()/directionNewLine.getNorm());     
        return directionLine.equals(directionNewLine.mult(-1)) && getEnd().equals(newLine.getStart());
    }

    private double[] getLambdas(Line lineNew) {
        Line line = this;
        Vector2D directionalVector = line.getEnd().sub(line.getStart());
        Vector2D directionalVectorNew = lineNew.getEnd()
                .sub(lineNew.getStart());
        double[][] matrix = new double[2][2];
        matrix[0][0] = directionalVector.getX();
        matrix[1][0] = directionalVector.getY();
        matrix[0][1] = -directionalVectorNew.getX();
        matrix[1][1] = -directionalVectorNew.getY();

        double[] b = new double[2];
        b[0] = lineNew.getStart().getX() - line.getStart().getX();
        b[1] = lineNew.getStart().getY() - line.getStart().getY();
        return lsolve(matrix, b);
    }

    /**
     * Returns the solution coefficients of the linear equation system
     * represented by the matrix <code>matrix</code> and the vector
     * <code>vector</code> using the Gaussian elimination algorithm.
     *
     * @param matrix
     *            The matrix corresponding to the linear equation system.
     * @param vector
     *            The vector corresponding to the linear equation system.
     * @return Returns the solution coefficients of the linear equation system.
     */
    private static double[] lsolve(double[][] matrix, double[] vector) {
        int dimension = vector.length;

        for (int p = 0; p < dimension; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < dimension; i++) {
                if (Math.abs(matrix[i][p]) > Math.abs(matrix[max][p])) {
                    max = i;
                }
            }
            double[] temp = matrix[p];
            matrix[p] = matrix[max];
            matrix[max] = temp;
            double t = vector[p];
            vector[p] = vector[max];
            vector[max] = t;

            // singular or nearly singular
            if (Math.abs(matrix[p][p]) <= EPSILON_GAUSS) {
                return null;

            }

            // pivot within matrix and vector
            for (int i = p + 1; i < dimension; i++) {
                double alpha = matrix[i][p] / matrix[p][p];
                vector[i] -= alpha * vector[p];
                for (int j = p; j < dimension; j++) {
                    matrix[i][j] -= alpha * matrix[p][j];
                }
            }
        }

        // back substitution
        double[] solutionVector = new double[dimension];
        for (int i = dimension - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < dimension; j++) {
                sum += matrix[i][j] * solutionVector[j];
            }
            solutionVector[i] = (vector[i] - sum) / matrix[i][i];
        }
        return solutionVector;
    }

}
