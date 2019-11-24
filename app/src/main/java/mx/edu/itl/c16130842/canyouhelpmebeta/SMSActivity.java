package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SMSActivity extends AppCompatActivity {
    private String ARCHIVO_SMS_PATH;
    EditText edSMS;
    private String SMS_DESPLEGADO;

    @TargetApi(26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        ARCHIVO_SMS_PATH = getFilesDir().getPath().
                toString()+"/archivoSMS.txt";

        edSMS = findViewById(R.id.edSMS);
        Log.i("ERRORARCHIVO", "A punto de entrar en el archivo");
        if (!Files.exists(Paths.get(ARCHIVO_SMS_PATH))) {
            try (FileWriter fw = new FileWriter(ARCHIVO_SMS_PATH)) {
                String TEXTO_POR_DEFECTO = "Este es un mensaje desde la aplicación de ayuda." +
                        "Si lo has recibido es porque estoy en un apuro y te tengo agregado como" +
                        "persona confiable";
                fw.write(TEXTO_POR_DEFECTO);
                Log.i("ERRORARCHIVO", "Escritura exitosa");
                edSMS.setText(TEXTO_POR_DEFECTO);
            } catch (IOException e) {
                Log.i("ERRORARCHIVO", e.getMessage());
            }
        } else {
            try(BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_SMS_PATH))) {
                String lectura;
                SMS_DESPLEGADO=null;
                while((lectura=br.readLine())!=null) {
                    SMS_DESPLEGADO+=lectura;
                }
                edSMS.setText(SMS_DESPLEGADO);
            } catch (IOException e) {
                Log.i("ERRORARCHIVO", e.getMessage());
            }
        }
    }

    @TargetApi(19)
    public void btnGuardarSMSOnClick(View v) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_SMS_PATH))) {
            String NUEVO_SMS = edSMS.getText().toString();
            bw.write(NUEVO_SMS);
            ActualizarSMS();
        } catch (IOException e) {
            Log.i("ERRORARCHIVO", e.getMessage());
        }
    }

    @TargetApi(19)
    private void ActualizarSMS() {
        try(BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_SMS_PATH))) {
            String lectura;
            SMS_DESPLEGADO=null;
            while((lectura=br.readLine())!=null) {
                SMS_DESPLEGADO+=lectura;
            }
            edSMS.setText(SMS_DESPLEGADO);
        } catch (IOException e) {
            Log.i("ERRORARCHIVO", e.getMessage());
        }
    }
}
