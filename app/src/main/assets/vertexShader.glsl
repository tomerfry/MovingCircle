uniform mat4 uMVPMatrix;
uniform vec4 modelPos;
attribute vec4 vPosition;
void main() {
  gl_Position = uMVPMatrix * (vPosition + modelPos);
}