package com.example.persistencia;

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

public class Calculadora extends AppCompatActivity {

    EditText etDisplay1;
    Button btnCE, btnC, btnBorrar, btnPunto,btnDivision, btn7, btn8, btn9, btnMultiplicacion, btn4, btn5, btn6, btnResta, btn1, btn2, btn3, btnSuma, btn0, btnIgual ;
    float num1, num2, resultado;
    String valorDisplay, operacion;
    boolean operado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculadora);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etDisplay1 = findViewById(R.id.etDisplay);
        valorDisplay = etDisplay1.getText().toString();
        btnCE = findViewById(R.id.btnCE);
        btnC = findViewById(R.id.btnC);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnDivision = findViewById(R.id.btnDivision);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnMultiplicacion = findViewById(R.id.btnMultiplicacion);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btnResta = findViewById(R.id.btnResta);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnSuma = findViewById(R.id.btnSuma);
        btn0 = findViewById(R.id.btn0);
        btnIgual = findViewById(R.id.btnIgual);
        operacion = "n";
        btnPunto = findViewById(R.id.btnPunto);

    }

    public void hola(View view){
        valorDisplay = etDisplay1.getText().toString();
        if (view==btnCE){
            etDisplay1.setText("0");
        } else if (view==btnC){
            num1 = 0;
            num2 = 0;
            operacion = "n";
            etDisplay1.setText("0");

        } else if (view==btnBorrar){
            borrarUltimoCaracter();
        } else if (view==btnDivision){
            verificarNumeroAntesDeGuardar();
            num1 = Float.parseFloat(valorDisplay);
            etDisplay1.setText("0");
            operacion = "Division";
        } else if (view==btn7){
            if(ultimoCaracter0()){
                etDisplay1.setText("7");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"7"));
            }
        } else if (view==btn8){
            if(ultimoCaracter0()){
                etDisplay1.setText("8");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"8"));
            }
        } else if (view==btn9){
            if(ultimoCaracter0()){
                etDisplay1.setText("9");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"9"));
            }
        } else if (view==btnMultiplicacion){
            verificarNumeroAntesDeGuardar();
            num1 = Float.parseFloat(valorDisplay);
            etDisplay1.setText("0");
            operacion = "Multiplicacion";
        } else if (view==btn4){
            if(ultimoCaracter0()){
                etDisplay1.setText("4");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"4"));
            }
        } else if (view==btn5){
            if(ultimoCaracter0()){
                etDisplay1.setText("5");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"5"));
            }
        } else if (view==btn6){
            if(ultimoCaracter0()){
                etDisplay1.setText("6");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"6"));
            }
        } else if (view==btnResta){
            verificarNumeroAntesDeGuardar();
            num1 = Float.parseFloat(valorDisplay);
            etDisplay1.setText("0");
            operacion = "Resta";
        } else if (view==btn1){
            if(ultimoCaracter0()){
                etDisplay1.setText("1");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"1"));
            }
        } else if (view==btnPunto){
            if (!valorDisplay.contains(".")) {
                etDisplay1.setText(concatenar(valorDisplay, "."));
            }
        } else if (view==btn2){
            if(ultimoCaracter0()){
                etDisplay1.setText("2");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"2"));
            }
        } else if (view==btn3){
            if(ultimoCaracter0()){
                etDisplay1.setText("3");
            }else{
                etDisplay1.setText(concatenar(valorDisplay,"3"));
            }
        } else if (view==btnSuma){
            verificarNumeroAntesDeGuardar();
            num1 = Float.parseFloat(valorDisplay);
            etDisplay1.setText("0");
            operacion = "Suma";
        } else if (view==btn0){
            if(!ultimoCaracter0()) {
                etDisplay1.setText(concatenar(valorDisplay,"0"));
            }
        } else if (view==btnIgual) {

            if (!operacion.equals("n")) {
                verificarNumeroAntesDeGuardar();
                    num2 = Float.parseFloat(valorDisplay);

                switch (operacion) {
                    case "Suma":
                        resultado = num1 + num2;
                        break;

                    case "Resta":
                        resultado = num1 - num2;
                        break;

                    case "Multiplicacion":
                        resultado = num1 * num2;
                        break;

                    case "Division":
                        if(num2==0){
                            resultado = 0;
                        }else{
                            resultado = num1 / num2;
                        }

                        break;
                }

                this.operado = true;
                etDisplay1.setText(String.format("%s", resultado));
                Toast.makeText(this, resultado + "", Toast.LENGTH_SHORT).show();
            }
        }


        //Toast.makeText(this, test1, Toast.LENGTH_SHORT).show();



    }

    public boolean ultimoCaracter0(){
        return (valorDisplay.charAt(valorDisplay.length() - 1) == '0') && (valorDisplay.length()==1);

    }

    public StringBuilder concatenar(String a, String b) {
        return new StringBuilder(a).append(b);
    }


    public void borrarUltimoCaracter(){
        valorDisplay = etDisplay1.getText().toString();
        if (valorDisplay.length()==1){
            etDisplay1.setText("0");
        }else{
            etDisplay1.setText(valorDisplay.substring(0,valorDisplay.length()-1));
            valorDisplay = etDisplay1.getText().toString();
            if(valorDisplay.charAt(valorDisplay.length() - 1) == '.'){
                etDisplay1.setText(valorDisplay.substring(0,valorDisplay.length()-1));
            }
        }
    }

    public void verificarNumeroAntesDeGuardar() {
        valorDisplay = etDisplay1.getText().toString();

        if (valorDisplay.charAt(valorDisplay.length() - 1) == '.') {
            valorDisplay = concatenar(valorDisplay, "0").toString();
            etDisplay1.setText(valorDisplay);
        }
    }


    public void cerrar(View view){
        finish();
    }
}