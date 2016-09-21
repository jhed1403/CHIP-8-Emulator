
package emulator;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Dimension;
import chip8.Chip8;

public class Frame extends JFrame {
    
    private Panel panel;
    
    public Frame(Chip8 c){
        setPreferredSize(new Dimension(640, 320));
        pack();
        setPreferredSize(new Dimension(640 + getInsets().left + getInsets().right, 320 + getInsets().top + getInsets().bottom ));
        panel = new Panel(c);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chip-8 Emulator");
        pack();
        setVisible(true);
        
    }
    
}
