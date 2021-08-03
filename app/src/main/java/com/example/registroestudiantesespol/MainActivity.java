package com.example.registroestudiantesespol;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText matricula, nombres, apellidos, edad, idCarrera, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matricula = (EditText)findViewById(R.id.editTextMatricula);
        nombres = (EditText)findViewById(R.id.editTextNombres);
        apellidos = (EditText)findViewById(R.id.editTextApellidos);
        edad = (EditText)findViewById(R.id.editTextEdad);
        idCarrera = (EditText)findViewById(R.id.editTextIDCarrera);
        correo = (EditText)findViewById(R.id.editTextCorreo);
    }

    public void registro (View view){
        database admin=new database(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();

        if (bd!=null) {

            String matriculaText = matricula.getText().toString();
            String nombresText = nombres.getText().toString();
            String apellidosText = apellidos.getText().toString();
            String edadText = edad.getText().toString();
            String idCarreraText = idCarrera.getText().toString();
            String correoText= correo.getText().toString();

            if (!matriculaText.isEmpty() || !nombresText.isEmpty() || !apellidosText.isEmpty() || !edadText.isEmpty() || !idCarreraText.isEmpty()|| !correoText.isEmpty()) {
                bd.execSQL("insert into estudiantes (id_estudiante,nombres,apellidos,edad,id_carrera,correo) "
                        + "values ("+matriculaText+",'"+nombresText+"','"+apellidosText+"',"
                        +edadText+",'"+idCarreraText+"','"+correoText+"')");
                bd.close();
                matricula.setText("");
                nombres.setText("");
                apellidos.setText("");
                edad.setText("");
                idCarrera.setText("");
                Toast.makeText(this, "Correctamente cargado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void eliminarEstudiante(View v){
        database admin=new database(this, "administracion", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        if(!matriculaText.isEmpty()){
            bd.execSQL("delete from estudiantes where id_estudiante="+matriculaText);
            bd.close();
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            idCarrera.setText("");
            correo.setText("");
            Toast.makeText(this,"Estudiante eliminado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Ingrese numero de matricula",Toast.LENGTH_SHORT).show();
        }
    }
    public void consultarMatricula (View view){
        database admin= new database(this,
                "administracion", null,1);
        SQLiteDatabase bd=admin.getReadableDatabase();
        String matriculaText = matricula.getText().toString();
        if(!matriculaText.isEmpty()){
            Cursor fila= bd.rawQuery(
                    "select nombres,apellidos,edad,id,carrera, correo from estudientes where"+
                            "id_estudiante="+matriculaText,null);

            if(fila.moveToFirst()){
                nombres.setText(fila.getString(0));
                apellidos.setText(fila.getString(1));
                edad.setText(fila.getString(2));
                idCarrera.setText(fila.getString(3));
                correo.setText(fila.getString(4));
                Toast.makeText(this,"Consulta exitosa",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"No existe un estudiante la matricula ingresada",
                        Toast.LENGTH_SHORT).show();

            }
            bd.close();


        }else{

            Toast.makeText(this,"Ingrese matricula",
                    Toast.LENGTH_SHORT).show();
        }
    }



    public void modificarInformacion(View v){
        database admin = new database(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String edadText = edad.getText().toString();
        String idCarreraText = idCarrera.getText().toString();
        String correoText = correo.getText().toString();

        if(!matriculaText.isEmpty()){
            if(nombresText.isEmpty()||apellidosText.isEmpty()||edadText.isEmpty()||idCarreraText.isEmpty()||correoText.isEmpty()){
                Toast.makeText(this, "Ingrese todos los datos.",
                        Toast.LENGTH_SHORT).show();
            }
            bd.execSQL("update estudiantes set id_estudiante="+matriculaText+",nombres='"
                    +nombresText+"',apellidos='"+apellidosText+"',edad="+edadText+",idCarrera='"
                    +idCarreraText+"',correo='"+correoText+"' where id_estudiante="+matriculaText);
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            idCarrera.setText("");
            correo.setText("");
            bd.close();
            Toast.makeText(this,"Se modificaron los datos.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ingrese una matrícula.", Toast.LENGTH_SHORT).show();
        }
    }

}