package com.cephid.soratasker.classes;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class TaskerAbstract {
    //First version of tasks

    /*
    I want to make tasker that will show some tasks that has: title, description, madedate, deadlinedate, isDone
     */
    public int id = -1;
    public String title,description;
    public LocalDateTime madeDate,deadlineDate;
    public boolean isDone;
    /*
    everything will be hosted in json files in external source - NO. this will get slow as data came to json. With more data lower speed.

    Everything will be stored in SQLite database stored in external source(To make it backupable by user).

    So we will create DB if it is`nt exist.

     */
}
