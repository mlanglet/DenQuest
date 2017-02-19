#version 330  
                       
in vec2 in_vertex; 
in vec2 in_tex_coord;                

out vec2 tex_coord;  
                   
void main(){                              
    gl_Position = vec4(in_vertex, 0, 1);  
    tex_coord = in_tex_coord;                 
}
