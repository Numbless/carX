package com.raketasoft.carxapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Typeface font; //шрифт
    private Point screenSize; // размер экрана
    private float textSmall,textMiddle,textBig; // разеры текста
    private int spaceSmall,spaceMiddle,spaceBig,iconMiddle,iconBig; // размеры иконок
    private InitViewHelper iv; // класс для тонкой настройки эелементов управления
    private int selectedItem,page=0; // выбранные элемент списка и выбранная страница приложения
    private ArrayList<CarItem> carList; // список с данными
    private long back_pressed=0; // необоходимо для счетчика разницы времени нажати "Назад"
    private Timer updateTask;// таймер

    /*
        Таким образом был определен абстрактный источник данных, он представляет собой JSON в котором определены обьекты в массиве, каждый обьект
        содержит необходимые поля для однозначной идентификации транспортной единицы
     */
    private final String staticJson="{\n" +
            "   \"id\":\"3318\",\n" +
            "   \"company\":\"TheCompany\",\n" +
            "   \"cars\":[\n" +
            "      {\n" +
            "         \"id\":199,\n" +
            "         \"number\":\"а419бб\",\n" +
            "         \"type\":\"truck\",\n" +
            "         \"driver\":\"Иванов М.М.\",\n" +
            "         \"work\":\"Уборка территории\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":209,\n" +
            "         \"number\":\"в111аб\",\n" +
            "         \"type\":\"tech\",\n" +
            "         \"driver\":\"Полевой Г.Д.\",\n" +
            "         \"work\":\"Проверка электрики\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":149,\n" +
            "         \"number\":\"у911мм\",\n" +
            "         \"type\":\"tech\",\n" +
            "         \"driver\":\"Зотиков А.В.\",\n" +
            "         \"work\":\"Проверка электрики\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":144,\n" +
            "         \"number\":\"р109ум\",\n" +
            "         \"type\":\"tech\",\n" +
            "         \"driver\":\"Акиньшин С.В.\",\n" +
            "         \"work\":\"Мойка окон\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":366,\n" +
            "         \"number\":\"у400аа\",\n" +
            "         \"type\":\"truck\",\n" +
            "         \"driver\":\"Ивушкин Д.Д.\",\n" +
            "         \"work\":\"Уборка внутри помещения\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":455,\n" +
            "         \"number\":\"к200ка\",\n" +
            "         \"type\":\"truck\",\n" +
            "         \"driver\":\"Маликов Л.Г.\",\n" +
            "         \"work\":\"Проверка электрики\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":202,\n" +
            "         \"number\":\"а996ум\",\n" +
            "         \"type\":\"tech\",\n" +
            "         \"driver\":\"Акимов В.В.\",\n" +
            "         \"work\":\"Уборка внутри помещения\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":500,\n" +
            "         \"number\":\"а175ит\",\n" +
            "         \"type\":\"tech\",\n" +
            "         \"driver\":\"Луга А.М.\",\n" +
            "         \"work\":\"Технические работы общего назначения\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"id\":520,\n" +
            "         \"number\":\"б657им\",\n" +
            "         \"type\":\"truck\",\n" +
            "         \"driver\":\"Киреев А.А.\",\n" +
            "         \"work\":\"Вывоз мусора\"\n" +
            "      }\n" +
            "   ]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRes();
        initPage();
    }
    /*
    Данный метод позволяет настроить определенную страницу приложения под свои нужды, в зависимости от поставленной задачи.
    В более масштабных реализациях подобный метод лучше разбить на несколько или создавать отдельные фрагменты в рамках приложения
     */
    private void initPage(){
        setActiveLayout(page);
        switch (page){
            case 0:
                getSupportActionBar().hide();
                LinearLayout lay=findViewById(R.id.screen_0);
                lay.setBackgroundColor(getResources().getColor(R.color.colorBackgroundSplash));

                ImageView splashImg=findViewById(R.id.screen_0_imag_0);
                splashImg.setBackgroundResource(R.drawable.logo);
                splashImg.setLayoutParams(iv.initParams(screenSize.x/2,screenSize.x/2,new int[]{0,spaceBig,0,spaceBig},0));

                TextView start=findViewById(R.id.screen_0_start);
                iv.initTextView(start,"Войти",textBig);
                start.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{spaceMiddle,0,spaceMiddle,0},0));
                start.setTextColor(getResources().getColor(R.color.colorWhite));

                TextView footer=findViewById(R.id.screen_0_footer);
                iv.initTextView(footer,"Мы всегда на связи",textBig);
                footer.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{spaceMiddle,0,spaceMiddle,spaceMiddle},1));
                footer.setTextColor(getResources().getColor(R.color.colorWhite));

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataPrepareFactory prep=new DataPrepareFactory(staticJson);
                        carList=prep.prepareAllData();
                        if(carList==null) {
                            showMessage("Ошибка", "Произошла ошибка формата, необходимо убедится в целостности источника дыннх");
                            return;
                        }

                        ArrayList<Integer> idErr=new ArrayList<>();
                        for(int i=0;i<carList.size();i++)
                            if (carList.get(i).getStatus() > 0) idErr.add(carList.get(i).getID());

                        if(idErr.size()>0){
                            String idErrMessage="Обнаружены ошибки формата в обьектах со следующими ID: ";
                            for(int i=0;i<idErr.size();i++)
                                idErrMessage+=idErr.get(i)+((i<idErr.size()-1)?", ":"");
                            showMessage("Ошибка", idErrMessage);
                            return;
                        }

                        page=1;
                        initPage();
                    }
                });
                break;

            case 1:
                ActionBar actBar=getSupportActionBar();
                actBar.show();
                actBar.setTitle("Автовизиты");
                actBar.setIcon(getResources().getDrawable(R.drawable.logo));

                final ListView lv=findViewById(R.id.screen_1_list);
                lv.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{0,spaceSmall,0,spaceSmall},0));
                lv.setAdapter(new CarXListView(this,iv,carList,textMiddle,iconBig,spaceMiddle));

                /*
                таймер обновляющий данные раз в 30 секунд. В даннос луче выполняет роль автоматизации запроса, При условии работы сервера и размещении данных на нем
                обновление денных лучше выполнять через asyncTask, запуск asyncTask можно так же выполнять через таймер
                 */
                updateTask.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        DataPrepareFactory prep=new DataPrepareFactory(staticJson);
                        carList=prep.prepareAllData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,Html.fromHtml("Выполнено обновление данных"),Toast.LENGTH_SHORT).show();
                                lv.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{0,spaceSmall,0,spaceSmall},0));
                                lv.setAdapter(new CarXListView(MainActivity.this,iv,carList,textMiddle,iconBig,spaceMiddle));
                            }
                        });
                    }
                },30*1000,30*1000);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedItem=i;
                        page=2;
                        initPage();
                    }
                });
                break;

            case 2:
                final int titleID[]={R.id.screen_2_text_0,R.id.screen_2_text_1,R.id.screen_2_text_2,R.id.screen_2_text_3,R.id.screen_2_text_4};
                final int editID[]={R.id.screen_2_edit_0,R.id.screen_2_edit_1,R.id.screen_2_edit_2,R.id.screen_2_edit_3,R.id.screen_2_edit_4};
                final EditText edit[]=new EditText[editID.length];
                String titleText[]={"ID","Номер","Тип","Водитель","Вид работ"},tmpCarData[]={
                        carList.get(selectedItem).getID()+"",
                        carList.get(selectedItem).getNumber(),
                        carList.get(selectedItem).getType(),
                        carList.get(selectedItem).getDriver(),
                        carList.get(selectedItem).getWork()};

                for(int i=0;i<titleID.length;i++){
                    TextView tv=findViewById(titleID[i]);
                    iv.initTextView(tv,titleText[i],textBig);
                    tv.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{spaceMiddle,0,spaceMiddle,0},0));

                    edit[i]=findViewById(editID[i]);
                    iv.initTextView(edit[i],tmpCarData[i],textBig);
                    edit[i].setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{spaceMiddle,0,spaceMiddle,0},0));
                }


                TextView save=findViewById(R.id.screen_2_save);
                iv.initTextView(save,"Сохранить",textBig);
                save.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{spaceMiddle,0,spaceMiddle,spaceSmall},0));

                TextView cancel=findViewById(R.id.screen_2_cancel);
                iv.initTextView(cancel,"Назад",textBig);
                cancel.setLayoutParams(iv.initParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,new int[]{spaceMiddle,0,spaceMiddle,0},0));

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        carList.get(selectedItem).setNumber(edit[1].getText().toString());
                        carList.get(selectedItem).setType(edit[2].getText().toString());
                        carList.get(selectedItem).setDriver(edit[3].getText().toString());
                        carList.get(selectedItem).setWork(edit[4].getText().toString());
                        if(checkEmptyEdit(edit)==true)
                            showMessage("Внимание","Не все поля в форме заполнены");
                        else {
                            page = 1;
                            initPage();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        page=1;
                        initPage();
                    }
                });
                break;
        }
    }

    /*
    проверка на отсутствие данных в одном из элементов редактирования текста
     */

    private boolean checkEmptyEdit(EditText edit[]){
        for(int i=0;i<edit.length;i++)
            if(edit[i].getText().toString().length()==0)return true;
            return false;
    }

    /*
    метод позволяющий установить видимоть только одному LinearLayout среди множества определенных в
    главном Layout приложения. Таким образом исчезает необходимоть в использовании Fragment в условиях
    небольших проектов разрабатываемых в одиночку
     */
    private void setActiveLayout(final int page){
        final int layoutID[]={R.id.screen_0,R.id.screen_1,R.id.screen_2};
        for(int i=0;i<layoutID.length;i++)
            findViewById(layoutID[i]).setVisibility(i==page?View.VISIBLE:View.GONE);
    }

    /*
    В этом методет сосредоточены UI операции выполняемые единократно,
    рпределеные размеры для текста и элементов управления. За счет использование
    windowManager определеляется разрешение экрана мобильного устройства
    и все дальнейшие установки размеров элементов UI будут исходить из полученныех в этом методе
     */
    private void initRes(){
        updateTask=new Timer();
        iv=new InitViewHelper(font);
        font = Typeface.create("sans-serif-light", Typeface.NORMAL);
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        textSmall = (convertPixelsToDp(screenSize.x))/32;
        textMiddle = (convertPixelsToDp(screenSize.x))/25;
        textBig = (convertPixelsToDp(screenSize.x))/18;

        spaceSmall=screenSize.x/30;
        spaceMiddle=screenSize.x/20;
        spaceBig=screenSize.x/10;

        iconMiddle=screenSize.x/10;
        iconBig=screenSize.x/6;
    }

    /*
    преобразование пиксельных единиц плотности в размер шрифта в пт
     */
    private float convertPixelsToDp(final float px){
        return px/(getResources().getDisplayMetrics().densityDpi/160f);
    }

    /*
    метод позволяет выйти из опеределенной страницы приложения в зависимости от ее номера.
    Такой способ лего настраеваем и позволяет гибко и без дополнительных трудозатрат реализовать
    обратный переход
     */
    @Override
    public void onBackPressed() {
        switch (page){
            case 0:
                finish();
                break;

            case 1:
                Toast.makeText(this,Html.fromHtml("Выход возможет при повторном нажатии <b>\"Назад\""),Toast.LENGTH_SHORT).show();
                if (back_pressed + 2000 > System.currentTimeMillis()) finish();
                else
                    back_pressed = System.currentTimeMillis();
                break;

            case 2:
                page=1;
                initPage();
                break;
        }
    }

    /*
    метод вывода на экран сообщения
     */
    private void showMessage(String title,String message){
        AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(Html.fromHtml(message));
        dialog.setPositiveButton("OK", null);
        dialog.setCancelable(false);
        dialog.create().show();
    }
}