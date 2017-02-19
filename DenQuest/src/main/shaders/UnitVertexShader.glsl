#version 330         
                         
in vec2 in_vertex;                             
in vec2 in_tex_coord;    
                     
out vec2 tex_coord;         
      	
uniform vec2 unitPos;     
uniform float scale;                 
uniform vec4 rotate;   
uniform vec2 resolution;     
                  
void main(){                                 
    vec2 trans_vertex = vec2(in_vertex);
  
    // Rotate
  
  	// Scale  
    trans_vertex.x = trans_vertex.x * scale;
    trans_vertex.y = trans_vertex.y * scale; 
     
    // Translate
	float OldMaxX = resolution.x; 
	float OldMaxY = resolution.y; 
	float OldMinX = 0;
	float OldMinY = 0;                          
	float NewMaxX = 1; 
	float NewMaxY = 1; 
	float NewMinX = -1;
	float NewMinY = -1;
    float OldRangeX = (OldMaxX - OldMinX);
    float OldRangeY = (OldMaxY - OldMinY);
    float NewRangeX = (NewMaxX - NewMinX);  
	float NewRangeY = (NewMaxY - NewMinY);  
	float screenPosX = (((unitPos.x - OldMinX) * NewRangeX) / OldRangeX) + NewMinX;
    float screenPosY =(((unitPos.y - OldMinY) * NewRangeY) / OldRangeY) + NewMinY;
  	trans_vertex.x = trans_vertex.x + screenPosX;
	trans_vertex.y = trans_vertex.y + screenPosY;
    
    gl_Position = vec4(trans_vertex, 0, 1);        
       
    tex_coord = in_tex_coord;                  		
} 