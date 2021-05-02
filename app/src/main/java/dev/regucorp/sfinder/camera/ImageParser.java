package dev.regucorp.sfinder.camera;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Guillaume ROUSSIN on 04/11/20
 */
public class ImageParser {

    private static final int FLAGS = Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP;

    public static String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

        return Base64.encodeToString(stream.toByteArray(), FLAGS);
    }
}
