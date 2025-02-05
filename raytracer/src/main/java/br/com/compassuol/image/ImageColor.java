package br.com.compassuol.image;

import lombok.Value;

@Value
public class ImageColor {

    public static final int MAX = 0xff;

    int r;
    int g;
    int b;
}
