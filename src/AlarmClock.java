import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

import static java.time.LocalTime.now;

public class AlarmClock implements Runnable{

    private final LocalTime alarmTime;
    private final String filePath;
    private final Scanner sc;


    AlarmClock(LocalTime alarmTime, String filePath, Scanner sc){
        this.alarmTime = alarmTime;
        this.filePath = filePath;
        this.sc = sc;
    }

    @Override
    public void run() {

        while(now().isBefore(alarmTime)){
           try{
               Thread.sleep(1000);

               LocalTime now = now();
               System.out.printf("\r%02d:%02d:%02d" ,
                                 now().getHour(),
                                 now().getMinute(),
                                 now().getSecond());
           }
           catch(InterruptedException e){
               throw new RuntimeException("Thread was Interrupted");
           }
        }
        System.out.println("\n 🔔‼️Alarm Noises ‼️🔔");
        Toolkit.getDefaultToolkit().beep();
        playSound(filePath);
    }
    private void playSound(String filePath){
        File audioFile = new File(filePath);

        try(AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)){
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("Press ENTER to *stop* the ⏰");
            sc.nextLine();
            clip.stop();

            sc.close();
        }
        catch(UnsupportedAudioFileException e){
            System.out.println("Audio File format is not suppported");

        }
        catch(LineUnavailableException e){
            System.out.println();
        }
        catch(IOException e){
            System.out.println("Error reading the audio file");
        }

    }
}
