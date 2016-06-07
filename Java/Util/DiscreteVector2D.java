package de.uni_passau.sep.portalgolf.util;

import java.io.Serializable;

/**
 * This class represents a two dimensional discrete vector. The coordinates can
 * only be set in the constructor and cannot be changed afterwards.
 */
public class DiscreteVector2D implements Serializable {

	/**
     * The serial version UID
     */
    private static final long serialVersionUID = -2016834924034777368L;

    /**
     * The x coordinate of the vector
     */
    private final int x;

    /**
     * The y coordinate of the vector
     */
    private final int y;

    /**
     * Constructor to initialize the vector.
     *
     * @param x
     *            The x coordinate
     * @param y
     *            The y coordinate
     */
    public DiscreteVector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x coordinate of the vector
     *
     * @return The x coordinate of the vector
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get y coordinate of the vector
     *
     * @return The y coordinate of the vector
     */
    public int getY() {
        return this.y;
    }

    /**
     * Add a vector to this vector
     *
     * @param vec
     *            The vector to be added to this vector
     * @return A new vector containing the sum
     * @throws IllegalArgumentException
     *             in case <code>vec</code> is <code>null</code>
     */
    public DiscreteVector2D add(DiscreteVector2D vec) throws IllegalArgumentException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return new DiscreteVector2D(this.x + vec.x, this.y + vec.y);
    }

    /**
     * Subtract a vector from this vector
     *
     * @param vec
     *            The vector to be subtracted from this vector
     * @return A new vector containing the difference
     * @throws IllegalArgumentException
     *             in case <code>vec</code> is <code>null</code>
     */
    public DiscreteVector2D sub(DiscreteVector2D vec) throws IllegalArgumentException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return new DiscreteVector2D(this.x - vec.x, this.y - vec.y);
    }

    /**
     * Multiply this vector with a scalar factor
     *
     * @param factor
     *            The factor to multiply this vector with
     * @return A new vector containing the result
     */
    public DiscreteVector2D mult(int factor) {
        return new DiscreteVector2D(this.x * factor, this.y * factor);
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
    public double scalarProduct(DiscreteVector2D vec) throws IllegalArgumentException {
        if (vec == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return this.getX() * vec.getX() + this.getY() * vec.getY();
    }

    /**
     * Calculate the norm of the vector
     *
     * @return The norm of the vector
     */
    public double getNorm() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
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
		result = prime * result + this.x;
		result = prime * result + this.y;
		return result;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiscreteVector2D other = (DiscreteVector2D) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		return true;
	}
}
