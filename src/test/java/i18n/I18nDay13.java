package i18n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nDay13 extends Commun {

    @Test
    public void etape1_exemple() throws URISyntaxException, IOException {
        List<String> inputs = lectureDuFichier(this, true);
        assertEquals(47, traitement(inputs));
    }

    @Test
    public void etape1() throws IOException, URISyntaxException {
        List<String> inputs = lectureDuFichier(this, false);
        assertEquals(15210, traitement(inputs));
    }

    public int traitement(List<String> inputs) {
        int resultat = 0;

        boolean finmot = false;
        List<String> mots = new ArrayList<>();
        List<String> puzzle = new ArrayList<>();
        for (String input : inputs) {
            if (!finmot) {
                if (input.isEmpty()) {
                    finmot = true;
                } else {
                    mots.add(input);
                }
            } else {
                puzzle.add(input.trim());
            }
        }
        List<String> motsDecodes = decodeLesMots(mots);

        for (int i = 0; i < motsDecodes.size(); i++) {
            for (String s : puzzle) {
                if (motsDecodes.get(i).matches(s)) { //on vérifie si le mot matche le pattern.
                    resultat += i + 1; //on compte à partir de 1 et pas à partir de 0.
                    break;
                }
            }
        }

        System.out.println(this.getClass().getSimpleName() + " " + name + " : " + resultat);
        return resultat;
    }

    public List<String> decodeLesMots(List<String> motsEnHexa) {
        List<String> motsDecodes = new ArrayList<>();
        for (String hex : motsEnHexa) {
            byte[] bytes = hexStringToByteArray(hex);
            Charset encoding = detecteEncodage(bytes);
            String decoded = new String(bytes, encoding);
            motsDecodes.add(decoded.replaceAll("[^\\p{L}|,]", ""));
        }
        return motsDecodes;
    }

    private Charset detecteEncodage(byte[] bytes) {
        if (bytes.length >= 3 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
            return StandardCharsets.UTF_8; // UTF-8 avec BOM
        } else if (bytes.length >= 2 && bytes[0] == (byte) 0xFE && bytes[1] == (byte) 0xFF) {
            return StandardCharsets.UTF_16BE; // UTF-16 Big Endian
        } else if (bytes.length >= 2 && bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xFE) {
            return StandardCharsets.UTF_16LE; // UTF-16 Little Endian
        }
        // Si pas de BOM, on suppose UTF-8, sinon on tente Latin-1
        String sample = new String(bytes, StandardCharsets.UTF_8);
        if (sample.contains("�")) { // Test de corruption
            return StandardCharsets.ISO_8859_1;
        }
        return StandardCharsets.UTF_8;
    }

    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}