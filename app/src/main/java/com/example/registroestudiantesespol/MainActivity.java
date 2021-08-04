package com.example.registroestudiantesespol;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Se definen las variables de los componentes EdidText
    EditText cedula, nombres, apellidos, edad, carrera, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Se inicializan las variables
        cedula = (EditText)findViewById(R.id.edtCedula);
        nombres = (EditText)findViewById(R.id.edtNombres);
        apellidos = (EditText)findViewById(R.id.edtApellidos);
        edad = (EditText)findViewById(R.id.edtEdad);
        carrera = (EditText)findViewById(R.id.edtCarrera);
        correo = (EditText)findViewById(R.id.edtCorreo);
    }
//Función registro para botón Registrar (Agrega un registro a la tabla estudiantes de la base de datos)
    public void registro(View view){
        //Se establece la conexión con la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null,1);
        //En modo writable
        SQLiteDatabase bd= admin.getWritableDatabase();
        //Se obtiene los valores ingresados en los EdidText
        String cedulaText= cedula.getText().toString();
        String nombresText= nombres.getText().toString();
        String apellidosText= apellidos.getText().toString();
        String edadText= edad.getText().toString();
        String carreraText= carrera.getText().toString();
        String correoText= correo.getText().toString();
        //Se valida que no haya campos vacíos
        if(!cedulaText.isEmpty()||!nombresText.isEmpty()||!apellidosText.isEmpty()||!edadText.isEmpty()||!carreraText.isEmpty()){
            //Se realiza la operación de insertar el registro con los datos proporcionados
            bd.execSQL("insert into estudiantes (id_estudiante, nombres,apellidos,edad, id_carrera,correo)"+"values ("+cedulaText+",'"+nombresText+"','"+apellidosText+"',"+edadText+",'"+carreraText+"','"+correoText+"')");
            bd.close();
            //Se resetea a strings vacíos los EdidText
            cedula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            carrera.setText("");
            correo.setText("");
            //Alerta toast con mensaje de operación exitosa
            Toast.makeText(this,"Estudiante Registrado en Admisiones ESPOL", Toast.LENGTH_SHORT).show();
        }else{
            //Alerta toast con mensaje de error
            Toast.makeText(this,"Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }
    //Función eliminarEstudiante para botón Eliminar (Elimina un registro a la tabla estudiantes de la base de datos según su cédula)
    public void eliminarEstudiante(View v){
        //Se establece la conexión con la base de datos
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //En modo writable
        SQLiteDatabase bd=admin.getWritableDatabase();
        //Se obtiene el valor ingresado en el EdidText cédula
        String cedulaText = cedula.getText().toString();
        //Se valida que el campo no este vacío
        if(!cedulaText.isEmpty()){
            //Se realiza la operación delete según la cédula utilizando where
            bd.execSQL("delete from estudiantes where id_estudiante="+cedulaText);
            bd.close();
            //Se resetean los campos
            cedula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            carrera.setText("");
            correo.setText("");
            //Alerta toast con mensaje de operación exitosa
            Toast.makeText(this,"Estudiante eliminado de Admisiones ESPOL",Toast.LENGTH_SHORT).show();
        }else{
            //Alerta toast con mensaje de error
            Toast.makeText(this,"Error, ingrese numero de matricula",Toast.LENGTH_SHORT).show();
        }
    }
    //Función consultarCedula para botón Consultar (Realiza consulta a un registro de la tabla estudiantes de la base de datos según su cédula)
    public void consultarCedula (View view){
        //Se establece la conexión con la base de datos
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this, "administracion", null,1);
        //En modo readable (no se modifica)
        SQLiteDatabase bd=admin.getReadableDatabase();
        //Se obtiene el valor ingresado en el EdidText cédula
        String cedulaText = cedula.getText().toString();
        //Se valida que el campo no este vacío
        if(!cedulaText.isEmpty()){
            //Se realiza la operación select según la cédula utilizando where
            Cursor fila= bd.rawQuery(
                    "select nombres,apellidos,edad, id_carrera, correo from estudiantes where id_estudiante="+cedulaText,null);
            //Se utiliza el cursor para validar si la cedula existe
            if(fila.moveToFirst()){
                nombres.setText(fila.getString(0));
                apellidos.setText(fila.getString(1));
                edad.setText(fila.getString(2));
                carrera.setText(fila.getString(3));
                correo.setText(fila.getString(4));
                //Alerta toast con mensaje de operación exitosa
                Toast.makeText(this,"Consulta exitosa",
                        Toast.LENGTH_SHORT).show();
            }else{
                //Alerta toast con mensaje de error
                Toast.makeText(this,"No existe un estudiante la cédula ingresada",
                        Toast.LENGTH_SHORT).show();

            }
            bd.close();
        }else{
            //Alerta toast con mensaje de campos vacíos
            Toast.makeText(this,"Ingrese matricula",
                    Toast.LENGTH_SHORT).show();
        }
    }

//Función modificarInformacion para botón modificar (Actualiza un registro de la tabla estudiantes de la base de datos con los valores ingresados según su cédula)

    public void modificarInformacion(View v){
        //Se establece la conexión con la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //En modo writable
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Se obtiene los valores ingresados en los EdidText
        String cedulaText = cedula.getText().toString();
        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String edadText = edad.getText().toString();
        String carreraText = carrera.getText().toString();
        String correoText = correo.getText().toString();
        //Se valida que los campos no estén vacíos
        if(!cedulaText.isEmpty()){
            if(nombresText.isEmpty()||apellidosText.isEmpty()||edadText.isEmpty()||carreraText.isEmpty()||correoText.isEmpty()){
                Toast.makeText(this, "Ingrese todos los datos.",
                        Toast.LENGTH_SHORT).show();
            }
            //Se realiza la operación update según la cédula utilizando where (actualizando a los datos ingresados)
            bd.execSQL("update estudiantes set id_estudiante="+cedulaText+",nombres='"
                    +nombresText+"',apellidos='"+apellidosText+"',edad="+edadText+",id_carrera='"
                    +carreraText+"',correo='"+correoText+"' where id_estudiante="+cedulaText);
            //Se resetean los campos
            cedula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            carrera.setText("");
            correo.setText("");
            bd.close();
            //Alerta toast con mensaje de operación exitosa
            Toast.makeText(this,"Se modificaron los datos.", Toast.LENGTH_SHORT).show();
        }else{
            //Alerta toast con mensaje de error
            Toast.makeText(this, "Ingrese una matrícula.", Toast.LENGTH_SHORT).show();
        }
    }

}
