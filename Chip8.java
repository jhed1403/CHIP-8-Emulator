package chip8;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Chip8 {

    private char[] memory; //Total memory of the chip 8 (4096)
    private char[] V; //CPU register
    private char I; //Index register
    private char programCounter; //program counter

    private char stack[];
    private char stackPointer;

    //Timer for game and sound
    private int delayTimer;
    private int soundTimer;

    private byte[] keys;

    private byte[] display; //graphics

    private boolean needRedraw;

    public void initialize() {
        memory = new char[4096];
        V = new char[16];
        I = 0x0;
        programCounter = 0x200;

        stack = new char[16];
        stackPointer = 0;

        delayTimer = 0;
        soundTimer = 0;

        keys = new byte[16];

        display = new byte[64 * 32];

        needRedraw = false;

        loadFontset();

    }

    public void run() {
        //fetch opcode
        char opcode = (char) (memory[programCounter] << 8 | memory[programCounter + 1]);
        System.out.println(Integer.toHexString(opcode));
        //decode opcode
        switch (opcode & 0xF000) {
            case 0x0000:
                switch (opcode & 0x000F) {
                    case 0x0000:

                }

            case 0x1000:
                I = (char) (opcode & 0x0FFF);
                programCounter += 2;
                break;

            case 0x2000://2NNN : calls subroutine at NNN
                stack[stackPointer] = programCounter; //sets current address to stack
                stackPointer++;//increment stackpointer to avoid overwriting
                programCounter = (char) (opcode & 0x0FFF);
                break;

            case 0x3000: { //3XNN : Skips the next instruction if VX equals NN
                int x = (opcode & 0x0F00) >> 8;
                int nn = opcode & 0x00FF;
                if (V[x] == nn) {
                    programCounter += 4;
                } else {
                    programCounter += 2;
                }
            }
            break;

            case 0x4000: {
                int x = (opcode & 0x0F00) >> 8;
                int nn = opcode & 0x00FF;
                if (V[x] != nn) {
                    programCounter += 4;
                } else {
                    programCounter += 2;
                }
                break;
            }
            case 0x5000:
                break;

            case 0x6000: { //0x6XNN : Set VX to NN
                int x = (opcode & 0x0F00) >> 8;
                V[x] = (char) (opcode & 0x00FF);
                programCounter += 2;
                break;
            }
            case 0x7000: {//7XNN: Adds NN to VX
                int x = (opcode & 0x0F00) >> 8;
                int nn = opcode & 0x00FF;
                V[x] = (char) (V[x] + nn & 0xFF);
                programCounter += 2;
                break;
            }
            case 0x8000: //Contains more data in last nibble

                switch (opcode & 0x000F) {

                    case 0x0000: //Sets VX to the value of VY
                    default:
                        System.err.println("Opcode not Supported");
                        System.exit(0);
                        break;

                }

            case 0xA000: //ANNN : Sets I to NNN
                I = (char) (opcode & 0x0FFF);
                programCounter += 2;
                break;

            case 0xB000:
                break;

            case 0xC000:
                break;

            case 0xD000: //DXYN : Draws a sprite (X, Y) size (8, N). Sprite is located at I
                programCounter += 2;
                break;

            case 0xE000:
                break;

            case 0xF000:
                break;

            default:
                System.err.println("Opcode not Supported");
                System.exit(0);
        }
        //execute opcode
    }

    public byte[] getDisplay() {
        return display;
    }

    public boolean needRedraw() {
        return needRedraw;
    }

    public void removeDrawFlag() {
        needRedraw = false;
    }

    public void loadProgram(String file) {
        DataInputStream input = null;
        try {
            input = new DataInputStream(new FileInputStream(file));

            int offset = 0;
            while (input.available() > 0) {
                memory[0x200 + offset] = (char) (input.readByte() & 0xFF);
                offset++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public void loadFontset() {
        for (int i = 0; i < ChipData.fontset.length; i++) {
            memory[0x50 + i] = (char) (ChipData.fontset[i] & 0xFF);
        }
    }

}
