package com.example.abc.languageconvertor1;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextToSpeech t1,t2;
    EditText e1;
    TextView e2;
    ImageView b1,b2,b3,b4;
    Context context;
    RelativeLayout l1,l2;
    NavigationView navigationview;
    FirebaseAnalytics mFirebaseAnalytics;
    public static String translateres;
    String textToTranslate;
    int result;
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    // private ShareActionProvider shareActionProvider;
     Animation  uptodown,downtoup;
    final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Create1();

    }
    public void Create1() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        e1 = (EditText) findViewById(R.id.text);
        e2 = (TextView) findViewById(R.id.texttranslate);
        b1 = (ImageView) findViewById(R.id.audio);
        b2 = (ImageView) findViewById(R.id.speech);
        b3 = (ImageView) findViewById(R.id.translate);
        b4 = (ImageView) findViewById(R.id.audio1);
        l1 = (RelativeLayout) findViewById(R.id.layo);
        l2 = (RelativeLayout) findViewById(R.id.lay);
        e1.setScroller(new Scroller(getApplicationContext()));
        e1.setVerticalScrollBarEnabled(true);
        e2.setScroller(new Scroller(getApplicationContext()));
        e2.setVerticalScrollBarEnabled(true);
        e1.setMinLines(1);
        e2.setMinLines(1);
        e1.setMaxLines(2);
        e2.setMaxLines(2);
        b4.setClickable(true);
        // b5.setClickable(true);
        b3.setClickable(true);
        b1.setClickable(true);
        b2.setClickable(true);
         mFirebaseAnalytics =FirebaseAnalytics.getInstance(this);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
         myToggle = new ActionBarDrawerToggle(
             this, myDrawer, toolbar, R.string.open, R.string.close);
       myDrawer.addDrawerListener(myToggle);
       myToggle.syncState();
       navigationview = (NavigationView) findViewById(R.id.navigation);
        navigationview.setNavigationItemSelectedListener(this);
       uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup=AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                promtSpeechInput();
            }
        });
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {  //Locale aloc =Locale.forLanguageTag("hi-IN");
                    /*Locale list[] = DateFormat.getAvailableLocales();
                    for (Locale aLocale : list) {
                        if(aLocale.toString()==ad) */
                    t1.setLanguage(Locale.getDefault());

                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSpeak = e1.getText().toString();
              // startActivity(new Intent(MainActivity.this,Popup.class));
                //onResume();
                // Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                // onResume();
            }

        });

        t2= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    String textlang1 = spinner.getSelectedItem().toString();
                    if(textlang1.equalsIgnoreCase("Chinese"))
                    {
                       result=t2.setLanguage(Locale.CHINESE);
                    }
                    if(textlang1.equalsIgnoreCase("Kannada"))
                    {
                        result=t2.setLanguage(Locale.CANADA);
                    }
                    if(textlang1.equalsIgnoreCase("Korean"))
                    {
                        result=t2.setLanguage(Locale.KOREAN);
                    }
                    if(textlang1.equalsIgnoreCase("French"))
                    {

                        result=t2.setLanguage(Locale.FRENCH);
                    }

                    else
                    {
                       result=t2.setLanguage(Locale.getDefault());
                    }
                   // t1.setLanguage(Locale.getDefault());

                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(MainActivity.this, "Feature Not supported in this app.", Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
                else{
                      String toSpeak = e2.getText().toString();
                    t2.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textToTranslate = e1.getText().toString();
                String textlang = spinner.getSelectedItem().toString();
                if(textlang.equalsIgnoreCase("French")) {
                   String  languagePair = "en-fr";
                  // b3.setImageResource(R.drawable.france);
                    translate(textToTranslate, languagePair);

                }
                if(textlang.equalsIgnoreCase("Spanish")) {
                    String  languagePair = "en-es";

                   // b3.setImageResource(R.drawable.spain);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Hindi")) {
                    String  languagePair = "en-hi";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Albanian")) {
                    String  languagePair = "en-sq";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Amharic")) {
                    String  languagePair = "en-am";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Armenian")) {
                    String  languagePair = "en-hy";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Africaans")) {
                    String  languagePair = "en-af";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Chinese")) {
                    String  languagePair = "en-zh";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Bengali")) {
                    String  languagePair = "en-bn";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Burmese")) {
                    String  languagePair = "en-my";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Bulgarian")) {
                    String  languagePair = "en-bg";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }

                if(textlang.equalsIgnoreCase("Welsh")) {
                    String  languagePair = "en-cy";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Vietnamese")) {
                    String  languagePair = "en-vi";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Dutch")) {
                    String  languagePair = "en-nl";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Greek")) {
                    String  languagePair = "en-el";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Kannada")) {
                    String  languagePair = "en-kn";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("latin")) {
                    String  languagePair = "en-la";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Gujrati")) {
                    String  languagePair = "en-gu";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Korean")) {
                    String  languagePair = "en-ko";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }
                if(textlang.equalsIgnoreCase("Irish")) {
                    String  languagePair = "en-ga";
                    // b3.setImageResource(R.drawable.india);
                    translate(textToTranslate, languagePair);
                }


            }
        });
           }

   public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

   public boolean onOptionsItemSelected(MenuItem item) {
        if (myToggle.onOptionsItemSelected(item)) {

            return true;
        }

        return onOptionsItemSelected(item);
    }
    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
    private void promtSpeechInput() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speech recognization");
        try
        {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a)
        {
            Toast.makeText(getApplicationContext(),"speech recognization not supported...",Toast.LENGTH_SHORT).show();
        }



    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode)
        {
            case REQ_CODE_SPEECH_INPUT:
            {
                if(resultCode==RESULT_OK&&null!=data)
                {
                    Create1();
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    e1.setText(result.get(0));

                }
            }
        }
    }
   void translate(String text,String lang)
    {
        BackgroundTask b1 =new BackgroundTask(context);
        b1.execute(text,lang);
        e2.setText(translateres);
       //        Log.d("Translation Result",translateres);
    }
    public void onBackPressed() {

        if (myDrawer.isDrawerOpen(GravityCompat.START)) {
            myDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
           // Intent intent=new Intent(MainActivity.this,AboutUs.class);
           // startActivity(intent);
           Toast.makeText(MainActivity.this,"This is an Language Translator App.",Toast.LENGTH_SHORT).show();// Handle the camera action
        } else if (id == R.id.menu_feedback) {
            final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
            _Intent.setType("text/html");
            _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(R.string.email) });
            _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.subject));
            _Intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.message));
            startActivity(Intent.createChooser(_Intent, getString(R.string.feedback)));
            return true;

        } else if (id == R.id.menu_rateus) {
              rateApp();
             return true;
        }

         else if (id == R.id.menu_share) {
            // item.setChecked(true);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody ="https://play.google.com/store/apps/details"+e1.getText().toString() + "\n" + e2.getText().toString();
            String shareSub = "Text: ";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share Via"));
            return true;

        }
       myDrawer.closeDrawer(GravityCompat.START);
        return true;

    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
      // ProgressDialog progressDialog;
        Context ctx;
        BackgroundTask(Context ctx)
        {
            this.ctx=ctx;
        }


        protected String doInBackground(String...params)
        {

            String language=params[1];
            String jsonString;
            try
            {
                String str=textToTranslate.replaceAll(System.lineSeparator()," ") ;
                String input=str.replaceAll(" ","%20");
                Log.e("output",input);
                String yandexKey="trnsl.1.1.20180509T115213Z.459b3ed9f944825f.24179f3f931cd0e24667e79e6118a797615e162b";
                String yandexUrl="https://translate.yandex.net/api/v1.5/tr.json/translate?key="+yandexKey+"&text="+input+"&lang="+language;
                URL yandexTranslateURL=new URL(yandexUrl);
                HttpURLConnection httpJsonConnection=(HttpURLConnection)yandexTranslateURL.openConnection();
                InputStream inputStream=httpJsonConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder jsonStringBuilder=new StringBuilder();
                while((jsonString=bufferedReader.readLine())!=null)
                {
                    jsonStringBuilder.append(jsonString+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpJsonConnection.disconnect();

                String resultString=jsonStringBuilder.toString().trim();
               resultString=resultString.substring(resultString.indexOf('[')+1);
               resultString=resultString.substring(0,resultString.indexOf("]"));
               resultString=resultString.substring(resultString.indexOf("\"")+1);
                resultString=resultString.substring(0,resultString.indexOf("\""));
                Log.d("Translation Result",resultString);
                translateres= resultString;
                return jsonStringBuilder.toString().trim();

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException a)
            {
                a.printStackTrace();
            }
            return null;

        }
        protected void onPreExecute()
        {
            super.onPreExecute();

          /* progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Translating....");
            progressDialog.setTitle("Translator");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
           // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
          */
        }
        protected void OnPostExecute(String result) {
          /*  if (progressDialog.isShowing()) {
                progressDialog.dismiss();


            }*/
        }
        protected void OnProgressExecute(String result)
        {

        }

    }

}

