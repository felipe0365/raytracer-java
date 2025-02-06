package br.com.compassuol.scene;

import br.com.compassuol.tracer.Ray;

import java.util.Optional;

public interface SceneObject {

    Material getMaterial();
    Optional<Float> earliestIntersection(Ray ray);
}
