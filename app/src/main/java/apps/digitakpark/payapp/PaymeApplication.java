package apps.digitakpark.payapp;

import android.app.Application;

import apps.digitakpark.payapp.lib.data.LocalDatabaseAdapterImpl;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.model.Balance;
import apps.digitakpark.payapp.utils.Formatters;


public class PaymeApplication extends Application {

    private static DatabaseAdapter database;
    private static Formatters formatters;

    // FIXME: When firebase is implemented
    public static Balance selectedBalance;

    @Override
    public void onCreate() {
        super.onCreate();
        database = new LocalDatabaseAdapterImpl(this);
        formatters = new Formatters(this);
    }

    public static DatabaseAdapter getDatabaseInstance() {
        return database;
    }

    public static Formatters getFormatters() {
        return formatters;
    }

}

// TODO: Borrar child de Listas

// TODO: Mostrar las fechas correctamente con formato.
// TODO: Mostrar detalle en Balance.
// TODO: Interface grafica.
// TODO: Estilos.
// TODO: Colores para botones dependiendo de TIPO Debt.
// TODO" Me deben Verde, Debo Rojo
// TODO: Posible Perfil.
// TODO: Cuando el balance es igual a 0 entonces normal borrar.
// TODO: Si el balance es diferente de 0 entonces se debe de borrar childs.
