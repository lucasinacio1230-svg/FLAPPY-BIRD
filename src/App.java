import javax.swing.JFrame;

public class App {
  
    public static void main(String[] args) throws Exception {
       
        int larguraBorda = 360;
        int Alturaborda = 640;

        JFrame janela = new JFrame("flappy bird");
        janela.setSize(larguraBorda,Alturaborda);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
      FlappyBird flappybird = new FlappyBird();

        janela.add(flappybird);
        janela.pack();
        janela.setVisible(true);

    }
}
