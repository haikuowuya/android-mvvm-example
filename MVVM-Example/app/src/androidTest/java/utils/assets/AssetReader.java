package utils.assets;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetReader {

    /**
     * Reads the string for a given name in the context's assets within the json folder.
     *
     * @param context  The context containing the assets.
     * @param fileName The file name of strings.
     * @return The content of the file.
     */
    public static String readJSON(Context context, String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("json/" + fileName + ".json")));

            StringBuilder stringBuilder = new StringBuilder();
            String readString;
            while ((readString = bufferedReader.readLine()) != null) {
                stringBuilder.append(readString);
            }

            bufferedReader.close();

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find json file <" + fileName + ">");
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not open json file <" + fileName + ">");
        }
    }
}
