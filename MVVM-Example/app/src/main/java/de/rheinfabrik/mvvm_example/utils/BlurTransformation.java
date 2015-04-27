package de.rheinfabrik.mvvm_example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

/**
 * See https://gist.githubusercontent.com/ryanbateman/6667995/raw/fae67e5fc5e1270fc4604a7b75caa5bf493224bc/gistfile1.txt
 */
public class BlurTransformation implements Transformation {

    RenderScript rs;

    public BlurTransformation(Context context) {
        super();

        rs = RenderScript.create(context);
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {

        // Create another bitmap that will hold the results of the filter.
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap);

        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        // Set the blur radius
        script.setRadius(10);

        // Start the ScriptIntrinsicBlur
        script.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        return blurredBitmap;
    }

    @Override
    public String key() {
        return "blur";
    }

}