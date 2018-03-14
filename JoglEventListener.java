package homework1part2;


import com.jogamp.opengl.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.Graphics;

public class JoglEventListener implements GLEventListener, KeyListener {

	float r = (float) .5;
    float num_segments = 360;
    float transx = 0;
    float transy = 0;

	 public void displayChanged(GLAutoDrawable gLDrawable, 
	            boolean modeChanged, boolean deviceChanged) {
	    }
	  	@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			  gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			
		      gl.glLoadIdentity();
		      
		      gl.glColor3f(1.0f, 1.0f, 1.0f);
		      gl.glBegin(GL.GL_LINE_LOOP);
		      for(int ii = 0; ii < num_segments; ii++) 
		  	{ 
		  		float theta = (float) (2.0 * Math.PI * (ii) / (num_segments));//get the current angle 

		  		float x = (float) (r * Math.cos(theta));//calculate the x component 
		  		float y = (float) (r * Math.sin(theta));//calculate the y component 

		  		gl.glVertex2f(x+transx, y+transy);//output vertex 

		  	} 
		  	  gl.glEnd();
		  	  
		  	  gl.glFlush();
		}
/*
	 public void paint(Graphics g) {
		    g.drawOval (10, 10, 1, 1);  
		  }
	 */
		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			if(key=='o'){
				r += .05;
				repaint();
			}
			else if(key == 'p'){
				r -= .05;
				repaint();
			}
			else if(key =='a'){
				transx -= 0.05;
				repaint();
			}
			else if(key == 'f'){
				transx += 0.05;
				repaint();
			}
			else if(key == 'w'){
				transy += 0.05;
				repaint();
			}
			else if(key == 'z'){
				transy -= 0.05;
				repaint();
			}
		}

		private void repaint() {
			// TODO Auto-generated method stub
			
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
		public void init(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
			// TODO Auto-generated method stub
			
		}

}
