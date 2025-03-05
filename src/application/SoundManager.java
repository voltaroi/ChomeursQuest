package application;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static Clip clip;
    private static FloatControl volumeControl;
    
    public SoundManager(String filePath, float f) {
    	playSound(filePath);
    	setVolume(f);
    }
    
    public SoundManager() {
    	stopSound();
    }
    
    public static void playSound(String filePath) {
        try {
            // Arrêter le son en cours si nécessaire
            stopSound();

            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Créer et ouvrir le clip
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Récupérer le contrôle du volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(0.0f); // Volume normal (0 dB)
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY); // Joue en boucle
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    // Ajuster le volume (en dB)
    public static void setVolume(float volume) {
        if (volumeControl != null) {
            float newVolume = Math.min(volumeControl.getMaximum(), Math.max(volumeControl.getMinimum(), volume));
            volumeControl.setValue(newVolume);
        }
    }

    // Arrêter la lecture du son
    public static void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}