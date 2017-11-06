import javax.sound.sampled.*;
import java.io.*;

public class AudioConverter {

    public static void convertMp3ToWav(String path) throws Exception {
        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(new File(path));

        byte[] sourceBytes = inputStreamToByteArray(mp3Stream);

        final ByteArrayInputStream bais = new ByteArrayInputStream(sourceBytes);
        final AudioInputStream sourceAIS = AudioSystem.getAudioInputStream(bais);
        AudioFormat sourceFormat = sourceAIS.getFormat();
        AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);
        final AudioInputStream convert1AIS = AudioSystem.getAudioInputStream(convertFormat, sourceAIS);


        AudioSystem.write(convert1AIS, AudioFileFormat.Type.WAVE, new File("out.wav"));
    }

    public static void changeBitrate(File source, File output) {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(source);
            AudioInputStream convert = AudioSystem.getAudioInputStream(format, in);
            AudioSystem.write(convert, AudioFileFormat.Type.WAVE, output);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printFileType(String path) throws Exception {
        AudioFileFormat inputFileFormat = AudioSystem.getAudioFileFormat(new File(path));
        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(new File(path));

        AudioFormat audioFormat = mp3Stream.getFormat();

        System.out.println("File Format Type: " + inputFileFormat.getType());
        System.out.println("File Format String: " + inputFileFormat.toString());
        System.out.println("File length: " + inputFileFormat.getByteLength());
        System.out.println("Frame length: " + inputFileFormat.getFrameLength());
        System.out.println("Channels: " + audioFormat.getChannels());
        System.out.println("Encoding: " + audioFormat.getEncoding());
        System.out.println("Frame Rate: " + audioFormat.getFrameRate());
        System.out.println("Frame Size: " + audioFormat.getFrameSize());
        System.out.println("Sample Rate: " + audioFormat.getSampleRate());
        System.out.println("Sample size (bits): " + audioFormat.getSampleSizeInBits());
        System.out.println("Big endian: " + audioFormat.isBigEndian());
        System.out.println("Audio Format String: " + audioFormat.toString());
    }

    private static byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

}
