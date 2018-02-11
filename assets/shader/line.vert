#version 120

attribute vec2 pos;
attribute float weight;

varying float v_weight;

void main() {
    v_weight = weight;
	gl_Position = vec4(pos,0,1);
}
