package br.com.compassuol.tracer;

import br.com.compassuol.scene.Vector3;
import lombok.Value;

@Value
public class Ray {

    Vector3 origin;
    Vector3 direction;
}
