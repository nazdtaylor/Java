package HelloOpenGL;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.nio.IntBuffer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import java.io.IOException;
import java.io.File; 

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.Line; 


public class JoglEventListener implements GLEventListener, MouseListener, MouseMotionListener/*, KeyListener*/ {
	
	float backrgb[] = new float[4]; 
	float rot; 
	
	boolean hit_backboard;
	boolean hit_ground;
	boolean hit_left_rim;
	boolean hit_right_rim;
	boolean hit_shot;
	boolean soundPlayed;
	boolean override = false;
	boolean bank_shot;
	
	boolean replay_hit_backboard;
	boolean replay_hit_ground;
	boolean replay_hit_left_rim;
	boolean replay_hit_right_rim;
	boolean replay_hit_shot;
	boolean replay = false;
	boolean replay_shot = false;
	int replayMode = 0;
	boolean replayMotion;
	long replay_start;
	long replay_init;
	
	boolean freelook = false;
	
	float time0, time1;
	
	int bounce_counter=0;
	
	private GLModel goalModel = null;
	
	int windowWidth, windowHeight;
	float orthoX=40;

	int mouseX0, mouseY0;	
	
	boolean inMotion = false, velocitySet = false;
	
	boolean replayVelocitySet = false;
	
	long start;
	
	int mode;
	
	float d = 0;
	
	float picked_x = 0.0f, picked_y = 0.0f;
	
	float initialX = 0, initialY = 0;
	
	float theta = 0; 
	float replay_theta;
	
	float phi = 0;
	float replay_phi;
	
	int replaySpeed;
	
	float V_init;
	float replay_V;
	
	float Vx_init, Vy_init, Vz_init;
	float Rx_init, Ry_init, Rz_init;
	
	float Vy_final;
	float Ry_final;
	
	float currentX, currentY, currentDistance = 0;
	
	float x = 0, y = 0, z = 0;
	
	float focalLength = 30.0f;
	
	float goalZ = 0;
	
	Texture mytex = null; 

	Texture mytex1 = null; 
	
	Texture mytex2 = null; 
	
    private GLU glu = new GLU();
    	
    public void drawSphere(final GL2 gl){
    		
        float x,y,z;  
        
        gl.glEnable(GL.GL_TEXTURE_2D);	        
        
        mytex1.bind(gl);
        
        float tx; 
        float ty; 
        
        gl.glBegin(GL2.GL_LINE_STRIP);
        for(float i = 0; i < 360; i += 0.5)
        	for(float j = 0; j < 180; j += 0.5){
        		
        		x=(float) (.35*Math.cos(Math.PI*i/180)*Math.sin(Math.PI*j/180));
        	    y=(float) (.35*Math.sin(Math.PI*i/180)*Math.sin(Math.PI*j/180));
        	    z=(float) (.35*Math.cos(Math.PI*j/180));
        	    
        	    tx = (float) ((Math.sin(Math.PI*i/180)/Math.PI)+0.5);
        	    ty = (float) ((Math.sin(Math.PI*j/180)/Math.PI)+0.5);
        	    
        	    gl.glColor3f(1.0f, 1.0f, 1.0f);
        	    gl.glTexCoord2f(tx,ty);
    	        gl.glVertex3f(x,y,z);  
        	}
        gl.glEnd();
        gl.glDisable(GL.GL_TEXTURE_2D);
   	}
  
