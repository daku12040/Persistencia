package com.example.persistencia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Buscar extends AppCompatActivity {
    EditText etCodigo, etNombre, etApellido, etDocumento, etEdad, etTipoTelefono, etDireccion, etFechaNacimiento, etTipoEmail, etEstadoCivil, etTelefono, etVolver, etEmail, etGustos, etGenero;
    Button btnBuscar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCodigo = findViewById(R.id.etCodigo);
        etNombre = findViewById(R.id.etNombreBuscar);
        etApellido = findViewById(R.id.etApellidoBuscar);
        etDocumento = findViewById(R.id.etDocumentoBuscar);
        etEdad = findViewById(R.id.etEdadBuscar);
        etEmail = findViewById(R.id.etEmailBuscar);
        etTipoEmail = findViewById(R.id.etTipoEmailBuscar);
        etTelefono = findViewById(R.id.etTelefonoBuscar);
        etTipoTelefono = findViewById(R.id.etTipoTelefonoBuscar);
        etDireccion = findViewById(R.id.etDireccionBuscar);
        etFechaNacimiento = findViewById(R.id.etFechaNacimientoBuscar);
        etEstadoCivil = findViewById(R.id.etEstadoCivilBuscar);
        etGenero = findViewById(R.id.etGeneroBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        etGustos = findViewById(R.id.etGustosBuscar);
    }


    public void buscarPorID(View v) {
        String codigo = etCodigo.getText().toString();

        if (codigo.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un código válido", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("usuarios.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea;
            boolean encontrado = false;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("ID: " + codigo)) {
                    encontrado = true;
                    etNombre.setText(obtenerValor(br.readLine(), "Nombre: "));
                    etApellido.setText(obtenerValor(br.readLine(), "Apellido: "));
                    etDocumento.setText(obtenerValor(br.readLine(), "Documento: "));
                    etEdad.setText(obtenerValor(br.readLine(), "Edad: "));
                    etTelefono.setText(obtenerValor(br.readLine(), "Teléfono: "));
                    etTipoTelefono.setText(obtenerValor(br.readLine(), "Tipo de Teléfono: "));
                    etEmail.setText(obtenerValor(br.readLine(), "Email: "));
                    etTipoEmail.setText(obtenerValor(br.readLine(), "Tipo de Email: "));
                    etDireccion.setText(obtenerValor(br.readLine(), "Dirección: "));
                    etFechaNacimiento.setText(obtenerValor(br.readLine(), "Fecha de Nacimiento: "));
                    etGenero.setText(obtenerValor(br.readLine(), "Género: "));
                    etEstadoCivil.setText(obtenerValor(br.readLine(), "Estado Civil: "));
                    etGustos.setText(obtenerValor(br.readLine(),"Preferencias: "));

                    break;
                }
            }

            br.close();
            archivo.close();

            if (!encontrado) {
                Toast.makeText(this, "No se encontró ningún contacto con el ID: " + codigo, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al leer el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String obtenerValor(String linea, String clave) {
        if (linea != null && linea.startsWith(clave)) {
            return linea.substring(clave.length()).trim();
        }
        return "";
    }

    public void volver(View view) {
        finish();
    }
}