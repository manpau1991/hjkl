package de.uni_passau.sep.portalgolf.util;

import java.io.Serializable;

import javax.naming.OperationNotSupportedException;

import de.uni_passau.sep.portalgolf.SystemConstants;

/**
 * This class represents a two dimensional vector. The coordinates can only be
 * set in the constructor and cannot be changed afterwards.
 */
public class Vector2D implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -2272516380831584070L;

    /**
     * The x coordinate of the vector.
     */
    private final double x;

    /**
     * The y coordinate of the vector.
     */
    private final double y;

    /**
     * Constructor to initialize the vector.
     *
     * @param x
     *            The x coordinate
     * @param y
     *            The y coordinate
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x coordinate of the vector.
     *
     * @return The x coordinate of the vector
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get y coordinate of the vector.
     *
     * @return The y coordinate of the vector
     */
    public double getY() {
        return this.y;
    }

    /**
     * Add a vector to this vector.
     *
     * @param vec
     *            The vector to be added to this vector
     * @return A new vector containing the sum
     * @throws IllegalArgumentException
     *             in case <code>vec</code> is <code>null</code>
     */
    public Vector2D add(Vector2D vec) throws IllegalArgumentException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return new Vector2D(this.x + vec.x, this.y + vec.y);
    }

    /**
     * Subtract a vector from this vector.
     *
     * @param vec
     *            The vector to be subtracted from this vector
     * @return A new vector containing the difference
     * @throws IllegalArgumentException
     *             in case <code>vec</code> is <code>null</code>
     */
    public Vector2D sub(Vector2D vec) throws IllegalArgumentException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return new Vector2D(this.x - vec.x, this.y - vec.y);
    }

    /**
     * Multiply this vector with a scalar factor.
     *
     * @param factor
     *            The factor to multiply this vector with
     * @return A new vector containing the result
     */
    public Vector2D mult(double factor) {
        return new Vector2D(this.x * factor, this.y * factor);
    }

    /**
     * Calculate the Scalar product of two vectors.
     *
     * @param vector
     *            the other vector
     * @return the scalar product
     * @throws IllegalArgumentException
     *             in case <code>vec</code> is <code>null</code>
     */
    public double scalarProduct(Vector2D vec) throws IllegalArgumentException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return this.getX() * vec.getX() + this.getY() * vec.getY();
    }

    /**
     * Calculate the norm of the vector.
     *
     * @return The norm of the vector
     */
    public double getNorm() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    /**
     * Calculates the angle between the calling vector and the vector
     * <code>vec</code> in degrees.
     * 
     * @param vec
     *            The received vector.
     * @return Returns the angle between the calling vector and the received
     *         vector.
     * @throws IllegalArgumentException
     *             In case <code>vec</code> is <code>null</code>.
     * @throws OperationNotSupportedException
     *             In case the calling vector or the vector <code>vec</code> is
     *             the zero vector.
     */
    public double getAngle(Vector2D vec) throws IllegalArgumentException,
        OperationNotSupportedException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        double normOne = this.getNorm();
        double normTwo = vec.getNorm();
        if (normOne < SystemConstants.EPSILON
                || normTwo < SystemConstants.EPSILON) {
            throw new OperationNotSupportedException("The zero vector is"
                    + "involved!");
        }
        double scalarProduct = this.scalarProduct(vec);
        return (Math.acos(scalarProduct / (normOne * normTwo)) * 180) / Math.PI;
    }
    
    /**
     * Returns the vector rotated clockwise by the received angle in degrees, if
     * the received angle is positive. If the received angle is negative, the
     * rotation will be performed anti-clockwise. If the absolute value of the
     * angle is greater or equal to 360°, there will be an periodic rotation.
     * 
     * @param angleDegrees
     *            The received angle in degrees.
     * @return Returns the vector rotated clockwise by the received angle.
     */
    public Vector2D rotateClockwise(double angleDegrees) {
        
        // Check if the calling vector is the zero vector
        if (this.getNorm() < SystemConstants.EPSILON) {
            return this;
        }
        
        // Determine the angle in radians
        double angleRadians = (angleDegrees * Math.PI) / 180;
        
        /*
         * Determine the coordinates of the rotated vector by mapping with a
         * two-dimensional rotational matrix
         */
        double newX = this.getX() * Math.cos(angleRadians)
                - this.getY() * Math.sin(angleRadians);
        double newY = this.getX() * Math.sin(angleRadians)
                + this.getY() * Math.cos(angleRadians);
        return new Vector2D(newX, newY);
    }

    /**
     * Returns the String of a vector.
     *
     * @return String of the vector
     */
    @Override
    public String toString() {
        String vectorString = "(" + this.x + ", " + this.y + ")";
        return vectorString;
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		return (Math.abs(((Vector2D) o).x - this.x) < SystemConstants.EPSILON 
                && Math.abs(((Vector2D) o).y - this.y) < SystemConstants.EPSILON);
	}
}
