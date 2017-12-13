package com.tema3.tabladinamica;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Creación de una Tabla en tiempo de ejecución
 * 
 * @author lourdes
 * 
 */
public class TablaDinamica extends Activity {
	private TableLayout tblCabecera;
	private TableLayout tblCeldas;
	private TableRow.LayoutParams layoutId;
	private TableRow.LayoutParams layoutNombre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabla_dinamica);
		tblCabecera = (TableLayout) findViewById(R.id.tblCabecera);
		tblCeldas = (TableLayout) findViewById(R.id.tblCeldas);
		layoutId = new TableRow.LayoutParams(
				80,
				TableRow.LayoutParams.WRAP_CONTENT);
		layoutId.setMargins(0, 0, 0, 0);
		layoutNombre = new TableRow.LayoutParams(
				290,
				TableRow.LayoutParams.WRAP_CONTENT);
		crearCabecera();
		crearCeldas();
	}

	/**
	 * Método que crea la cabecera de una tabla en tiempo de ejecución
	 */
	private void crearCabecera() {
		// TODO Auto-generated method stub
		TableRow fila = new TableRow(this);

		TextView id = new TextView(this);
		id.setText(R.string.id);
		id.setLayoutParams(layoutId);
		id.setBackgroundResource(R.drawable.cabecera);

		TextView nombre = new TextView(this);
		nombre.setText(R.string.nombre);
		nombre.setLayoutParams(layoutNombre);
		nombre.setBackgroundResource(R.drawable.cabecera);
		/* Construir la cabecera */
		fila.addView(id);
		fila.addView(nombre);
		/* Añadir la cabecera a la Tabla */
		tblCabecera.addView(fila);

	}

	/**
	 * Método que crea las filas de una tabla en tiempo de ejecución
	 */
	private void crearCeldas() {
		TableRow fila;
		TextView id;
		TextView nombre;
		/*
		 * Recoger el array de string
		 */
		String[] nombres = getResources().getStringArray(R.array.alumnos);
		for (int i = 0; i < nombres.length; i++) {
			fila = new TableRow(this);
			id = new TextView(this);
			id.setText("" + i);
			id.setLayoutParams(layoutId);
			id.setBackgroundResource(R.drawable.celda);
			nombre = new TextView(this);
			nombre.setText(nombres[i]);
			nombre.setLayoutParams(layoutNombre);
			nombre.setBackgroundResource(R.drawable.celda);
			fila.addView(id);
			fila.addView(nombre);

			/* Añadir la cabecera a la Tabla */
			tblCeldas.addView(fila);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabla_dinamica, menu);
		return true;
	}

}
