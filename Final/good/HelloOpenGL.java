package HelloOpenGL;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas; 

import com.jogamp.opengl.util.Animator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;




public class HelloOpenGL extends Frame implements KeyListener{

	static JoglEventListener jgl = new JoglEventListener();
	
	static JProgressBar progressBar = new JProgressBar();
	
	static JButton replay = new JButton();
	
	static JButton speed1 = new JButton();
	
	static JButton speed2 = new JButton();
	
	static JButton speed3 = new JButton();
	
	static Timer timer;
	
	static int shotMode = 0;
	
	static int force = 0;
	
	static int shotFailed = 0;
	
	static Animator anim = null;
	private void setupJOGL(){
		
		
		
	    GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);
	    caps.setHardwareAccelerated(true);
	    
	    GLCanvas canvas = new GLCanvas(caps); 
	    	    
	    
	    add(canvas);

        
        //canvas.addGLEventListener(new JoglEventListener()); 
        
        canvas.addGLEventListener(jgl);
        
        canvas.addMouseListener(jgl);
        
        canvas.addMouseMotionListener(jgl);
        
        // canvas.addKeyListener(jgl);
        
        canvas.addKeyListener(this);
        
        anim = new Animator(canvas);
        anim.start();

	}
	
    public HelloOpenGL() {
        super("Basic JOGL Demo");

        setLayout(new BorderLayout());
        


        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        setSize(600, 600);
        setLocation(40, 40);

        setVisible(true);

        setupJOGL();
    }

    public static void main(String[] args) {
        HelloOpenGL demo = new HelloOpenGL();
        
        //demo.addKeyListener(demo);
        
        
        JPanel Panel = new JPanel();
        
        Panel.setLayout(new GridLayout(1,4));
             
        JPanel buttonPanel = new JPanel();
                
   		buttonPanel.setLayout(
   				new BorderLayout( 4, 4 ) );
   	
   		
   		JButton button = new JButton();
   		
   		button.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){ 
				  
				  jgl.phi += 5;
				  
			  }});
   		
   		BufferedImage img = null;
		try {
			    img = ImageIO.read(new File("/Users/abdelhakmoustafa/Desktop/images/u.png"));
		} catch (IOException e) {
			    e.printStackTrace();
		}
			
		Image dimg = img.getScaledInstance(30, 30,
			        Image.SCALE_SMOOTH);
			
		ImageIcon imageIcon = new ImageIcon(dimg);
			
		button.setIcon(imageIcon);
		
		JButton button1 = new JButton();
   		
		button1.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){ 
				  
				  jgl.phi -= 5;
				  
			  }});
		
   		BufferedImage img1 = null;
		try {
			    img1 = ImageIO.read(new File("/Users/abdelhakmoustafa/Desktop/images/d.png"));
		} catch (IOException e) {
			    e.printStackTrace();
		}
			
		Image dimg1 = img1.getScaledInstance(30, 30,
			        Image.SCALE_SMOOTH);
			
		ImageIcon imageIcon1 = new ImageIcon(dimg1);
			
		button1.setIcon(imageIcon1);
		
		JButton button2 = new JButton();
		
		button2.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){ 
				  
				  jgl.theta -= 5;
				  
			  }});
   		
   		BufferedImage img2 = null;
		try {
			    img2 = ImageIO.read(new File("/Users/abdelhakmoustafa/Desktop/images/l.png"));
		} catch (IOException e) {
			    e.printStackTrace();
		}
			
		Image dimg2 = img2.getScaledInstance(30, 30,
			        Image.SCALE_SMOOTH);
			
		ImageIcon imageIcon2 = new ImageIcon(dimg2);
			
		button2.setIcon(imageIcon2);
		
		JButton button3 = new JButton();
   		
   		BufferedImage img3 = null;
		try {
			    img3 = ImageIO.read(new File("/Users/abdelhakmoustafa/Desktop/images/r.png"));
		} catch (IOException e) {
			    e.printStackTrace();
		}
			
		Image dimg3 = img3.getScaledInstance(30, 30,
			        Image.SCALE_SMOOTH);
			
		ImageIcon imageIcon3 = new ImageIcon(dimg3);
			
		button3.setIcon(imageIcon3);
		
		button3.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){ 
				  
				  jgl.theta += 5;
				  
			  }});
		
		buttonPanel.add(button, BorderLayout.NORTH);
		
		buttonPanel.add(button1, BorderLayout.SOUTH);
		
		buttonPanel.add(button2, BorderLayout.WEST);
		
		buttonPanel.add(button3, BorderLayout.EAST);
		
		Panel.add(buttonPanel);
		
		JPanel buttonPanel2 = new JPanel();
		
		buttonPanel2.setLayout(
   				new FlowLayout() );
		
		JButton button4 = new JButton();
			
		button4.setIcon(imageIcon2);
		
		button4.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){ 
				  
				  jgl.x += 1;
				  jgl.goalZ += 1;
				  
			  }});
		
		buttonPanel2.add(button4);
		
		JButton button5 = new JButton();
			
		button5.setIcon(imageIcon3);
		
		button5.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){ 
				  
				  jgl.x -= 1;
				  jgl.goalZ -= 1;
				  
			  }});
		
		buttonPanel2.add(button5);
	
		progressBar.setVisible(false);
			
			ActionListener actListner = new ActionListener() {

				@Override
				

				public void actionPerformed(ActionEvent event) {
					
					if(shotMode == 1){
						
						replay.setVisible(false);
						speed1.setVisible(false);
						
						speed2.setVisible(false);
						
						speed3.setVisible(false);
						progressBar.setVisible(true);
						force+=5;
						progressBar.setValue(force);
						if(force == 100){
							force = 0;
							shotFailed ++;
							if(shotFailed == 2){
								shotFailed = 0;
								progressBar.setVisible(false);
								shotMode = 0;
								jgl.mode = 0;
							}		
						}
					}
				}

	   	    };
	   	    
	   	    timer = new Timer(100, actListner);

	   	    timer.start();

		
		
		Panel.add(buttonPanel2);
		
		replay.setText("replay");
		
		speed1.setText("x1");
		
		speed2.setText("x1/4");
		
		speed3.setText("x1/10");
		
		speed1.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){
				  
				  jgl.replaySpeed = 0;
				  
			  }});
		
		speed2.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){
				  
				  jgl.replaySpeed = 1;
				  
			  }});
		
		speed3.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){
				  
				  jgl.replaySpeed = 2;
				  
			  }});
		
		replay.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e){
				    jgl.override = true;
					jgl.inMotion = true;
					jgl.hit_backboard = false;
		    		jgl.hit_ground = false;
		    		jgl.hit_left_rim = false;
		    		jgl.hit_right_rim = false;
		    		jgl.hit_shot = false;
		    		jgl.soundPlayed = false;
				  
			  }});
		
		replay.setVisible(false);
		
		speed1.setVisible(false);
		
		speed2.setVisible(false);
		
		speed3.setVisible(false);
		
		Panel.add(replay);
		
		Panel.add(speed1);
		
		Panel.add(speed2);
		
		Panel.add(speed3);
		
		Panel.add(progressBar);
   		demo.add(Panel,BorderLayout.SOUTH);
		
        demo.setVisible(true);
    }
    
    public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	    char key= e.getKeyChar();
		System.out.printf("Key typed: %c\n", key);
		System.out.printf("Initial Velocity: %f\n", jgl.Vx_init);
		System.out.printf("Initial Velocity: %f\n", jgl.Vy_init);
		System.out.printf("Initial Velocity: %f\n", jgl.Vz_init);
		
		
		switch (key){
		
		/*
		case 'r':{
			jgl.phi = 0;
			jgl.theta = 0;
		}
		*/
    	case 's':
    		shotMode ++;
    		jgl.mode++;
    		jgl.override = false;
    		jgl.replay = false;
    		
    		if(shotMode == 2){
    			
    			shotMode = -1;
    			
    			jgl.V_init = force/3;

	    		jgl.inMotion = true;
	    		jgl.hit_backboard = false;
	    		jgl.hit_ground = false;
	    		jgl.hit_left_rim = false;
	    		jgl.hit_right_rim = false;
	    		jgl.hit_shot = false;
	    		jgl.soundPlayed = false;
	    		
	    		
	    		
	    		force = 0;
	    		progressBar.setVisible(false);
	    		replay.setVisible(true);
	    		
	    		speed1.setVisible(true);
	    		
	    		speed2.setVisible(true);
	    		
	    		speed3.setVisible(true);
    		}
    		
    		break;
    	case 'g':
			jgl.override = true;
			jgl.inMotion = true;
			jgl.hit_backboard = false;
    		jgl.hit_ground = false;
    		jgl.hit_left_rim = false;
    		jgl.hit_right_rim = false;
    		jgl.hit_shot = false;
    		jgl.soundPlayed = false;
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
}
