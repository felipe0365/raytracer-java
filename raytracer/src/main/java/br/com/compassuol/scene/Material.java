package br.com.compassuol.scene;

import lombok.Value;

@Value
public class Material {

    Color kAmbient;
    Color kDiffuse;
    Color kSpecular;
    Color kReflection;
    Integer alpha;
}
