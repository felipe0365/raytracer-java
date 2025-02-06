package br.com.compassuol.scene;

import lombok.Value;

import java.util.List;

@Value
public class Scene {

    Vector3 camera;
    ImagePlane imagePlane;
    Color ambientLight;
    List<Light> lights;
    List<SceneObject> objects;
}
