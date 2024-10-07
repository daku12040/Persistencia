package com.example.persistencia;

import android.content.Context;
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
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

// DANIEL ESTEBAN KURE RODRIGUEZ - 1755146-2724
public class MainActivity extends AppCompatActivity {
    EditText etNombre, etApellido, etEmail, etTelefono, etDocumento, etEdad, etDireccion,etFechaNacimiento;
    CheckBox cbArte,cbMusica,cbCine,cbDeporte;
    RadioButton rbMasculino, rbFemenino;
    Spinner spinnerTelefono, spinnerEmail, spinnerEstadoCivil;
    int contadorID = 1;
    String[] tipoTelefono = {"Casa","Trabajo","Personal","Emergencia"};
    String[] tipoEmail = {"Casa","Trabajo","Personal","Emergencia"};
    String[] tipoEstadoCivil = {"Soltero", "Casado", "Unión Libre", "Matrimonio Religioso", "Matrimonio Civil"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contarUsuarios();


        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etDocumento = findViewById(R.id.etDocumento);
        etEdad = findViewById(R.id.etEdad);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etEmail = findViewById(R.id.etEmail);

        cbCine = findViewById(R.id.cbCine);
        cbArte = findViewById(R.id.cbArte);
        cbDeporte = findViewById(R.id.cbDeporte);
        cbMusica = findViewById(R.id.cbMusica);

        rbMasculino = findViewById(R.id.rbMasculino);
        rbFemenino = findViewById(R.id.rbFemenino);

        spinnerTelefono = findViewById(R.id.spinnerTelefono);
        ArrayAdapter<String> adaptadorTelefono = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipoTelefono);
        spinnerTelefono.setAdapter(adaptadorTelefono);

