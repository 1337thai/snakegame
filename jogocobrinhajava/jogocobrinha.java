import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class jogocobrinha extends JFrame implements ActionListener, KeyListener {

    private final int LARGURA_TELA = 300;
    private final int ALTURA_TELA = 300;
    private final int TAMANHO_BLOCO = 10;
    private final int NUMERO_BLOCOS = LARGURA_TELA * ALTURA_TELA / (TAMANHO_BLOCO * TAMANHO_BLOCO);
    private int[] xCobrinha = new int[NUMERO_BLOCOS];
    private int[] yCobrinha = new int[NUMERO_BLOCOS];
    private int tamanhoCobrinha = 3;
    private int macaX;
    private int macaY;
    private boolean emJogo = true;
    private Timer timer;

    public jogocobrinha() {
        setTitle("Jogo da Cobrinha");
        setSize(LARGURA_TELA, ALTURA_TELA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addKeyListener(this);
        setFocusable(true);

        inicializarJogo();
        timer = new Timer(100, this);
        timer.start();
    }

    private void inicializarJogo() {
        emJogo = true;
        tamanhoCobrinha = 3;

        for (int i = 0; i < tamanhoCobrinha; i++) {
            xCobrinha[i] = 50 - i * TAMANHO_BLOCO;
            yCobrinha[i] = 50;
        }

        gerarMaca();
    }

    private void gerarMaca() {
        Random random = new Random();
        macaX = random.nextInt(LARGURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
        macaY = random.nextInt(ALTURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
    }

    private void mover() {
        for (int i = tamanhoCobrinha; i > 0; i--) {
            xCobrinha[i] = xCobrinha[i - 1];
            yCobrinha[i] = yCobrinha[i - 1];
        }

        switch (direcao) {
            case KeyEvent.VK_UP:
                yCobrinha[0] -= TAMANHO_BLOCO;
                break;
            case KeyEvent.VK_DOWN:
                yCobrinha[0] += TAMANHO_BLOCO;
                break;
            case KeyEvent.VK_LEFT:
                xCobrinha[0] -= TAMANHO_BLOCO;
                break;
            case KeyEvent.VK_RIGHT:
                xCobrinha[0] += TAMANHO_BLOCO;
                break;
        }
    }

    private void checarColisao() {
        // Checar colisão com as bordas da tela
        if (xCobrinha[0] >= LARGURA_TELA || xCobrinha[0] < 0 || yCobrinha[0] >= ALTURA_TELA || yCobrinha[0] < 0) {
            emJogo = false;
        }

        // Checar colisão com o corpo da cobrinha
        for (int i = 1; i < tamanhoCobrinha; i++) {
            if (xCobrinha[0] == xCobrinha[i] && yCobrinha[0] == yCobrinha[i]) {
                emJogo = false;
            }
        }

        // Checar colisão com a maçã
        if (xCobrinha[0] == macaX && yCobrinha[0] == macaY) {
            tamanhoCobrinha++;
            gerarMaca();
        }
    }

    private void desenhar(Graphics g) {
        if (emJogo) {
            // Desenhar a maçã
            g.setColor(Color.RED);
            g.fillRect(macaX, macaY, TAMANHO_BLOCO, TAMANHO_BLOCO);

            // Desenhar a cobrinha
            for (int i = 0; i < tamanhoCobrinha; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillRect(xCobrinha[i], yCobrinha[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
            }
        } else {
            // Se o jogo acabou, exibir uma mensagem
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over", 100, ALTURA_TELA / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (emJogo) {
            mover();
            checarColisao();
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        desenhar(g);
    }

    private int direcao = KeyEvent.VK_RIGHT;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (direcao != KeyEvent.VK_RIGHT)) {
            direcao = KeyEvent.VK_LEFT;
        } else if ((key == KeyEvent.VK_RIGHT) && (direcao != KeyEvent.VK_LEFT)) {
            direcao = KeyEvent.VK_RIGHT;
        } else if ((key == KeyEvent.VK_UP) && (direcao != KeyEvent.VK_DOWN)) {
            direcao = KeyEvent.VK_UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direcao != KeyEvent.VK_UP)) {
            direcao = KeyEvent.VK_DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Não é necessário implementar neste exemplo
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não é necessário implementar neste exemplo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new jogocobrinha().setVisible(true);
        });
    }
}
