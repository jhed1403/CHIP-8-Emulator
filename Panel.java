package emulator;

import javax.swing.JPanel;
import chip8.Chip8;
import java.awt.Color;
import java.awt.Graphics;

public class Panel extends JPanel {

    private Chip8 chip;

    public Panel(Chip8 chip) {
        this.chip = chip;
    }
    
    public void paint(Graphics g){
        byte[] display = chip.getDisplay();
        for(int i = 0; i < display.length; i++){
            if(display[i] == 0){
                g.setColor(Color.BLACK); //Black if zero
            }else{
                g.setColor(Color.WHITE); //White if one
            }
            
            int x = (i % 64);
            int y = (int)Math.floor(i /64); //calculate the coordinates
            
            g.fillRect(x * 10, y * 10, 10, 10);
        }
    }

}
