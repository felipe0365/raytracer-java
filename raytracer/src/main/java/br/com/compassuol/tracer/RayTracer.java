package br.com.compassuol.tracer;

import br.com.compassuol.scene.*;
import lombok.Value;

import java.util.Optional;

@Value
public class RayTracer {
    private static final int NUM_BOUCES = 3;

    Scene scene;
    int w;
    int h;

    public Color tracedValueAtPixel(int x, int y) {
        float xt = ((float) x) / w;
        float yt = ((float) h - y - 1) / h;

        Vector3 top = Vector3.lerp(
                scene.getImagePlane().getTopLeft(),
                scene.getImagePlane().getTopRight(),
                xt
        );

        Vector3 bottom = Vector3.lerp(
                scene.getImagePlane().getBottomLeft(),
                scene.getImagePlane().getBottomRight(),
                xt
        );

        Vector3 point = Vector3.lerp(bottom, top, yt);
        Ray ray = new Ray(
                point,
                point.minus(scene.getCamera())
        );

        return colorFromAnyObjectHit(NUM_BOUCES, ray).claped();
    }

    private Color colorFromAnyObjectHit(int numBouces,Ray ray) {
        return scene
                .getObjects()
                .stream()
                .map(obj -> obj
                        .earliestIntersection(ray)
                        .map(t -> new RayCastHit(obj, t,obj.normalAt(ray.at(t)))))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min((h0, h1) -> (int) Math.signum(h0.getT() - h1.getT()))
                .map(hit -> {
                    Vector3 point = ray.at(hit.getT());
                    Vector3 view = ray.getDirection().inverted().normalized();

                    Color color = phongLightAtPoint(
                            scene,
                            hit.getObject(),
                            point,
                            hit.getNormal(),
                            view
                    );

                    if (numBouces > 0) {
                        Vector3 reflection = hit.getNormal()
                                .times(view.dot(hit.getNormal()))
                                .times(2)
                                .minus(view);

                        Color reflectedColor = colorFromAnyObjectHit(
                                numBouces - 1,
                                new Ray(point, reflection)
                        );

                        color = color.plus(
                                reflectedColor.times(
                                        hit
                                                .getObject()
                                                .getMaterial()
                                                .getKReflection()
                                )
                        );

                    }

                    return color;

                })
                .orElse(Color.BLACK);
    }

    private Color phongLightAtPoint(Scene scene, SceneObject object, Vector3 point, Vector3 normal, Vector3 view) {
        Material material = object.getMaterial();

        Color lightContributions = scene
                .getLights()
                .stream()
                .filter(light ->
                        light.getPosition().minus(point).dot(normal) > 0)
                .filter(light ->
                        !isPointInShadowFromLight(scene, object, point, light))
                .map(light -> {
                    Vector3 l = light.getPosition().minus(point).normalized();
                    Vector3 r = normal.times(l.dot(normal) * 2).minus(l);

                    Color diffuse = light
                            .getIntensityDiffuse()
                            .times(material.getKDiffuse())
                            .times(l.dot(normal));

                    Color specular = light
                            .getIntensitySpecular()
                            .times(material.getKSpecular())
                            .times((float) Math.pow(
                                    r.dot(view), material.getAlpha()
                            ));
                    return diffuse.plus(specular);
                })
                .reduce(Color.BLACK, Color::plus);

        Color ambient = material
                .getKAmbient()
                .times(scene.getAmbientLight());
        return ambient.plus(lightContributions);
    }

    private boolean isPointInShadowFromLight(Scene scene, SceneObject objectToExclude, Vector3 point, Light light) {
        Vector3 direction = light.getPosition().minus(point);
        Ray shadowRay = new Ray(point, direction);

        return scene
                .getObjects()
                .stream()
                .filter(obj -> obj != objectToExclude)
                .map(obj -> obj
                        .earliestIntersection(shadowRay)
                        .map(t -> new RayCastHit(
                                obj, t, null)
                        ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(hit -> hit.getT() <= 1);

    }
}
