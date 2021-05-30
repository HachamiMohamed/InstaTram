package inpt.sud.instatram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

public class ModeConfig extends AppCompatActivity {

    Locale myLocale;
    public static String lang = "";//Default Language


    //Shared Preferences Variables
    public static final String Locale_Preference = "Locale Preference";
    public static final String Locale_KeyValue = "Saved Locale";
    public static final String LANGUAGE_PREFERENCE = "Language Preference";
    public static final String SELECTED = "Selected";
    public static final String MODE_PREFERENCE = "Mode Preference";
    public static final String SELECTED_MODE = "Selected";
    public static int languageSelected = 0;
    public static int modeSelected = 0;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //CHECK MODE
        if ( AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Darck);
        }else
            setTheme(R.style.Theme_Light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_config);

        ////CONFIGURATIONS SAVED

        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadLocale();
        loadData();
        loadData1();


    }

    public void changemode(View v){
        showOptionDialogue1();
    }

    public void changelang(View v){
        showOptionDialogue();
    }

    /////MULTI LANGUAGE SUPPORT

    //Change Locale
    public  void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);//Set Selected Locale
        saveLocale(lang);//Save the selected locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
    }

    //Save locale method in preferences
    public  void saveLocale(String lang) {
        editor.putString(Locale_KeyValue, lang);
        editor.commit();
    }

    //Get locale method in preferences
    public  void loadLocale() {
        String language = sharedPreferences.getString(Locale_KeyValue, "");
        changeLocale(language);
    }


    ///// ALERT DIALOGUE FOR LANGUAGE

    public  void showOptionDialogue(){
        String[] language = {"ENGLISH","SPANISH"};
        String title ="Choose language";
        String proceed="Proceed";
        String exit ="Exit";
        if (languageSelected==1){
            title ="Elige lengua";
            proceed="Continuar";
            exit ="Salida";
            language[0]="Inglés";
            language[1]="Español";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ModeConfig.this);
        builder.setTitle(title);
        //languageSelected=Integer.parseInt(SELECTED);
        loadData();
        builder.setSingleChoiceItems(language, languageSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (language[which] == "ENGLISH" || language[which]=="Inglés") {
                    changeLocale("en");
                    languageSelected=0;
                    saveData(languageSelected);
                }
                else {
                    changeLocale("es");
                    languageSelected=1;
                    saveData(languageSelected);
                }
            }
        });
        builder.setPositiveButton(proceed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });builder.setNegativeButton(exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });
        builder.show();

    }

    public  void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(LANGUAGE_PREFERENCE, MODE_PRIVATE);
        languageSelected= sharedPreferences.getInt(SELECTED, 0);
    }

    public void saveData(int languageSelected) {
        SharedPreferences sharedPreferences = getSharedPreferences(LANGUAGE_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SELECTED,languageSelected);
        editor.commit();

    }

    ///// ALERT DIALOG FOR MODE
    public  void showOptionDialogue1(){
        String[] mode = {"DARK MODE","LIGHT MODE"};
        String title ="Choose mode";
        String proceed="Proceed";
        String exit ="Exit";
        if (languageSelected==1){
            title ="Eligir modo";
            proceed="Continuar";
            exit ="Salida";
            mode[0]="MODO OSCURO";
            mode[1]="MODO DE LUZ";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ModeConfig.this);
        builder.setTitle(title);
        loadData1();
        builder.setSingleChoiceItems(mode, modeSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mode[which] == "DARK MODE" || mode[which] == "MODO OSCURO") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    modeSelected=0;
                    saveData1(modeSelected);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    modeSelected=1;
                    saveData1(modeSelected);
                }
            }
        });
        builder.setPositiveButton(proceed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });builder.setNegativeButton(exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public  void loadData1() {
        SharedPreferences sharedPreferences = getSharedPreferences(MODE_PREFERENCE, MODE_PRIVATE);
        modeSelected= sharedPreferences.getInt(SELECTED_MODE, 0);
        if (modeSelected ==0)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }

    public void saveData1(int modeSelected) {
        SharedPreferences sharedPreferences = getSharedPreferences(MODE_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SELECTED_MODE,modeSelected);
        editor.commit();

    }

}