        spinnerEmail = findViewById(R.id.spinnerEmail);
        ArrayAdapter<String> adaptadorEmail = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipoEmail);
        spinnerEmail.setAdapter(adaptadorEmail);

        spinnerEstadoCivil = findViewById(R.id.spinnerEstadoCivil);
        ArrayAdapter<String> adaptadorCivil = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tipoEstadoCivil);
        spinnerEstadoCivil.setAdapter(adaptadorCivil);

    }


    public void guardar(View v){
        contarUsuarios();

        String datoNombre, datoApellido, datoEmail, datoTelefono, datoGenero, datoEdad, datoDocumento, datoDireccion, datoFechaNacimiento, tipoTelefono, tipoEmail, tipoEstadoCivil;
        String preferencias = "";
        datoNombre = etNombre.getText().toString();
        datoApellido = etApellido.getText().toString();
        datoDocumento = etDocumento.getText().toString();
        datoEdad = etEdad.getText().toString();
        datoTelefono = etTelefono.getText().toString();
        datoDireccion = etDireccion.getText().toString();
        datoFechaNacimiento = etFechaNacimiento.getText().toString();
        datoEmail = etEmail.getText().toString();

        if (datoNombre.isEmpty() || datoApellido.isEmpty() || datoEmail.isEmpty() || datoTelefono.isEmpty()
                || datoDocumento.isEmpty() || datoEdad.isEmpty() || datoDireccion.isEmpty() || datoFechaNacimiento.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
        }else if (!datoFechaNacimiento.matches("^\\d{8}$")) {
            Toast.makeText(this, "La fecha de nacimiento debe estar en formato DDMMYYYY", Toast.LENGTH_LONG).show();
        }else {

            int dia = Integer.parseInt(datoFechaNacimiento.substring(0, 2));
            int mes = Integer.parseInt(datoFechaNacimiento.substring(2, 4));
            int anio = Integer.parseInt(datoFechaNacimiento.substring(4, 8));
            int anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);


            if (dia < 1 || dia > 31) {
                Toast.makeText(this, "El día de la fecha de nacimiento debe estar entre 01 y 31", Toast.LENGTH_LONG).show();
            } else if (mes < 1 || mes > 12) {
                Toast.makeText(this, "El mes de la fecha de nacimiento debe estar entre 01 y 12", Toast.LENGTH_LONG).show();
            } else if (anio > anioActual) {
                Toast.makeText(this, "El año de la fecha de nacimiento no puede ser mayor al año actual", Toast.LENGTH_LONG).show();
            } else {

                if (cbArte.isChecked()) {
                    preferencias += "Arte ";
                }

                if (cbMusica.isChecked()) {
                    preferencias += "Musica ";
                }

                if (cbDeporte.isChecked()) {
                    preferencias += "Deporte ";
                }

                if (cbCine.isChecked()) {
                    preferencias += "Cine";
                }

                if (rbMasculino.isChecked()) {
                    datoGenero = "Masculino";
                } else {
                    datoGenero = "Femenino";
                }

                tipoTelefono = spinnerTelefono.getSelectedItem().toString();
                tipoEmail = spinnerEmail.getSelectedItem().toString();
                tipoEstadoCivil = spinnerEstadoCivil.getSelectedItem().toString();

                String datosUsuario = "ID: " + contadorID + "\n" +
                        "Nombre: " + datoNombre + "\n" +
                        "Apellido: " + datoApellido + "\n" +
                        "Documento: " + datoDocumento + "\n" +
                        "Edad: " + datoEdad + "\n" +
                        "Teléfono: " + datoTelefono + "\n" +
                        "Tipo de Teléfono: " + tipoTelefono + "\n" +
                        "Email: " + datoEmail + "\n" +
                        "Tipo de Email: " + tipoEmail + "\n" +
                        "Dirección: " + datoDireccion + "\n" +
                        "Fecha de Nacimiento: " + datoFechaNacimiento + "\n" +
                        "Género: " + datoGenero + "\n" +
                        "Estado Civil: " + tipoEstadoCivil + "\n" +
                        "Preferencias: " + preferencias + "\n\n";

                try {
                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("usuarios.txt", Context.MODE_APPEND));
                    archivo.write(datosUsuario);
                    archivo.flush();
                    archivo.close();

                    Toast.makeText(this, "Contacto guardado con código: " + contadorID, Toast.LENGTH_LONG).show();
                    this.contadorID += 1;

                    limpiarFormulario();

                } catch (Exception e) {
                    Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    public void limpiarFormulario(){
        etNombre.setText("");
        etApellido.setText("");
        etDocumento.setText("");
        etEdad.setText("");
        etTelefono.setText("");
        etDireccion.setText("");
        etFechaNacimiento.setText("");
        etEmail.setText("");
        rbMasculino.setChecked(true);
        cbCine.setChecked(false);
        cbMusica.setChecked(false);
        cbDeporte.setChecked(false);
        cbArte.setChecked(false);
    }




    public void iniciarViewBuscar(View view) {
        File archivo = getFileStreamPath("usuarios.txt");

        if (archivo.exists()) {
            Intent Intento1 = new Intent(this, Buscar.class);
            startActivity(Intento1);
        } else {
            Toast.makeText(this, "No hay usuarios registrados para buscar.", Toast.LENGTH_LONG).show();
        }
    }

    public void iniciarViewCalculadora(View view){
        Intent Intento3 = new Intent(this, Calculadora.class);
        startActivity(Intento3);
    }

    public void iniciarViewModificar(View view){
        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("usuarios.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();

            if (linea == null || linea.isEmpty()) {
                Toast.makeText(this, "No hay usuarios en la base de datos.", Toast.LENGTH_LONG).show();
            } else {
                Intent Intento = new Intent(this, Modificar.class);
                startActivity(Intento);
            }

            br.close();
            archivo.close();

        } catch (Exception e) {
            Toast.makeText(this, "No se encontraron usuarios para modificar.", Toast.LENGTH_LONG).show();
        }
    }


    public void contarUsuarios() {
        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("usuarios.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea;
            int contadorUsuarios = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("ID:")) {
                    contadorUsuarios++;
                }
            }

            br.close();
            archivo.close();

            this.contadorID = contadorUsuarios + 1;


        } catch (Exception e) {
            this.contadorID = 1;
        }
    }


//    public void guardar(View view){
//        String nombrearchivo = ed1.getText().toString();
//        String contenido = edMulti.getText().toString();
//
//        try{
//            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nombrearchivo, Context.MODE_PRIVATE));
//            archivo.write(contenido);
//            archivo.flush();
//            archivo.close();
//
//            Toast.makeText(this, "Se guardaron los datos", Toast.LENGTH_SHORT).show();
//        }catch(Exception e){
//            Toast.makeText(this,    "Error", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//    public void abrir(View view){
//        String nombreArchivo = ed1.getText().toString();
//
//        try {
//            InputStreamReader archivo = new InputStreamReader(openFileInput(nombreArchivo));
//            BufferedReader br = new BufferedReader(archivo);
//            String texto = "";
//            String linea = br.readLine();
//            while(linea != null){
//                texto += linea+"\n";
//                linea = br.readLine();
//            }
//            br.close();
//            archivo.close();
//            edMulti.setText(texto);
//        }catch (Exception e){
//
//        }
//    }
}