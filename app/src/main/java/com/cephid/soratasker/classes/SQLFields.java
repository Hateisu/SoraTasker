package com.cephid.soratasker.classes;

public enum SQLFields {
    /*Second file from Tasker Abstract*/
    //The idea is that we write new fields in Tasker class and here then SQL CRUD methods automatically updated.

    id("INTEGER PRIMARY KEY AUTOINCREMENT"),
    title("Text"),
    description("Text"),
    isDone("Boolean"),
    madeDate("Text"),
    type("Text"),
    background_color("Text");
    private String fieldtype;
    public String task_value = "";

    SQLFields(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public static String GetTableGenerator() {
        //Auto generating table by fields
        String ret = "(";
        SQLFields[] allF = SQLFields.values();
        for (int i = 0; i < allF.length; i++) {
            ret = ret + allF[i].name() + " " + allF[i].fieldtype;
            if (i == allF.length - 1) {
                return ret + ")";
            } else {
                ret = ret + ",";
            }
        }
        return ret + ")";
    }

    public static String GetInsertGenerator(Tasker task) throws NoSuchFieldException, IllegalAccessException {
        String fieldnames = "(";
        String filedValues = "(";
        SQLFields[] fields = SQLFields.values();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].name().equals("id") && (Integer) task.getClass().getField(fields[i].name()).get(task) == -1) {
                //Pass as id is not setted
            } else {
                fieldnames = fieldnames + fields[i].name();
                filedValues = filedValues + "\"" + task.getClass().getField(fields[i].name()).get(task) + "\"";
                if (i == fields.length - 1) {
                    return fieldnames + ") VALUES " + filedValues + ")";
                } else {
                    fieldnames = fieldnames + ", ";
                    filedValues = filedValues + ", ";
                }
            }
        }
        return fieldnames + ") VALUES " + filedValues + ")";
    }
}
