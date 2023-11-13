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

    //For better builder
    public Tasker(){
        this("","",LocalDateTime.now());
    }

    //For simple add on
    public Tasker(String title, String description, LocalDateTime madeDate) {
        this.title=title;
        this.description=description;
        this.madeDate=madeDate;
        //Default's
        this.id=-1;
        this.isDone=false;
        this.type = TaskTypes.Single.toString();
        this.background_color = TaskColors.RED.toString();
    }

    //For Getting from db
    public Tasker(String title, String description, LocalDateTime madeDate,TaskTypes type,boolean isDone, int id,TaskColors back) {
        this(title,description,madeDate);
        this.type = type.toString();
        this.isDone = isDone;
        this.id = id;
        this.background_color = back.toString();
    }
    /*public Tasker(String title, String description, LocalDateTime madeDate,boolean isDone, int id) {
        this(title,description,madeDate,isDone,id);
        this.type = String.valueOf(type);
    }*/
    //==========================================Logic=============================================
    public void checkToday(){
        this.isDone = true;
    }
    public Tasker setTitle(String title){
        this.title = title;
        return this;
    }
    public Tasker setDescription(String description){
        this.description = description;
        return this;
    }
    public Tasker setId(int id){
        this.id = id;
        return this;
    }
    public Tasker setDate(LocalDateTime date){
        this.madeDate = date;
        return this;
    }

    public Tasker setIsDone(boolean isDone){
        this.isDone = isDone;
        return this;
    }
    public Tasker setType(String type){
        this.type = type;
        return this;
    }
    public Tasker setBackgroundColor(String color){
        this.background_color = color;
        return this;
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
