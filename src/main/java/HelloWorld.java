import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static spark.Spark.*;

public class HelloWorld {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");

        //Audio converter from mp3 to wav
//        try {
//            AudioConverter.printFileType("sample_audio.mp3");
//            AudioConverter.convertMp3ToWav("sample_audio.mp3");
//            AudioConverter.changeBitrate(new File("out.wav"), new File("new-out.wav"));
//            AudioConverter.printFileType("new-out.wav");
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("file:data/en-us-adapt");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("intro.wav"));

        recognizer.startRecognition(stream);
        SpeechResult result;

        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());

//            System.out.println("List of recognized words and their times:");
//            for (WordResult r : result.getWords()) {
//                System.out.println(r);
//            }
//
//            System.out.println("Best 3 hypothesis:");
//            for (String s : result.getNbest(3))
//                System.out.println(s);
        }
        recognizer.stopRecognition();

    }
}
