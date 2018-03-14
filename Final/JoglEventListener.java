package basketball;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;



public class JoglEventListener implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {
	
	float backrgb[] = new float[4]; 
	float rot; 
	
	
	int windowWidth, windowHeight;
	float orthoX=40;

	int mouseX0, mouseY0;	
	float picked_x = 0.0f, picked_y = 0.0f;
	
	float initialX = 0, initialY = 0;
	
	float theta = 0; 
	
	float currentX, currentY, currentDistance = 0;
	
	float x = 0, y = 0, z = 0;
	
	float focalLength = 30.0f;
	
	
	Texture mytex = null; 

    	private GLU glu = new GLU();

	
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
	        	 //mytex = TextureIO.newTexture(new File("C:/Users/ruigang/workspace/helloOpenGL/hp.png"), false);
	        	 mytex = TextureIO.newTexture(new File("/Users/abdelhakmoustafa/Desktop/court.png"), false);
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

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			//backrgb[0]+=0.0005;
			if (backrgb[0]> 1) backrgb[0] = 0; 
			
			
			gl.glLoadIdentity();			                   	  	             
			
	        gl.glEnable(GL.GL_TEXTURE_2D);	        
	        
            mytex.bind(gl); 
         
            // currentY & currentX are equal to the distance moved by the mouse
            // in the x and y directions, respectively.
                  
            if(Math.abs(currentY) > Math.abs(currentX)){
				theta = currentY * 10;
				gl.glRotatef(theta, 1, 0, 0);
			}else{
				theta = currentX * 10;
				gl.glRotatef(theta,0, 1, 0);
			}              
            
            
            // set the initial position of the camera.
            
            gl.glTranslatef(x, -5, 4);

            
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
	        gl.glVertex3f(-50.0f, 50.0f, -50.0f);    
	         
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,0.5f);	        	        
	        gl.glVertex3f(50.0f, 50.0f, -50.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     	        
	        gl.glTexCoord2f(1,0);
	        gl.glVertex3f(50.0f, 50.0f, 50.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-50.0f, 50.0f, 50.0f);   
	        gl.glEnd();
	        
	        gl.glDisable(GL.GL_TEXTURE_2D);
	        
	        gl.glLoadIdentity();
	        	        
	        
	        // walls
	        
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glVertex3f(-100.0f, -100.0f, -100.0f);    
	         	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(100.0f, -100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     
	        gl.glVertex3f(100.0f, 100.0f, -100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);  
	        gl.glVertex3f(-100.0f, 100.0f, -100.0f);   
	        gl.glEnd(); 
	        
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glVertex3f(-100.0f, -100.0f, 100.0f);    
	         	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(100.0f, -100.0f, 100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f); 
	        gl.glVertex3f(100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glVertex3f(-100.0f, 100.0f, 100.0f);   
	        gl.glEnd();	        	        
	        
	        
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glVertex3f(-100.0f, -100.0f, -100.0f);    
	        	        
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(-100.0f, 100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     
	        gl.glVertex3f(-100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);
	        gl.glTexCoord2f(0,0.333f);	           
	        gl.glEnd();
	        
	      //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glVertex3f(100.0f, -100.0f, -100.0f);    
	         
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(100.0f, 100.0f, -100.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     
	        gl.glVertex3f(100.0f, 100.0f, 100.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glVertex3f(100.0f, -100.0f, 100.0f);   
	        gl.glEnd();	        	               	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
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
		
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 				
			
			switch (key){
			case 'w':
	    		z += 1;
	    		break;
	    	case 's':
	    		z -= 1;	    		
	    		break;
	    	case 'd':
	    		x += 1;
	    		break;
	    	case 'a':
	    		x -= 1;
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
