# Task 3

---

## Explanation of the .obj entries v, vt, vn and f:

### v:
Defines a 3D point (x, y, z) in space – typically a corner of a shape like a cube.


### vt:
Defines a 2D coordinate (u, v) for mapping a texture; values are between 0 and 1.


### vn:
Specifies a 3D direction vector (x, y, z) that is perpendicular to a surface (normal vector) – used for lighting 
calculations.


### f:
Defines a triangular or rectangular surface, based on v, vt and vn.  
Entry example:  

`f 1/11/111 2/22/222 3/33/333`

means:  
This surface is based on...
- v values 1, 2, 3
- vt values 11, 22, 33
- vn values 111, 222, 333

`f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3`


---
 ## Explanation of the exact values found in earth.obj:
### v = 8:
The model has 8 unique 3D vertices – The corners of a cube or a simplified Earth model.

### vt = 14:
14 texture coordinates are used to allow multiple mappings of textures to the same vertex from different faces.

### vn = 6:
6 normal vectors, one for each face of the cube – representing direction for lighting calculations.

### f = 12:
12 faces are defined – consistent with a cube made of 6 sides, each split into 2 triangles (6 × 2 = 12).