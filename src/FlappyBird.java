import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




    

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
int LarguraBorda = 360;
int AlturaBorda = 640;


//IMAGEN
Image birdImage;
Image backgroundImage;
Image bottomPipeImage;
Image topPipeImage;

//PASSARO
int birdX = LarguraBorda / 8;
int birdY = AlturaBorda / 2;
int birdWidth = 34;
int birdHeight = 24;

class Bird {
 int x = birdX;
 int y = birdY;
 int width = birdWidth;  int height = birdHeight;
    Image img;

     Bird (Image img) {
     this.img = img;

    }

}


//CANOS
int PipeX = LarguraBorda;
int PipeY = 0;
int PipeWidth = 64;
int PipeHeight = 512;

class Pipe {
int x = PipeX;
int y = PipeY;
int width = PipeWidth;
int height = PipeHeight;
Image img;
boolean passed = false;

Pipe (Image img) {
this.img = img;
}

}




//LOGICA DO JOGO
Bird bird;
int velocityX = -4;
int velocityY = 0;
int gravity = 1;

ArrayList<Pipe> pipes;
Random random = new Random();


Timer gameloop;
Timer placePipesTimer;

boolean gameOver = false;

double counter = 0;


 FlappyBird(){
   setPreferredSize(new Dimension(LarguraBorda, AlturaBorda));
   setFocusable(true);
   addKeyListener(this);

backgroundImage = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
birdImage = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
topPipeImage = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
bottomPipeImage = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();

  bird = new Bird (birdImage);
  pipes = new ArrayList<Pipe>();

  placePipesTimer = new Timer(1500, new ActionListener() { 
    @Override
    public void actionPerformed(ActionEvent e) {
      placePipes();
    }


  }); 
placePipesTimer.start();


  gameloop = new Timer (1000/60, this);
  gameloop.start();
}


public void paintComponent (Graphics g) {
  super.paintComponent(g);
  draw(g);


}
    
public void placePipes () {
  int randomPypeY = (int) (PipeY - PipeHeight/4 - Math.random() * (PipeHeight/2));
  int openingSpace = AlturaBorda / 4;
  Pipe topPipe = new Pipe(topPipeImage);
  topPipe.y = randomPypeY;
  pipes.add(topPipe);

  Pipe bottomPipe = new Pipe (bottomPipeImage);
  bottomPipe.y = topPipe.y + PipeHeight + openingSpace;
  pipes.add(bottomPipe);

}



    public void draw(Graphics g) {
    
        g.drawImage(backgroundImage, 0, 0, LarguraBorda, AlturaBorda, null);

        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        for (int i = 0; i < pipes.size(); i++) {
          Pipe pipe = pipes.get(i); 

          g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font( "Arial", Font.BOLD, 20));
        g.drawString("" + (int)counter, 10, 20);
    }
        
    public void move() {
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y, 0);


  for (int i = 0; i < pipes.size(); i++){
    Pipe pipe = pipes.get(i);
    pipe.x += velocityX;

    if (!pipe.passed && bird.x > pipe.x + pipe.width){
        pipe.passed = true;
        counter += 0.5;

    }

    if (collision(bird, pipe)) {
      gameOver = true;
    }
  }

  if (bird.y > AlturaBorda){
    gameOver = true;

  }

    }


  
@Override
public void keyPressed(KeyEvent e) {
  if(e.getKeyCode() == KeyEvent.VK_SPACE) {
    velocityY = -9;


  }

  if (gameOver){
    bird.y = birdY;
    bird.x = birdX;
    bird.y = birdY;
    pipes.clear();
    counter = 0;
    gameOver = false;
    placePipesTimer.start();
    gameloop.start();
  }
}




public boolean collision (Bird a, Pipe b) {
  return a.x < b.x + b.width && //  1 O lado esquerdo do passaro esta antes  do direito do cano?
  a.x + a.width > b.x && // 2 O lado direito do passaro esta depois do lado esquerdo do cano?
  a.y < b.y + b.height && // 3 O topo  do passaro esta antes da parte  inferior do cano?
  a.y + a.height > b.y;   // 4 A parte inferior do passaro esta depois do topo do cano?


}

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver){
        placePipesTimer.stop();
        gameloop.stop();

        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }


    @Override
    public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }


    }
  




