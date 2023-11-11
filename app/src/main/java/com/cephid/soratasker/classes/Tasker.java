package com.cephid.soratasker.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Tasker extends TaskerAbstract{


//==========================================Initialization=============================================
    public Tasker(String title, String description, LocalDateTime madeDate, LocalDateTime deadlineDate) {
        this.title=title;
        this.description=description;
        this.madeDate=madeDate;
        this.deadlineDate=deadlineDate;
        this.isDone = false;
    }
    public Tasker(String title, String description, LocalDateTime madeDate,LocalDateTime deadlineDate,boolean isDone) {
        this(title,description,madeDate,deadlineDate);
        this.isDone=isDone;
    }
    public Tasker(String title, String description, LocalDateTime madeDate,LocalDateTime deadlineDate,boolean isDone, int id) {
        this(title,description,madeDate,deadlineDate,isDone);
        this.id = id;
    }
    public Tasker(String title, String description, LocalDateTime madeDate,LocalDateTime deadlineDate,boolean isDone, int id, TaskTypes type) {
        this(title,description,madeDate,deadlineDate,isDone,id);
        this.type = String.valueOf(type);
    }
    //==========================================Logic=============================================
    public void checkToday(){
        this.isDone = true;
    }
    /*
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("title",this.title);
        obj.put("description",this.description);
        obj.put("madeDate",this.madeDate);
        obj.put("deadlineDate",this.deadlineDate);
        obj.put("isDone",this.isDone);
        return obj;
    }
    public static Tasker TaskFromJSONformattedString(String string) throws JSONException {
        JSONObject obj = new JSONObject(string);
        return new Tasker(
                obj.get("title").toString(),
                obj.get("description").toString(),
                LocalDateTime.parse(obj.get("madeDate").toString()),
                LocalDateTime.parse(obj.get("deadlineDate").toString()),
                (obj.get("isDone").toString().equals("true"))
        );
    }
    public static JSONArray JSONArrayFromTaskArray(Tasker[] array) throws JSONException {
        JSONArray ret = new JSONArray();
        for (Tasker task:array){
            ret.put(task.toJSON());
        }
        return ret;
    }
    public static Tasker[] TaskerArrayFromJSNOformattedString(String string) throws JSONException {
        List<Tasker> list = new ArrayList<>();
        JSONArray array = new JSONArray(string);
        for (int i=0;i< array.length();i++){
            list.add(TaskFromJSONformattedString(array.get(i).toString()));
        }
        return list.toArray(new Tasker[0]);
    }
*/
}
