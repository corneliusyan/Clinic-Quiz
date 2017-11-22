package com.example.android.clinicquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView btnexit;
    private ImageView suster;
    private ImageView gbrsoal;
    private ImageView btnno;
    private ImageView btnyes;
    private ImageView btnskip;
    private ImageView btnnext;
    private TextView lblnama;
    private TextView lblnomor;
    private TextView lblsoal;
    private TextView lblchat;
    private LinearLayout gbrmenu;
    private LinearLayout menucolor;
    private LinearLayout menuiq;
    private RelativeLayout textbox;
    private RelativeLayout chatbox;
    private Button btniq;
    private Button btncolor;
    int soal=2,nomor=1,benar=0,iq,sec=0;
    MediaPlayer bgmstart,quiz_start,cancel,select,next,betul,salah,bgmloop,voice;
    boolean beforeloop=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quiz_start = MediaPlayer.create(getApplicationContext(), R.raw.quiz_start);
        bgmstart = MediaPlayer.create(getApplicationContext(), R.raw.voice);
        bgmloop = MediaPlayer.create(getApplicationContext(), R.raw.menu);
        voice = MediaPlayer.create(getApplicationContext(), R.raw.voice);
        cancel = MediaPlayer.create(getApplicationContext(), R.raw.cancel);
        next = MediaPlayer.create(getApplicationContext(), R.raw.next);
        select = MediaPlayer.create(getApplicationContext(), R.raw.select);
        betul = MediaPlayer.create(getApplicationContext(), R.raw.betul);
        salah = MediaPlayer.create(getApplicationContext(), R.raw.salah);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        hapusStatusBar();
        btnexit=(ImageView) findViewById(R.id.btnexit);
        suster=(ImageView) findViewById(R.id.suster);
        gbrsoal=(ImageView) findViewById(R.id.gbrsoal);
        btnno=(ImageView) findViewById(R.id.btnno);
        btnyes=(ImageView) findViewById(R.id.btnyes);
        btnskip=(ImageView) findViewById(R.id.btnskip);
        btnnext=(ImageView) findViewById(R.id.btnnext);
        lblnama=(TextView) findViewById(R.id.lblnama);
        lblnomor=(TextView) findViewById(R.id.lblnomor);
        lblsoal=(TextView) findViewById(R.id.lblsoal);
        lblchat=(TextView) findViewById(R.id.lblchat);
        gbrmenu=(LinearLayout) findViewById(R.id.gbrmenu);
        menucolor=(LinearLayout) findViewById(R.id.menucolor);
        menuiq=(LinearLayout) findViewById(R.id.menuiq);
        textbox=(RelativeLayout) findViewById(R.id.textbox);
        chatbox=(RelativeLayout) findViewById(R.id.chatbox);
        btniq=(Button) findViewById(R.id.btniq);
        btncolor=(Button) findViewById(R.id.btncolor);
        try {
            bgmstart.prepareAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        bgmstart.start();
        bgmloop.start();
        bgmloop.setLooping(true);
        new CountDownTimer(6000,200){
            @Override
            public void onTick(long l)
            {
                sec++;
                if(sec==4)
                {
                    suster.setVisibility(View.VISIBLE);
                }
                else if(sec==7)
                {
                    chatbox.setVisibility(View.VISIBLE);
                }
                else if(sec==9)
                {
                    suster.setImageResource(R.drawable.susterawal);
                    btnskip.setVisibility(View.VISIBLE);
                    lblchat.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFinish()
            {
                gbrmenu.setVisibility(View.VISIBLE);
                btnskip.setVisibility(View.INVISIBLE);
                suster.setImageResource(R.drawable.suster1);
            }
        }.start();
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gbrmenu.setVisibility(View.VISIBLE);
                btnskip.setVisibility(View.INVISIBLE);
                suster.setImageResource(R.drawable.suster1);
                bunyi(next);
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bunyi(select);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.exit);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        hapusStatusBar();
                        bunyi(cancel);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                hapusStatusBar();
            }
        });
        btncolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihMenuColor();
            }
        });
        menucolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihMenuColor();

            }
        });
        btniq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihMenuIQ();
            }
        });
        menuiq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihMenuIQ();
            }
        });
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesSoal(true);
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesSoal(false);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bunyi(next);
                nextSoal();
            }
        });

    }
    public void bunyi(MediaPlayer suara)
    {
        if(!suara.isPlaying())
        {
            suara.start();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bgmstart != null) bgmstart.release();
        if (bgmloop != null) bgmloop.release();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (bgmloop != null) bgmloop.pause();
    }
    public void onResume() {
        super.onResume();
        bgmloop.start();
        hapusStatusBar();
    }
    public void prosesSoal(boolean yes)
    {
        nomor++;
        if(soal==2){
            if(nomor-1==1){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 29.\nThose with red green color blindness see a 70.");
                }
                else{
                    benar++; bunyi(betul);
                    nextSoal();
                }
            }
            else if(nomor-1==2){
                if(yes){
                    benar++; bunyi(betul);
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 3.\nThose with red green color blindness see a 5.");
                }
            }
            else if(nomor-1==3){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 74.\nThose with red green color blindness see a 21.");
                }
                else{
                    benar++; bunyi(betul);
                    nextSoal();
                }
            }
            else if(nomor-1==4){
                if(yes){
                    benar++; bunyi(betul);
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 6.\nThe majority of color blind people can't see those numbers clearly.");
                }
            }
            else if(nomor-1==5){
                if(yes){
                    benar++; bunyi(betul);
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 5.\nThe majority of color blind people can't see those numbers clearly.");
                }
            }
            else if(nomor-1==6){
                if(yes){
                    benar++; bunyi(betul);
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 7.\nThe majority of color blind people can't see those numbers clearly.");
                }
            }
            else if(nomor-1==7){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 73.\nThe majority of color blind people can't see those numbers clearly.");
                }
                else{
                    benar++; bunyi(betul);
                    nextSoal();
                }
            }
            else if(nomor-1==8){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nPeople with normal vision shouldn't be able to see any number. Those with red green color blindness will see 5.");
                }
                else{
                    benar++; bunyi(betul);
                    prosesSalah();
                    lblsoal.setText("Correct !\nPeople with normal vision shouldn't be able to see any number. Those with red green color blindness will see 5.");
                }
            }
            else if(nomor-1==9){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nPeople with normal vision shouldn't be able to see any number. Those with red green color blindness will see 45.");
                }
                else{
                    benar++; bunyi(betul);
                    prosesSalah();
                    lblsoal.setText("Correct !\nPeople with normal vision shouldn't be able to see any number. Those with red green color blindness will see 45.");
                }
            }
            else if(nomor-1==10){
                if(yes){
                    benar++; bunyi(betul);
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe right answer is 26. Red color blind people will see a 6,while green color blind people will see a 2.");
                }
            }
        }
        else if(soal==1){
            if(nomor-1==1){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n1 - 1 - 2 - 3 - 5 - 8 - 13 - 21 is the right answer.");
                }
            }
            else if(nomor-1==2){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nMary will be (16+8) = 24 and her brother will be ((16/4)+8) = 12.");
                }
            }
            else if(nomor-1==3){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n2-3-6-7-8-14-15-30 is a correct series.");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
            }
            else if(nomor-1==4){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n\"TNHESLRNEAD\" can be arranged into \"NETHERLANDS\"");
                }
            }
            else if(nomor-1==5){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n(((200/5)/2)/4) = 200/40 equals 5");
                }
            }
            else if(nomor-1==6){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe sequence should be :\n121 - 144 - 169 - 196 - 225");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
            }
            else if(nomor-1==7){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=5;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n5 - 10 - 19 - 32 - 49 - 70 - 95 is a valid sequence");
                }
            }
            else if(nomor-1==8){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe number * in the cell is -5.");
                }
            }
            else if(nomor-1==9){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n1 - 2 - 5 - 10 - 13 - 26 - 29 - 48\n48 is the one that doesn't belong in the series.");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
            }
            else if(nomor-1==10){
                if(yes){
                    iq+=10;
                    benar++; bunyi(betul);
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\n-2, 3, 27, 69, 129, 207 is a valid sequence.");
                }
            }
            else if(nomor-1==11){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe bottom one is square number 8.");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
            }
            else if(nomor-1==12){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThere are no acceptable number to this cell. It's just a random number square.");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
            }
            else if(nomor-1==13){
                if(yes){
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
                else{
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe number * in the cell is indeed 17.");
                }
            }
            else if(nomor-1==14){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe sequence should be :\n1,11,21,1211,111221,312211.");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
            }
            else if(nomor-1==15){
                if(yes){
                    prosesSalah(); bunyi(salah);
                    lblsoal.setText("Incorrect !\nThe correct letter is actually H.");
                }
                else{
                    benar++; bunyi(betul);
                    iq+=10;
                    nextSoal();
                }
            }
        }

    }
    public void nextSoal()
    {
        int sisa;
        btnyes.setVisibility(View.VISIBLE);
        btnno.setVisibility(View.VISIBLE);
        btnnext.setVisibility(View.INVISIBLE);
        if(soal==2)
        {
            sisa=10-nomor;
            lblchat.setText("Colorblind Test\n\nCorrect Answer : "+benar+"\nQuestion(s) left : "+sisa);
            if(nomor==1)
            {
                lblnomor.setText("1");
                gbrsoal.setImageResource(R.drawable.s2_1_29);
                lblsoal.setText("The number is 70");
            }
            else if(nomor==2)
            {
                lblnomor.setText("2");
                gbrsoal.setImageResource(R.drawable.s2_2_3);
                lblsoal.setText("The number is 3");
            }
            else if(nomor==3)
            {
                lblnomor.setText("3");
                gbrsoal.setImageResource(R.drawable.s2_3_74);
                lblsoal.setText("The number is 21");
            }
            else if(nomor==4)
            {
                lblnomor.setText("4");
                gbrsoal.setImageResource(R.drawable.s2_4_6);
                lblsoal.setText("The number is 6");
            }
            else if(nomor==5)
            {
                lblnomor.setText("5");
                gbrsoal.setImageResource(R.drawable.s2_5_5);
                lblsoal.setText("The number is 5");
            }
            else if(nomor==6)
            {
                lblnomor.setText("6");
                gbrsoal.setImageResource(R.drawable.s2_6_7);
                lblsoal.setText("The number is 7");
            }
            else if(nomor==7)
            {
                lblnomor.setText("7");
                gbrsoal.setImageResource(R.drawable.s2_7_73);
                lblsoal.setText("The number is 23");
            }
            else if(nomor==8)
            {
                lblnomor.setText("8");
                gbrsoal.setImageResource(R.drawable.s2_8_x);
                lblsoal.setText("The number is 5");
            }
            else if(nomor==9)
            {
                lblnomor.setText("9");
                gbrsoal.setImageResource(R.drawable.s2_9_x);
                lblsoal.setText("The number is 45");
            }
            else if(nomor==10)
            {
                lblnomor.setText("10");
                gbrsoal.setImageResource(R.drawable.s2_10_26);
                lblsoal.setText("The number is 26");
            }
            else if(nomor==11)
            {
                prosesSelesai();
            }
        }
        else if(soal==1)
        {
            sisa=15-nomor;
            lblchat.setText("IQ Test\n\nCorrect Answer : "+benar+"\nQuestion(s) left : "+sisa);
            if(nomor==1)
            {
                lblnomor.setText("1");
                gbrsoal.setImageResource(R.drawable.s1_easy);
                lblsoal.setText("1 - 1 - 2 - 3 - 5 - 8 - 13 - X\nX should be 21.");
            }
            else if(nomor==2)
            {
                lblnomor.setText("2");
                gbrsoal.setImageResource(R.drawable.s1_easy);
                lblsoal.setText("Mary, who is sixteen years old, is four times as old as her brother. Mary will be 24 when she is twice as old as her brother.");
            }
            else if(nomor==3)
            {
                lblnomor.setText("3");
                gbrsoal.setImageResource(R.drawable.s1_easy);

                lblsoal.setText("2 - 3 - 6 - 7 - 8 - 14 - 15 – 30.\n30 doesn’t belong to the series.");
            }
            else if(nomor==4)
            {
                lblnomor.setText("4");
                gbrsoal.setImageResource(R.drawable.s1_easy);

                lblsoal.setText("If you rearrange the letters \"TNHESLRNEAD\" you have the name of a country.");
            }
            else if(nomor==5)
            {
                lblnomor.setText("5");
                gbrsoal.setImageResource(R.drawable.s1_easy);

                lblsoal.setText("The number that is 1/4 of 1/2 of 1/5 of 200 is 5.");
            }
            else if(nomor==6)
            {
                lblnomor.setText("6");
                gbrsoal.setImageResource(R.drawable.s1_easy);

                lblsoal.setText("121 – 144 – 169 – 196 – 235\nThe sequence above  is right.");
            }
            else if(nomor==7)
            {
                lblnomor.setText("7");
                gbrsoal.setImageResource(R.drawable.s1_easy);

                lblsoal.setText("X – 10 – 19 – 32 – 49 – 70 – 95\n X is 5.");
            }
            else if(nomor==8)
            {
                lblnomor.setText("8");
                gbrsoal.setImageResource(R.drawable.s1_1);
                lblsoal.setText("The number * in the cell is -5");
            }
            else if(nomor==9)
            {
                lblnomor.setText("9");
                gbrsoal.setImageResource(R.drawable.s1_hard);
                lblsoal.setText("1 - 2 - 5 - 10 - 13 - 26 - 29 – 48\n 1 does not belong in the series.");
            }
            else if(nomor==10)
            {
                lblnomor.setText("10");
                gbrsoal.setImageResource(R.drawable.s1_hard);
                lblsoal.setText("-2, 3, 27, 69, 129, X\nX is 207.");
            }
            else if(nomor==11)
            {
                lblnomor.setText("11");
                gbrsoal.setImageResource(R.drawable.s1_2);
                lblsoal.setText("Eight identically sized squares of paper have been placed on top of one another as follows. 2 is at the bottom.");
            }
            else if(nomor==12)
            {
                lblnomor.setText("12");
                gbrsoal.setImageResource(R.drawable.s1_3);
                lblsoal.setText("The number * in the cell is 11.");
            }
            else if(nomor==13)
            {
                lblnomor.setText("13");
                gbrsoal.setImageResource(R.drawable.s1_4);
                lblsoal.setText("The number * in the cell is 17.");
            }
            else if(nomor==14)
            {
                lblnomor.setText("14");
                gbrsoal.setImageResource(R.drawable.s1_hard);
                lblsoal.setText("1,11,21,1211,111221, X\nX is 11222111.");
            }
            else if(nomor==15)
            {
                lblnomor.setText("15");
                gbrsoal.setImageResource(R.drawable.s1_5);
                lblsoal.setText("The letter * in the cell is F");
            }
            else if(nomor==16)
            {
                prosesSelesai();
            }
        }

    }
    public void prosesSelesai()
    {
        if(soal==2)
        {
            Toast.makeText(MainActivity.this,"Colorblind Test Completed !",Toast.LENGTH_SHORT).show();
            if(benar>8)
            {
                lblchat.setText("Congratulations ! You have just finished the Colorblind Test !\nYou got "+benar+" correct answers.\nYou are not a colorblind person.\n\nYou can try taking another quiz if you want.");
            }
            else
            {
                lblchat.setText("You have just finished the Colorblind Test !\nUnfortunately, you only got "+benar+" correct answers.\nYou're most likely a colorblind person; therefore, it's better for you to check to the doctor.\nYou can try taking another quiz if you want.");
            }
        }
        else if(soal==1)
        {
            Toast.makeText(MainActivity.this,"IQ Test Completed !",Toast.LENGTH_SHORT).show();
            Random rand = new Random();
            int  rng = rand.nextInt(2) -2;
            iq+=rng;
            lblchat.setText("Congratulations ! You have just finished the IQ Test !\nYou got "+benar+" correct answers.\nYour estimated IQ is "+iq+".\n\nYou can try taking another quiz if you want.");
        }
        textbox.setVisibility(View.INVISIBLE);
        gbrmenu.setVisibility(View.VISIBLE);
    }
    public void prosesSalah()
    {
        btnyes.setVisibility(View.INVISIBLE);
        btnno.setVisibility(View.INVISIBLE);
        btnnext.setVisibility(View.VISIBLE);
    }
    public void pilihMenuColor()
    {
        bunyi(select);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.menucolor);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                hapusStatusBar();
                bunyi(quiz_start);
                gbrmenu.setVisibility(View.INVISIBLE);
                textbox.setVisibility(View.VISIBLE);
                soal=2;
                nomor=1;
                benar=0;
                lblnama.setText("Colorblind Test : Number");
                nextSoal();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                hapusStatusBar();
                bunyi(cancel);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        hapusStatusBar();
    }
    public void pilihMenuIQ()
    {
        bunyi(select);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.menuiq);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                hapusStatusBar();
                bunyi(quiz_start);
                gbrmenu.setVisibility(View.INVISIBLE);
                textbox.setVisibility(View.VISIBLE);
                soal=1;
                nomor=1;
                benar=0;
                iq=65;
                lblnama.setText("IQ Test : Number");
                nextSoal();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                hapusStatusBar();
                bunyi(cancel);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        hapusStatusBar();
    }
    public void hapusStatusBar()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
