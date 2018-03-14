// Naz Taylor
// CS 335
// Homework 4 Skybox

package HW4;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;




public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	int windowWidth, windowHeight;
	float orthoX=40;

	boolean leftClick;
	boolean dragging;
	boolean toggle;
	int toggleCount = 1;
	
	float dragX;
	float dragY;
	
	float tilt = 0;
	float tiltInc = 0;
	
	int mouseX, mouseY;	
	float picked_x = 0.0f, picked_y = 0.0f;
	
	float focalLength = 30.0f;
	
	boolean diffuse_flag  = false;
	boolean specular_flag = false;
	
	boolean smooth_flag = true;

    private GLU glu = new GLU();

    float backrgb[] = new float[4]; 
	float rotx;
	float roty;
	float rotz;
	Texture skybox0 = null;
	Texture skybox1 = null;
	Texture skybox2 = null;
	Texture skybox3 = null;
	Texture skybox4 = null;
	Texture skybox5 = null;
	Texture woodbox = null;
	int eyex = 0;
	int eyez = 1;
	float camx = 0;
	float camy = 0;
	float camz = 0;
	float camz2 = 1;
	float movx = 0f;
	float movz = 1.0f;
	float theta = 0;

	
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
	        
	        // load the texture;
	        
	        try {
	        	 skybox0 = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/skybox_pine_forest_front.jpg"), false);
	        	 // left
	        	 skybox1 = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/skybox_pine_forest_left.jpg"), false);
	        	 // back
	        	 skybox2 = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/skybox_autum_forest_back.jpg"), false);
	        	 // right
	        	 skybox3 = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/skybox_autum_forest_right.jpg"), false);
	        	 // top
	        	 skybox4 = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/skybox_autum_forest_top.jpg"), false);
	        	 // bottom
	        	 skybox5 = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/skybox_autum_forest_bottom.jpg"), false);
	        	 woodbox = TextureIO.newTexture(new File("C:/Users/iPhone/Documents/CS 335/Skybox texture/box.jpg"), false);
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             
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

	       
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	    	
		    	windowWidth = width;
		    	windowHeight = height;
		        final GL2 gl = gLDrawable.getGL().getGL2();

		        if (height <= 0) // avoid a divide by zero error!
		            height = 1;
		        final float h = (float) width / (float) height;
		        gl.glViewport(0, 0, width, height);
		        gl.glMatrixMode(GL2.GL_PROJECTION);
		        gl.glLoadIdentity();
		       // gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
		        glu.gluPerspective(45.0f, h, 1, 100000.0);
	        
	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();

			glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 1);

	        gl.glRotatef(rotx, 0, 1, 0);
	        gl.glRotatef(roty, 1, 0, 0);
	        gl.glRotatef(rotz, 0, 0, 1);
	        
			gl.glTranslated(movx, 1, movz);
			
			gl.glPushMatrix();
			
			gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		    gl.glDisable(GL.GL_DEPTH_TEST);
		    gl.glDisable(GL2.GL_LIGHTING);
		    gl.glDisable(GL.GL_BLEND);
	        gl.glEnable(GL.GL_TEXTURE_2D);
	        // Front
	        skybox0.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(50f, -50f, -50f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-50f, -50f, -50f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-50f, 50f, -50f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(50f, 50f, -50f);   
	        gl.glEnd(); 
	        
	        // Left
	        skybox1.bind(gl);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(50f, -50f, 50f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(50f, -50f, -50f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(50f, 50f, -50f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(50f, 50f, 50f);   
	        gl.glEnd(); 
	        
	        // back
	        skybox2.bind(gl);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-50f, -50f, 50f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(50f, -50f, 50f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(50f, 50f, 50f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-50f, 50f, 50f);   
	        gl.glEnd(); 
	        
	        // right
	        skybox3.bind(gl);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-50f, -50f, -50f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-50f, -50f, 50f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-50f, 50f, 50f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-50f, 50f, -50f);   
	        gl.glEnd(); 
	        
	        // top
	        skybox4.bind(gl);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(50f, 50f, -50f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-50f, 50f, -50f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-50f, 50f, 50f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(50f, 50f, 50f);   
	        gl.glEnd(); 
	        
	        // bottom
	        skybox5.bind(gl);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(50f, -50f, 50f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-50f, -50f, 50f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-50f, -50f, -50f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(50f, -50f, -50f);   
	        gl.glEnd(); 
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        
	        
	     // Front
	        woodbox.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-5f, -10f, -10f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-10f, -10f, -10f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-10f, -5f, -10f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-5f, -5f, -10f);   
	        gl.glEnd(); 
	        
	        // left
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-5f, -10f, -5f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-5f, -10f, -10f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-5f, -5f, -10f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-5f, -5f, -5f);   
	        gl.glEnd(); 
	        
	        // back
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-10f, -10f, -5f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-5f, -10f, -5f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-5f, -5f, -5f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-10f, -5f, -5f);   
	        gl.glEnd(); 
	        /*
	        // right
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-10f, -10f, -10f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-10f, -10f, -5f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-10f, -5f, -5f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-10f, -5f, -10f);   
	        gl.glEnd(); 
	        */
	        // top
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-5f, -5f, -10f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-10f, -5f, -10f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-10f, -5f, -5f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-5f, -5f, -5f);   
	        gl.glEnd(); 
	        /*
	        // bottom
	        gl.glBegin(GL2.GL_QUADS);  
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-5f, -10f, -5f);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(-10f, -10f, -5f);  // 
	          
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-10f, -10f, -10f);   
	          
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-5f, -10f, -10f);   
	        gl.glEnd(); 
	        */
	        
	        gl.glPopMatrix();
	        gl.glDisable(GL.GL_TEXTURE_2D);
	        

	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 
			
			switch(key)
			{
			case 'w':	
				movz += 0.5;
				break;
				
			case 's':	
				movz -= 0.5;
				break;
				
			case 'd':	
				movx -= 0.5;
				break;
				
			case 'a':	
				movx += 0.5;
				break;
	
			case '0':
				eyex = 0;
				eyez = 0;			
				break;
				
			default:
				
				break;
			
			
			
			}
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			dragX = XX/2;
			dragY = YY/2;
			System.out.printf("Point drug to: (%.3f, %.3f)\n", XX, YY);
			dragging = true;
			if (leftClick == true){
				if (dragX != 0 && dragY < 5){
					rotx += dragX / 10;
				}
				if (dragY != 0 && dragX < 5 ){
					roty -= dragY / 10;
				}
			}
			else if (leftClick == false){
				focalLength -= dragX/8;
			}

			//repaint();
			
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
			
			System.out.printf("Point clicked: (%.3f, %.3f)\n", picked_x, picked_y);
			
			mouseX = e.getX();
			mouseY = e.getY();
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				leftClick = true;
			
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				leftClick = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) { // cursor enter the window
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) { // cursor exit the window
			// TODO Auto-generated method stub
			
		}


	
}



