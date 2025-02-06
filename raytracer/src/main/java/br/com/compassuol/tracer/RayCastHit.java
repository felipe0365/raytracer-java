package br.com.compassuol.tracer;

import br.com.compassuol.scene.SceneObject;
import lombok.Value;

@Value
public class RayCastHit {

    SceneObject object;
    Float t;
}
