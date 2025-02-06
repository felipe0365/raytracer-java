package br.com.compassuol.tracer;

import br.com.compassuol.scene.Color;
import br.com.compassuol.scene.Scene;
import br.com.compassuol.scene.Vector3;
import lombok.Value;

@Value
public class RayTracer {

    Scene scene;
    Integer w;
    Integer h;

    public Color tracedValueAtPixel(int x, int y){
        float xt = ((float) x) / w;
        float yt = ((float) h - y - 1) / h;

        Vector3 top  = Vector3.lerp(
                scene.getImagePlane().getTopLeft(),
                scene.getImagePlane().getTopRight(),
                xt
        );

        Vector3 bottom = Vector3.lerp(
                scene.getImagePlane().getBottomLeft(),
                scene.getImagePlane().getBottomRight(),
                xt
        );

        Vector3 point = Vector3.lerp(bottom, top, xt);
        Ray ray  = new Ray(
                point, point.minus(scene.getCamera())
        );

        return new Color(
                (ray.getDirection().getX() + 1.92f) / 3.84f,
                (ray.getDirection().getY() + 1.08f) / 2.16f,
                ray.getDirection().getZ() / -4
        );
    }
}
