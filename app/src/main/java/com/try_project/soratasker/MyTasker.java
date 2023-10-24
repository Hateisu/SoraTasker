package com.try_project.soratasker;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MyTasker {
    public String name, description, type, other;
    public String madein;
    public String background = "red";
    public int task_id = 0 - 1;
    public Map<String, Object> map = new HashMap<String, Object>();

    public MyTasker(String name, String description, String type, String other, String madein) throws JSONException {
        this.name = name;
        this.background = "red";
        this.description = description;
        this.type = type;
        this.other = other;
        this.madein = madein;
        if (other != null) {
            register_attr_by_type();
        }
    }

    private void register_attr_by_type() throws JSONException {
        JSONObject ob = new JSONObject(this.other);
        switch (this.type) {
            case "checkbox_single":
                this.map.put("is_checked", (boolean) ob.get("is_checked") );
                break;
            case "checkbox_everyday":
                this.map.put("occure_in_checked", (ob.get("occure_in_checked")));
                this.map.put("expire_in", ob.get("expire_in"));
                break;
            case "checkbox_single_occure":
                this.map.put("occure_in_checked", (ob.get("occure_in_checked")));
                this.map.put("occure_in", (ob.get("occure_in")));

                break;
        }
    }

    public static String[] get_arr(Object object) {
        String[] ret = null;
        try {
            JSONArray arr = new JSONArray(object.toString());
            ret = new String[arr.length()];
            for (int n = 0; n < arr.length(); n++) {
                ret[n] = arr.get(n).toString();
            }
        } catch (Exception e) {
            System.out.println(e + " Exception in MyTaskerArray");
        }
        return ret;
    }

    public static String generate_json(MyTasker[] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            /*if (tasks[i].name.equals("Title"))*/
            System.out.println(tasks[i].other);
        }
        String out = "{\n" +
                "  \"tasks\": [";
        int leng = tasks.length;
        int counter_emp = 0;
        for (int i = 0; i < leng; i++) {
            MyTasker task = tasks[i];


            if (task != null) {
                out = out + "\n     {\n" +
                        "      \"name\" : \"" + task.name + "\",\n" +
                        "      \"description\": \"" + task.description + "\",\n" +
                        "      \"type\": \"" + task.type + "\",\n" +
                        "      \"made_in\": \"" + task.madein + "\",\n" +
                        "      \"background_color\": \"" + task.background + "\",\n" +
                        "      \"other\":" + task.generate_other() + "\n" +
                        "      }";
                if (i != (leng - 1 - counter_emp)) out = out + ",";
            } else counter_emp = counter_emp + 1;
        }
        if (tasks[tasks.length - 1] == null) {
            String temp = "";
            for (int i = 0; i < out.length() - 1; i++) {
                temp = temp + out.charAt(i);
            }
            out = temp;
        }
        out = out + "\n" +
                "  ]\n" +
                "}";
        return out;
    }

    public static MyTasker generate_checkbox_single_occure(String name, String description, String madein, String[] ocure_in) throws JSONException {
        String dates = "";
        for (int i = 0; i < ocure_in.length; i++) {
            dates = dates + "\"" + ocure_in[i] + "\"";
            if (i != (ocure_in.length - 1)) dates = dates + ",";
        }
        return new MyTasker(name, description, "checkbox_single_occure", "{\"occure_in_checked\":[\"\"],\"occure_in\":[" + dates + "]}", madein);
    }

    public static MyTasker generate_checkbox_everyday(String name, String description, String madein, String expire_in) throws JSONException {
        return new MyTasker(name, description, "checkbox_everyday", "{\"repeatable\":true,\"occure_in_checked\":[],\"expire_in\":\"" + expire_in + "\"}", madein);
    }

    public static MyTasker generate_checkbox_single(String name, String description, String madein) throws JSONException {
        return new MyTasker(name, description, "checkbox_single", "{\"is_checked\":false}", madein);
    }

    public static String get_current_date() {
        String date = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        date = dtf.format(LocalDateTime.now()).toString();
        return date;
    }

    public static MyTasker[] remove_at_pos(MyTasker[] tasks, int task_id) {
        MyTasker[] task_temp = new MyTasker[tasks.length - 1];
        int pos = -1;
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].task_id == task_id) pos = i;
        }
        for (int i = 0; i < tasks.length; i++) {
            if (i < pos) {
                task_temp[i] = tasks[i];
            } else if (i > pos) {
                task_temp[i - 1] = tasks[i];
            }
        }
        return task_temp;
    }

    public String generate_other() {
        String temp = "{";
        String out = "";
        for (Map.Entry<String, Object> set : map.entrySet()) {
            // Printing all elements of a Map
            if (set.getKey().equals("expire_in")){
                temp = temp + "\"" + set.getKey() + "\":\""+set.getValue()+"\",";
            }else {
                temp = temp + "\"" + set.getKey() + "\":"+set.getValue()+",";
            }
        }
        for (int i = 0; i < temp.length() - 1; i++) {
            out = out + temp.charAt(i);
        }

        return out+"}";
    }

}
