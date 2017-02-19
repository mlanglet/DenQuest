#version 330 
                                                   
in vec2 tex_coord;     
                                        
out vec4 out_color;
                                              
uniform sampler2D tex;
uniform vec2 texture_offset;
                                        
void main(){           
    out_color = texture(tex, vec2(tex_coord.x + texture_offset.x, 
                                  tex_coord.y + texture_offset.y)).rgba;
}