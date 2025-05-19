package com.example.base64fileencoder;

import android.util.Base64;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

import java.io.*;

@DesignerComponent(
        version = 1,
        description = "Converts a file to Base64 and returns it as a string.",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = ""
)
@SimpleObject(external = true)
public class Base64FileEncoder extends AndroidNonvisibleComponent {

    public Base64FileEncoder(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Convert file at given path to Base64 string.")
    public void FileToBase64(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                GotError("File does not exist: " + path);
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            fis.close();
            String encoded = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
            GotBase64(encoded);
        } catch (Exception e) {
            GotError("Error reading file: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Called when the Base64 string is ready.")
    public void GotBase64(String base64) {
        EventDispatcher.dispatchEvent(this, "GotBase64", base64);
    }

    @SimpleEvent(description = "Called when an error occurs.")
    public void GotError(String error) {
        EventDispatcher.dispatchEvent(this, "GotError", error);
    }
}