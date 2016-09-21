/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulator;

import chip8.Chip8;

public class Main extends Thread {

    private Chip8 chip8;
    private Frame frame;

    public Main() {
        chip8 = new Chip8();
        chip8.initialize();
        chip8.loadProgram("./pong2.c8");
        frame = new Frame(chip8);
    }

    public void run() {
        while (true) {
            chip8.run();
            if (chip8.needRedraw()) {
                frame.repaint();
                chip8.removeDrawFlag();
            }
            try {
                Thread.sleep((long) 16.66);
            } catch (InterruptedException e)  {
                //unthrown exception
            }

        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

}
