package br.com.compassuol.scene;

import lombok.Value;

@Value
public class Color {
    public static final Color BLACK = new Color(0, 0, 0);

    float r, g, b;
}
