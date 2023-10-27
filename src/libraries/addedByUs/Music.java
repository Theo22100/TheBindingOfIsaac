package libraries.addedByUs;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * The Music Class is a class that can play sound from a file. 
 * It has five function related to that, play, pause, stop, changeVolume, resume.
 */
public class Music {
	// to store current position
    Long currentFrame;
    Clip clip;
      
    // current status of clip
    String status;
      
    AudioInputStream audioInputStream;
    String filePath;

    /**
     * Constructs a new Music, whose music file is referred with the filePath argument.
     * 
     * @param filePath the music imagePath
     */
    public Music(String filePath)
    {
    	try {
    		this.filePath = filePath;
        	// create AudioInputStream object
        	audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        	// create clip reference
        	clip = AudioSystem.getClip();
          
        	// open audioInputStream to the clip
        	clip.open(audioInputStream);
          
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        	clip.stop();
        	status = "paused";
        	
    	} catch (Exception ex) {
            ex.printStackTrace();
          }
    }
    
    /**
     * Plays the music.
     */
    public void play() 
    {
        clip.start();
        status = "play";
    }
    
    /**
     * Pauses the music if it's not already paused.
     */
    public void pause() 
    {
        if (status.equals("paused")) return;
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    /**
     * Resumes the music if it's paused.
     */
    public void resumeAudio()
    {
        if (status.equals("play")) return;
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }
      
    /**
     * Stops the music and closes the music.
     */
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    
    /**
     * Reset the time stamp to the start of the music.
     */
    public void resetAudioStream()
    {
        try {
			audioInputStream = AudioSystem.getAudioInputStream(
			new File(filePath).getAbsoluteFile());
	        clip.open(audioInputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Changes the volume to the new volume.
     * @param volume the new volume of the music
     */
    public void changeVolume(float volume) {
    	
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	volume = Math.min(volume, gainControl.getMaximum());
    	gainControl.setValue(volume);
    }
    
    
    /**
     * Returns the music volume value.
     * @return the music volume value.
     */
    public float getVolume() {
    	return ((FloatControl) (clip.getControl(FloatControl.Type.MASTER_GAIN)) ).getValue();
    }
    
    
    /**
     * Returns {@code true} if the musics are the same, {@code false} otherwise.
     * @param m the other music
     * @return {@code true} if the musics are the same, {@code false} otherwise.
     */
    public boolean equals(Music m) {
    	if (m.filePath.equals(filePath)) return true;
		return false;
    }
    
    /**
     * Returns the music file path.
     * @return the music file path.
     */
    public String getMusicPath() {
    	return filePath;
    }
    
    /**
     * Returns the music status.
     * @return the music status.
     */
    public String getStatus() {
    	if (status.equals("play")) return "play";
    	return "paused";
    }
}
