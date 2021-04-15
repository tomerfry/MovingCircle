uniform mat4 uMVPMatrix;
uniform mat4 translationMatrix;
attribute vec4 vPosition;
void main() {
  gl_Position = uMVPMatrix * translationMatrix * vPosition;
}