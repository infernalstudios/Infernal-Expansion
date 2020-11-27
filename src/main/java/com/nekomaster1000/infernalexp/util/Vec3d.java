package com.nekomaster1000.infernalexp.util;

/**
 * Creates a relative vector from 0,0,0 to x,y,z
 * @author SwanX1
 */
public class Vec3d {
	public float x;
	public float y;
	public float z;
	
	public Vec3d(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return Returns vector length
	 */
	public float length() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
	}
	
	/**
	 * Add vector v and returns result as new vector.
	 * @param v vector to add
	 * @return result as new vector
	 */
	public Vec3d add(Vec3d v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return new Vec3d(this.x + v.x, this.y + v.y, this.z + v.z);
	}
	
	public String toString() {
		return String.format("%s, %s, %s", this.x, this.y, this.z);
	}
	
	public String toIntString() {
		return String.format("%s, %s, %s", (int) this.x, (int) this.y, (int) this.z);
	}
}