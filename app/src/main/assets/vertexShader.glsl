attribute vec4 position;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
  gl_Position = projectionMatrix * viewMatrix * transformationMatrix * position;
}