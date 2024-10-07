package com.example.persistencia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Modificar extends AppCompatActivity {

    EditText etNombre, etApellido, etEmail, etTelefono, etDocumento, etEdad, etDireccion,etFechaNacimiento, etCodigo;
    Button btnBuscar;
    CheckBox cbArte,cbMusica,cbCine,cbDeporte;
    RadioButton rbMasculino, rbFemenino;
    Spinner spinnerTelefono, spinnerEmail, spinnerEstadoCivil;
    String[] tipoTelefono = {"Casa","Trabajo","Personal","Emergencia"};
    String[] tipoEmail = {"Casa","Trabajo","Personal","Emergencia"};
    String[] tipoEstadoCivil = {"Soltero", "Casado", "Unión Libre", "Matrimonio Religioso", "Matrimonio Civil"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBuscar = findViewById(R.id.btnBuscarModificar);

        etCodigo = findViewById(R.id.etCodigoModificar);
        etNombre = findViewById(R.id.etNombreModificar);
        etApellido = findViewById(R.id.etApellidoModificar);
        etDocumento = findViewById(R.id.etDocumentoModificar);
        etEdad = findViewById(R.id.etEdadModificar);
        etTelefono = findViewById(R.id.etTelefonoModificar);
        etDireccion = findViewById(R.id.etDireccionModificar);
        etFechaNacimiento = findViewById(R.id.etFechaNacimientoModificar);
        etEmail = findViewById(R.id.etEmailModificar);

        cbCine = findViewById(R.id.cbCineModificar);
        cbArte = findViewById(R.id.cbArteModificar);
        cbDeporte = findViewById(R.id.cbDeporteModificar);
        cbMusica = findViewById(R.id.cbMusicaModificar);

        rbMasculino = findViewById(R.id.rbMasculinoModificar);
        rbFemenino = findViewById(R.id.rbFemeninoModificar);

        spinnerTelefono = findViewById(R.id.spinnerTelefonoModificar);
        ArrayAdapter<String> adaptadorTelefono = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoTelefono);
        spinnerTelefono.setAdapter(adaptadorTelefono);

        spinnerEmail = findViewById(R.id.spinnerEmailModificar);
        ArrayAdapter<String> adaptadorEmail = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoEmail);
        spinnerEmail.setAdapter(adaptadorEmail);

        spinnerEstadoCivil = findViewById(R.id.spinnerEstadoCivilModificar);
        ArrayAdapter<String> adaptadorCivil = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoEstadoCivil);
        spinnerEstadoCivil.setAdapter(adaptadorCivil);

    }



    public void buscarUsuarioPorID(View view) {

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
                    spinnerTelefono.setSelection(obtenerPosicionSpinner(spinnerTelefono, obtenerValor(br.readLine(), "Tipo de Teléfono: ")));
                    etEmail.setText(obtenerValor(br.readLine(), "Email: "));
                    spinnerEmail.setSelection(obtenerPosicionSpinner(spinnerEmail, obtenerValor(br.readLine(), "Tipo de Email: ")));
                    etDireccion.setText(obtenerValor(br.readLine(), "Dirección: "));
                    etFechaNacimiento.setText(obtenerValor(br.readLine(), "Fecha de Nacimiento: "));

                    String genero = obtenerValor(br.readLine(), "Género: ");
                    if (genero.equals("Masculino")) {
                        rbMasculino.setChecked(true);
                    } else if (genero.equals("Femenino")) {
                        rbFemenino.setChecked(true);
                    }

                    String estadoCivil = obtenerValor(br.readLine(), "Estado Civil: ");
                    spinnerEstadoCivil.setSelection(obtenerPosicionSpinner(spinnerEstadoCivil, estadoCivil));

                    String preferencias = obtenerValor(br.readLine(), "Preferencias: ");
                    cbArte.setChecked(preferencias.contains("Arte"));
                    cbMusica.setChecked(preferencias.contains("Musica"));
                    cbCine.setChecked(preferencias.contains("Cine"));
                    cbDeporte.setChecked(preferencias.contains("Deporte"));

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




    public void modificarUsuario(View view) {
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
        } else if (!datoFechaNacimiento.matches("^\\d{8}$")) {
            Toast.makeText(this, "La fecha de nacimiento debe estar en formato DDMMYYYY", Toast.LENGTH_LONG).show();
        } else {

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

                String codigo = etCodigo.getText().toString();

                if (codigo.isEmpty()) {
                    Toast.makeText(this, "Por favor, ingrese un código válido", Toast.LENGTH_LONG).show();
                    return;
                }

                ArrayList<String> nuevaBaseDatos = new ArrayList<>();
                boolean encontrado = false;

                try {
                    InputStreamReader archivo = new InputStreamReader(openFileInput("usuarios.txt"));
                    BufferedReader br = new BufferedReader(archivo);
                    String linea;

                    while ((linea = br.readLine()) != null) {
                        if (linea.startsWith("ID: " + codigo)) {
                            for (int i = 0; i < 13; i++) {
                                br.readLine();
                            }

                            String nuevaLinea = "ID: " + codigo + "\n" +
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
                            nuevaBaseDatos.add(nuevaLinea);
                            encontrado = true;
                        } else {
                            nuevaBaseDatos.add(linea + "\n");
                        }
                    }
                    br.close();
                    archivo.close();

                    if (encontrado) {
                        OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("usuarios.txt", Context.MODE_PRIVATE));
                        for (String nuevaLinea : nuevaBaseDatos) {
                            writer.write(nuevaLinea);
                        }
                        writer.close();
                        Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "No se encontró ningún contacto con el ID: " + codigo, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(this, "Error al modificar el archivo", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public int obtenerPosicionSpinner(Spinner spinner, String valor) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(valor)) {
                return i;
            }
        }
        return 0;
    }


    public void cancelar(View view) {
        setResult(MainActivity.RESULT_CANCELED);
        finish();
    }

}
