package com.ciaa_poncho.lucashour.transmisortcpgyroscope;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    // Primero se declaran los títulos e íconos para el Navigation drawer List View
    // Los títulos e íconos permanecen en un arreglo

    String TITLES[] = {"Comunicación","Configuración"};
    int ICONS[] = {R.drawable.ic_action,R.drawable.ic_action};

    private Toolbar toolbar;                            // Declaración del Toolbar
    RecyclerView recyclerView;                          // Declaración del recyclerView
    RecyclerView.Adapter adapter;                       // Declaración del adapter para recyclerView
    RecyclerView.LayoutManager layoutManager;           // Declaración del Layout Manager como linear layout manager
    DrawerLayout drawer;                                // Declaración del DrawerLayout

    ActionBarDrawerToggle drawerToggle;                  // Declaración del Action Bar drawer Toggle

    /**Inicialización de Activity*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /* Configuración del toolbar.*/
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        /*Configuración del recyclerView*/
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Asignación a recyclerView de la vista XML correspondiente
        recyclerView.setHasFixedSize(true);                            // Informarle al sistema que la lista de elementos tienen tamaño arreglado
        /*Creación del adapter para el recyclerView*/
        adapter = new MyAdapter(TITLES,ICONS);
        /*Configuración del adapter del recyclerView*/
        recyclerView.setAdapter(adapter);

        final GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
       /*Configuración del Fragment visible por defecto*/
        FragmentManager fragmentManager = getFragmentManager();
        BehaviourFragment fragmentA = new BehaviourFragment();
        fragmentManager.beginTransaction().replace(R.id.content, fragmentA).commit();

       /*Configuración de escucha de clicks en las secciones del drawer.*/
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if ((child != null) && (gestureDetector.onTouchEvent(motionEvent))) {
                    drawer.closeDrawers();
                    FragmentManager localFragmentManager = getFragmentManager();
                    int id = recyclerView.getChildAdapterPosition(child);
                    switch (id) {
                        case 1:
                            BehaviourFragment fragmentA = new BehaviourFragment();
                            localFragmentManager.beginTransaction().replace(R.id.content, fragmentA).commit();
                            break;
                        case 2:
                            ConfigurationFragment fragmentB = new ConfigurationFragment();
                            localFragmentManager.beginTransaction().replace(R.id.content, fragmentB).commit();
                            break;
                    }

                    //Toast.makeText(MainActivity.this,"El elemento seleccionado es el número: "+recyclerView.getChildPosition(child),Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                /* Método de clase abstracta vacío, no interesa su utilización */
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                /* Método de clase abstracta vacío, no interesa su utilización */
            }
        });

        layoutManager = new LinearLayoutManager(this);            // Creando a layout Manager

        recyclerView.setLayoutManager(layoutManager);            // Configurando el layout Manager


        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // drawer object Assigned to the view
        drawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.drawer_open,R.string.drawer_close){

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /* Código a ejectura cuando el drawer se abra */
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                /* Código a ejectura cuando el drawer se cierre */
            }

        };
        drawer.setDrawerListener(drawerToggle); // drawer Listener set to the drawer toggle
        drawerToggle.syncState();               // Finally we set the drawer toggle sync State

        fragmentManager = getFragmentManager();
    }

    /**Inicialización de menú superior*/

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**Configuración de menú superior*/

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menuItemId = item.getItemId();

        switch(menuItemId){
            case R.id.about:
                String version = "";

                try {
                    version = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder message = new AlertDialog.Builder(this);
                message.setTitle("Información");
                message.setMessage("Versión: "+ version + "\nFacultad de Ingeniería - Universidad Nacional de La Plata\nProyecto: CIAA");
                message.setCancelable(false);
                message.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog msj = message.create();
                msj.show();
                break;
            case R.id.exit:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
