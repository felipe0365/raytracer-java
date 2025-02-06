package br.com.compassuol.scene;

import lombok.Value;

@Value
public class Vector3 {

    float x, y, z;

    private  Vector3 times(float scalar) {
        return new Vector3(scalar * this.x, scalar * this.y, scalar * this.z);
    }

    private Vector3 plus(Vector3 other) {
        return new Vector3(x + other.x,y + other.y, z + other.z);
    }

    public Vector3 minus(Vector3 other) {
        return new Vector3(x - other.x,y - other.y, z - other.z);
    }

    public static Vector3 lerp(Vector3 start, Vector3 end, float t) {
        return start.times(1 - t).plus(end.times(t));
    }
}
