package br.com.compassuol.tracer;

import br.com.compassuol.scene.SceneObject;
import br.com.compassuol.scene.Vector3;
import lombok.Value;

@Value
public class RayCastHit {

    SceneObject object;
    Float t;
    Vector3 normal;
}
