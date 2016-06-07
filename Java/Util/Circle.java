package de.uni_passau.sep.portalgolf.util;

import java.io.Serializable;

import de.uni_passau.sep.portalgolf.SystemConstants;

/**
 *
 * The class Circle represents a Circle with a position and a radius, which can
 * calculate intersecting points with a infinite line.
 *
 */
public class Circle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3567168159544161816L;
    private double radius;
    private Vector2D position;
    private static final double EPSILON_GAUSS = 1e-10;

    /**
     * Creates a new Circle-Objects with a position and a radius.
     *
     * @param position
     *            The position of the ball.
     * @param radius
     *            The radius of the ball.
     * @throws IllegalArgumentException
     *             In case <code>position</code> is null
     */
    public Circle(Vector2D position, double radius)
            throws IllegalArgumentException {
        if (position == null) {
            throw new IllegalArgumentException("Position is null.");
        }
        if (Math.abs(radius) < SystemConstants.EPSILON
                || radius < -SystemConstants.EPSILON) {
            throw new IllegalArgumentException("Radius is zero or negative.");
        }
        this.position = position;
        this.radius = radius;
    }

    /**
     * Returns the position of the circle.
     *
     * @return position of the circle
     */
    public Vector2D getPosition() {
        return this.position;
    }

    /**
     * Returns the radius of the circle.
     *
     * @return radius of the circle
     */
    public double getRadius() {
        return this.radius;
    }

    public boolean containsPoint(Vector2D point)
            throws IllegalArgumentException {
        if (point == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return (this.radius
                - this.position.sub(point).getNorm() > SystemConstants.EPSILON);
    }

    /**
     * Checks, whether the infinite line defined by the reference point
     * <code>position</code> and the direction vector <code>direction</code>
     * intersects the circle. In case of two intersections, pick the one which
     * is nearer to <code>position</code>.
     *
     * @param position
     *            The position of the reference point to define the infinite
     *            line
     * @param direction
     *            The direction of the infinite line
     * @return Returns <code>true</code>, if the infinite line is intersecting
     *         the circle. Otherwise returns <code>false</code>.
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             null.
     */
    public boolean isIntersecting(Vector2D position, Vector2D direction)
            throws IllegalArgumentException {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (direction.getNorm() < SystemConstants.EPSILON) {
            throw new IllegalArgumentException(
                    "The norm of the direction" + " vector is zero.");
        }

        double[] coefficients = getCoefficients(position, direction);
        double my = coefficients[0];

        // Determine the intersection between the infinite line and the normal
        Vector2D intersectionLines = position.add(direction.mult(my));
        double distanceIntersectionToCentre = this.position
                .sub(intersectionLines).getNorm();
        return !(distanceIntersectionToCentre
                - this.radius > SystemConstants.EPSILON);
    }

    /**
     * Checks, whether the semi-infinite line defined by the reference point
     * <code>position</code> and the direction vector <code>direction</code>
     * intersects the circle. In case of two intersections, pick the one which
     * is nearer to <code>position</code>.
     *
     * @param position
     *            The position of the reference point to define the infinite
     *            line
     * @param direction
     *            The direction of the infinite line
     * @return Returns <code>true</code>, if the infinite line is intersecting
     *         the circle. Otherwise returns <code>false</code>.
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             null.
     */
    public boolean isIntersectingWithSemiInfiniteLine(Vector2D position,
            Vector2D direction) throws IllegalArgumentException {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (direction.getNorm() < SystemConstants.EPSILON) {
            throw new IllegalArgumentException(
                    "The norm of the direction" + " vector is zero.");
        }

        double[] coefficients = getCoefficients(position, direction);
        double my = coefficients[0];

        // Check, whether the circle lies "behind" the semi-infinite line
        if (my < 0) {
            return false;
        }

        // Determine the intersection between the infinite line and the normal
        Vector2D intersectionLines = position.add(direction.mult(my));
        double distanceIntersectionToCentre = this.position
                .sub(intersectionLines).getNorm();
        return !(distanceIntersectionToCentre
                - this.radius > SystemConstants.EPSILON);
    }

    /**
     * Returns the position of the intersection with a infinite line defined by
     * <code>position</code> and <code>direction</code>. Returns null if there's
     * no intersection. In case of two intersections, pick the one which is
     * nearer to <code>position</code>.
     *
     * @param position
     *            The position of the reference point to define the infinite
     *            line
     * @param direction
     *            the direction of the infinite line
     * @return Returns the position of the intersection
     *
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             <code>null</code>.
     */
    public Vector2D getIntersectionPosition(Vector2D position,
            Vector2D direction) throws IllegalArgumentException {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (direction.getNorm() < SystemConstants.EPSILON) {
            throw new IllegalArgumentException(
                    "The norm of the direction" + " vector is zero.");
        }
        if (!isIntersecting(position, direction)) {
            return null;
        } else {
            return getIntersection(position, direction);
        }
    }

    /**
     * Returns the position of the intersection with a semi-infinite line
     * defined by <code>position</code> and <code>direction</code>. Returns null
     * if there's no intersection. In case of two intersections, pick the one
     * which is nearer to <code>position</code>.
     *
     * @param position
     *            The position of the reference point to define the infinite
     *            line
     * @param direction
     *            the direction of the infinite line
     * @return Returns the position of the intersection
     *
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             <code>null</code>.
     */

    public Vector2D getIntersectionPositionWithSemiInfiniteLine(
            Vector2D position, Vector2D direction)
                    throws IllegalArgumentException {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (direction.getNorm() < SystemConstants.EPSILON) {
            throw new IllegalArgumentException(
                    "The norm of the direction" + " vector is zero.");
        }
        if (!isIntersectingWithSemiInfiniteLine(position, direction)) {
            return null;
        } else {
            return getIntersection(position, direction);
        }
    }

    /**
     * This helper method calculates the nearest intersection with an infinite
     * resp. semi-infinite line defined by <code>position</code> and
     * <code>direction</code>, if it has been already assured that there is at
     * least one intersection.
     *
     * @param position
     *            The position of the reference point to define the infinite
     *            line.
     * @param direction
     *            The direction of the infinite line.
     * @return Returns the position of the intersection.
     *
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             <code>null</code>.
     */
    private Vector2D getIntersection(Vector2D position, Vector2D direction)
            throws IllegalArgumentException {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (direction.getNorm() < SystemConstants.EPSILON) {
            throw new IllegalArgumentException(
                    "The norm of the direction" + " vector is zero.");
        }
        double[] coefficients = getCoefficients(position, direction);
        double my = coefficients[0];

        /*
         * Determine the intersection between the infinite line and the normal
         */
        Vector2D intersectionLines = position.add(direction.mult(my));
        double distanceIntersectionToCentre = this.position
                .sub(intersectionLines).getNorm();

        /*
         * Check, whether the infinite line is a central secant, a non-central
         * secant or a tangent to the circle and return the corresponding
         * intersection
         */
        if (Math.abs(distanceIntersectionToCentre) < SystemConstants.EPSILON) {
            Vector2D normalizedDirection = direction
                    .mult(1 / direction.getNorm());
            Vector2D pointOne = this.position
                    .add(normalizedDirection.mult(this.radius));
            Vector2D pointTwo = this.position
                    .sub(normalizedDirection.mult(this.radius));
            double distanceToPointOne = position.sub(pointOne).getNorm();
            double distanceToPointTwo = position.sub(pointTwo).getNorm();
            if (distanceToPointOne
                    - distanceToPointTwo > SystemConstants.EPSILON) {
                return pointTwo;
            } else {
                return pointOne;
            }
        } else if (this.radius
                - distanceIntersectionToCentre > SystemConstants.EPSILON) {

            // Use the theorem of Pythagoras
            double scalar = Math.sqrt(
                    this.radius * this.radius - distanceIntersectionToCentre
                            * distanceIntersectionToCentre);
            Vector2D normalizedDirection = direction
                    .mult(1 / direction.getNorm());
            Vector2D pointOne = intersectionLines
                    .add(normalizedDirection.mult(scalar));
            Vector2D pointTwo = intersectionLines
                    .sub(normalizedDirection.mult(scalar));
            double distanceToPointOne = position.sub(pointOne).getNorm();
            double distanceToPointTwo = position.sub(pointTwo).getNorm();
            if (distanceToPointOne
                    - distanceToPointTwo > SystemConstants.EPSILON) {
                return pointTwo;
            } else {
                return pointOne;
            }
        } else if (Math.abs(distanceIntersectionToCentre
                - this.radius) < SystemConstants.EPSILON) {
            return intersectionLines;
        } else {
            return null;
        }
    }

    /**
     * This helper method calculates the coefficients of the linear equation
     * system according to the intersection of the infinite line defined by the
     * reference point <code>position</code> and the direction vector
     * <code>direction</code> with the according perpendicular infinite line
     * through the centre of the circle.
     *
     * @param position
     *            The position of the reference point to define the infinite
     *            line
     * @param direction
     *            The direction of the infinite line
     * @return Returns the coefficients of the linear equation system
     * @throws IllegalArgumentException
     *             In case <code>position</code> or <code>direction</code> is
     *             null.
     */
    private double[] getCoefficients(Vector2D position, Vector2D direction)
            throws IllegalArgumentException {
        if (position == null || direction == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (direction.getNorm() < SystemConstants.EPSILON) {
            throw new IllegalArgumentException(
                    "The norm of the direction" + " vector is zero.");
        }
        double posX = position.getX();
        double posY = position.getY();
        double dirX = direction.getX();
        double dirY = direction.getY();
        double circleX = this.position.getX();
        double circleY = this.position.getY();

        // Determine the direction of the perpendicular infinite line
        Vector2D perpendicularDirection = new Vector2D(-dirY, dirX);
        double perDirX = perpendicularDirection.getX();
        double perDirY = perpendicularDirection.getY();

        /*
         * Define the matrix and the vector that represent the linear equation
         * system
         */
        double[][] matrix = new double[2][2];
        matrix[0][0] = dirX;
        matrix[1][0] = dirY;
        matrix[0][1] = -perDirX;
        matrix[1][1] = -perDirY;
        double[] b = new double[2];
        b[0] = circleX - posX;
        b[1] = circleY - posY;
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