	 public void displayChanged(GLAutoDrawable gLDrawable, 
	            boolean modeChanged, boolean deviceChanged) {
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        //gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        // Really Nice Perspective Calculations
	        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	        
	        mode = 0;
	        
	        // load the texture;
	        
	        try {
	        	 //mytex = TextureIO.newTexture(new File("C:/Users/ruigang/workspace/helloOpenGL/hp.png"), false);
	        	 mytex = TextureIO.newTexture(new File("/Users/abdelhakmoustafa/Desktop/images/court.png"), false);
	        	 int texID = mytex.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             //mytex.bind(gl);
	             //gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	        	 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE);
	             
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        
	        try {
	        	 //mytex = TextureIO.newTexture(new File("C:/Users/ruigang/workspace/helloOpenGL/hp.png"), false);
	        	 mytex1 = TextureIO.newTexture(new File("/Users/abdelhakmoustafa/Desktop/images/B.jpg"), false);
	        	 int texID = mytex1.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             //mytex.bind(gl);
	             //gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	        	 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE);
	             
	        } catch (Exception ex) {
	            ex.printStackTrace();    
	        }
	        
	        try {
	        	 //mytex = TextureIO.newTexture(new File("C:/Users/ruigang/workspace/helloOpenGL/hp.png"), false);
	        	 mytex2 = TextureIO.newTexture(new File("/Users/abdelhakmoustafa/Desktop/images/wall.jpg"), false);
	        	 int texID = mytex2.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             //mytex.bind(gl);
	             //gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	        	 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE);
	             
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        
	        if (false == loadModels(gl)) {
				System.exit(1);
			}

	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	        final GL2 gl = gLDrawable.getGL().getGL2();
	        
	        windowWidth = width;
	        windowHeight = height;

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	        glu.gluPerspective(45.0f, h, 1.0, 10000.0);
	        
	        glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);
	        
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        
	        //gl.glLoadIdentity();
	     
	    }
	    public static void play(File Filename) throws Exception 

	    { 
	        File soundFile; 
	        Clip clip; 
	        soundFile = Filename; 

	        System.out.println("Playing " + soundFile.getName()); 

	        Line.Info linfo = new Line.Info(Clip.class); 
	        Line line = AudioSystem.getLine(linfo); 
	        clip = (Clip) line; 
	        AudioInputStream ais = AudioSystem.getAudioInputStream( soundFile ); 
	        clip.open(ais); 
	        clip.start(); 
	    } 
	    	
	    	
		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			//backrgb[0]+=0.0005;
			if (backrgb[0]> 1) backrgb[0] = 0; 
			
			
			gl.glLoadIdentity();			                   	  	             
			
	                             
                        
            // currentY & currentX are equal to the distance moved by the mouse
            // in the x and y directions, respectively.
                  
         if(freelook == true){
            if(Math.abs(currentY) > Math.abs(currentX)){
            	phi = currentY*10;
				gl.glRotatef(-1*phi, 1, 0, 0);
				gl.glRotatef(theta,0, 1, 0);
			}else{
				theta = currentX*10;
				gl.glRotatef(theta,0, 1, 0);
				gl.glRotatef(phi, 1, 0, 0);
			}              
         }
            gl.glRotatef(-1*phi, 1, 0, 0);
			gl.glRotatef(theta,0, 1, 0);
			
			
			gl.glEnable(GL.GL_TEXTURE_2D);	        
	        
            mytex2.bind(gl);
	                    
	        
	        // walls
	        
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glTexCoord2f(0,1); 
	        gl.glVertex3f(-100.0f, -100.0f, -100.0f);    
	         	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,1); 
	        gl.glVertex3f(100.0f, -100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f); 
	        gl.glTexCoord2f(1,0); 
	        gl.glVertex3f(100.0f, 100.0f, -100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-100.0f, 100.0f, -100.0f);   
	        gl.glEnd(); 
	        
	                   
	        
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glTexCoord2f(0,1);
	        gl.glVertex3f(-100.0f, -100.0f, 100.0f);    
	         	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,1);
	        gl.glVertex3f(100.0f, -100.0f, 100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f); 
	        gl.glTexCoord2f(1,0);
	        gl.glVertex3f(100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f); 
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-100.0f, 100.0f, 100.0f);   
	        gl.glEnd();	        	        
	        
	        
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glTexCoord2f(0,1);
	        gl.glVertex3f(-100.0f, -100.0f, -100.0f);    
	        	        
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,1);
	        gl.glVertex3f(-100.0f, 100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f); 
	        gl.glTexCoord2f(1,0);
	        gl.glVertex3f(-100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(-100.0f, -100.0f, 100.0f); 
	        gl.glEnd();
	        
	      //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glTexCoord2f(0,1);
	        gl.glVertex3f(100.0f, -100.0f, -100.0f);    
	         
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,1);
	        gl.glVertex3f(100.0f, 100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);   
	        gl.glTexCoord2f(1,0);
	        gl.glVertex3f(100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);  
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(100.0f, -100.0f, 100.0f);   
	        gl.glEnd();	
	        
	        gl.glDisable(GL.GL_TEXTURE_2D);
	        

            gl.glPushMatrix();
            gl.glRotatef(90,0,1,0);
            gl.glTranslatef((float) 18,(float) 1,(float) (goalZ*.8));
			gl.glScalef(0.05f, 0.05f, 0.05f);
			goalModel.opengldraw(gl);
            gl.glPopMatrix();
            
            
            // set the initial position of the camera.
            
            gl.glTranslatef(x-2, -5, 20);
            /*
            if (replay == true || override == true){

            	replay_shot = true;
	        	}
	        	
	        	
	        	
 */
            gl.glEnable(GL.GL_TEXTURE_2D);	        
	        
            mytex.bind(gl);
            
            //gl.glDisable(GL.GL_TEXTURE_2D);
            
            //ground
            
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glTexCoord2f(0,1); 
	        gl.glVertex3f(-50.0f, 0, -50.0f);    
	         
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(0,0.5f);   
	        gl.glVertex3f(50.0f, 0, -50.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     
	        gl.glTexCoord2f(1,0.5f);   
	        gl.glVertex3f(50.0f, 0, 50.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-50.0f, 0, 50.0f);   
	        gl.glEnd();
	        	        	        
	        
	        //ceiling
	        
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
		    //gl.glTexCoord2f(0,0);   	       
	        gl.glTexCoord2f(0,0.5f);
	        gl.glVertex3f(-100.0f, 100.0f, -100.0f);    
	         
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,0.5f);	        	        
	        gl.glVertex3f(100.0f, 100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     	        
	        gl.glTexCoord2f(1,0);
	        gl.glVertex3f(100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-100.0f, 100.0f, 100.0f);   
	        gl.glEnd();
	        
	        gl.glDisable(GL.GL_TEXTURE_2D);	        	      
	        if(override == false){
	        if(!velocitySet){
	        	
	        	Vx_init = (float) (V_init*(float)Math.sin(-1*theta*Math.PI/180)*Math.cos(phi*Math.PI/180));
	        	Vy_init = V_init*(float)Math.sin(phi*Math.PI/180);
	        	Vz_init = (float) (V_init*(float) Math.cos(theta*Math.PI/180)*Math.cos(phi*Math.PI/180));
	        }
	        if(mode == 1){
	        	gl.glTranslatef(-x+2, 5, (float)-22.5);
	        	drawSphere(gl);
	        	}
	        else if(mode == 2){
	        	
	        	velocitySet = true;
	        	
	        	if(inMotion){
	        		start = System.currentTimeMillis();
	        		inMotion = false;
	        	}
	        	time0 = (float) ((System.currentTimeMillis() - start)/1000.0);
	        	Vy_final = (float) (Vy_init - 9.8*time0);
	        	float con = Vy_init/Vy_final;
	        	time1 = (float) ((System.currentTimeMillis() - start)/(1000.0*con));
	        	float hit_x = -x+2-Vx_init*time0;
	        	double hit_y = (5+(Vy_init-0.5*9.8*time1*time1));
	        	float hit_z = -22-Vz_init*time0;
	        	if(hit_z <= -32 && hit_z >-35 && -1 < hit_x && hit_x < 4 && 6 < hit_y && hit_y < 9.35){
	        		hit_backboard=true;
	        	}
	        	if(hit_y < 8 && hit_y > 7.3 && hit_backboard == true){
	        		bank_shot = true;
	        	}
	        	if(1.0 < hit_x &&  hit_x < 2.20 && hit_z <= -32 && hit_y < 7.45 && hit_y > 7 || 1.0 < hit_x &&  hit_x < 2.20 && hit_y < 7.5 && hit_y > 6.5 && bank_shot == true){
	        		hit_shot = true;
	        	}	        	
	        	if(hit_shot == false && .85 < hit_x && hit_x <= 1.05 && hit_z <= -32 && hit_y < 7.4 && hit_y > 6.5){
	        		hit_left_rim = true;
	        	}
	        	
	        	 if(hit_x > 2.15 &&  hit_x < 2.35 && hit_shot == false && hit_z <= -32 && hit_y < 7.4 && hit_y > 6.5){
	        		 hit_right_rim = true;
	        	 }
	        	if (hit_backboard == true){
	        		hit_z = -32;
	        	}	        	
	        	if (hit_shot == true){
	        		hit_z =-32;
	        		if(soundPlayed == false){
	        			File audioFilePath = new File("/Users/abdelhakmoustafa/Desktop/Re__CS335__Project/make.wav");
	        			try {
						play(audioFilePath);
						soundPlayed = true;
	        			} catch (Exception e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        		}
	        	}
	        	
	        	else if (hit_shot == false && soundPlayed == false && hit_y < 5 ){
	        		
	        		File audioFilePath = new File("/Users/abdelhakmoustafa/Desktop/Re__CS335__Project/fail.wav");
	                try {
						play(audioFilePath);
						soundPlayed = true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	
	        	if (hit_left_rim == true){
	        		hit_x -= 0.5;;
	        	}
	        	if (hit_right_rim == true){
	        		hit_x += 0.5;;
	        	}
	        	if(hit_y <= 1){
	        		hit_ground = true;

	        		//bounce_counter++;
	        	}
	        	if (hit_ground == true){
	        		hit_y = 1;
	        		
	        		
	        		/*
	        		if(bounce_counter == 1){
	        			hit_y = 5;
	        		}
	        		if(bounce_counter == 2){
	        			hit_y = 2.5;
	        		}
	        		if(bounce_counter == 3){
	        			hit_y = 1;
	        		}*/
	        	}
	        		gl.glTranslatef(hit_x,(float) hit_y, hit_z);
	        		drawSphere(gl);
	        	
	        	
		        }
	        else{	        	
	        	velocitySet = false;
	        	mode = 0;
        	}
	        }
	        
	        else if(override == true){
            		if(override == true){
            			if(replayMode == 0){
            			gl.glTranslatef(-x+2, 5, (float)-22.5);
        	        	drawSphere(gl);
        	        	replayMode++;
        	        	
            			}
        	        
            			if (replayMode == 1){
            				
            				if(inMotion){
            	        		start = System.currentTimeMillis();
            	        		inMotion = false;
            	        	}
            	        time0 = (float) ((System.currentTimeMillis() - start)/1000.0);
            	        Vy_final = (float) (Vy_init - 9.8*time0);
            	        float con = Vy_init/Vy_final;
            	        
            	        if(replaySpeed == 0)
            	        	con = Vy_init/Vy_final;
            	        else if(replaySpeed == 1)
            	        	con = (Vy_init/Vy_final)*4;
            	        else if(replaySpeed == 2)
            	        	con = (Vy_init/Vy_final)*10;
            	        
            	        time1 = (float) ((System.currentTimeMillis() - start)/(1000.0*con));
        	        	float hit_x = -x+2-Vx_init*time0;
        	        	double hit_y = (5+(Vy_init-0.5*9.8*time1*time1));
        	        	float hit_z = -22-Vz_init*time0;
        	        	if(hit_z <= -32 && hit_z >-35 && -1 < hit_x && hit_x < 4 && 6 < hit_y && hit_y < 9.35){
        	        		hit_backboard=true;
        	        	}
        	        	
        	        	if(1.05 < hit_x &&  hit_x < 2.15 && hit_z <= -32 && hit_y < 7.4 && hit_y > 7){
        	        		hit_shot = true;
        	        	}	        	
        	        	if(hit_shot == false && .85 < hit_x && hit_x <= 1.05 && hit_z <= -32 && hit_y < 7.4 && hit_y > 6.5){
        	        		hit_left_rim = true;
        	        	}
        	        	
        	        	 if(hit_x > 2.15 &&  hit_x < 2.35 && hit_shot == false && hit_z <= -32 && hit_y < 7.4 && hit_y > 6.5){
        	        		 hit_right_rim = true;
        	        	 }
        	        	if (hit_backboard == true){
        	        		hit_z = -32;
        	        	}	        	
        	        	if (hit_shot == true){
        	        		hit_z =-32;
        	        	}
   
        	        	if (hit_left_rim == true){
        	        		hit_x -= 0.5;;
        	        		hit_z =-32;
        	        	}
        	        	if (hit_right_rim == true){
        	        		hit_x += 0.5;;
        	        		hit_z =-32;
        	        	}
        	        	if(hit_y <= 1){
        	        		hit_ground = true;
        	        	}
        	        	if (hit_ground == true){
        	        		hit_y = 1;
        	        		/*
        	        		while(bounce_counter < 10){
        	        			hit_y = 5;
        	        			bounce_counter++;
        	        		}
        	        		while(bounce_counter < 20){
        	        			hit_y = 2.5;
        	        			bounce_counter++;
        	        		}
        	        		while(bounce_counter >= 30){
        	        			hit_y = 1;
        	        		}*/
        	        	}        	             	        	
        	        		gl.glTranslatef(hit_x,(float) hit_y, hit_z);
        	        		drawSphere(gl);
            			}
            		}
        	        
                	else{	        	
        	        	replayVelocitySet = false;
        	        	replayMode = 0;
        	        }
	        }
            		                		        	     	        	        

		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		private Boolean loadModels(GL2 gl) {
			goalModel = ModelLoaderOBJ.LoadModel("/Users/abdelhakmoustafa/Desktop/Re__CS335__Project/goal.obj", "/Users/abdelhakmoustafa/Desktop/Re__CS335__Project/goal.mtl", gl);
			if (goalModel == null) {
				return false;
			}
			return true;
		}

		
public void mouseDragged(MouseEvent e) {
			
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			currentX = XX - initialX;
			currentY = YY - initialY;
			

		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Your window get focus."); 
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			picked_x = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			picked_y = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			initialX = picked_x;
			
			initialY = picked_y;
			
			System.out.printf("Point clicked: (%.3f, %.3f)\n", picked_x, picked_y);
			
			mouseX0 = e.getX();
			mouseY0 = e.getY();
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			currentX = XX - initialX;
			currentY = YY - initialY;
			
		}
		
		
		public void mouseEntered(MouseEvent e) { // cursor enter the window
			// TODO Auto-generated method stub
			
		}
		
		
		public void mouseExited(MouseEvent e) { // cursor exit the window
			// TODO Auto-generated method stub
			
		}
		
//		public void keyTyped(KeyEvent e) {
//			// TODO Auto-generated method stub
//		    char key= e.getKeyChar();
//			System.out.printf("Key typed: %c\n", key); 				
//			
//			switch (key){
//			case 'w':
//	    		z += 1;
//	    		break;
//	    	case 's':
//	    		z -= 1;	    		
//	    		break;
//	    	case 'd':
//	    		x -= 1;
//	    		goalZ -= 1;
//	    		break;
//	    	case 'a':
//	    		x += 1;
//	    		goalZ += 1;
//	    		break;
//	    	case ' ':
//	    		mode++;
//	    		inMotion = true;
//	    		hit_backboard = false;
//	    		hit_ground = false;
//	    		hit_left_rim = false;
//	    		hit_right_rim = false;
//	    		hit_shot = false;
//	    		soundPlayed = false;
//	    	}
//			
//		}

//		@Override
//		public void keyPressed(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
		
	  /*  
	public void init(GLDrawable gLDrawable) {
		final GL gl = glDrawable.getGL();
        final GLU glu = glDrawable.getGLU();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0f, 1.0f, -1.0f, 1.0f); // drawing square
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }*/
	
}
